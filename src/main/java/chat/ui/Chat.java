package chat.ui;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import chat.exceptions.ChatException;
import chat.parser.Job;
import chat.storage.Storage;
import chat.tasklist.TaskList;
import chat.tasks.Deadline;
import chat.tasks.Event;
import chat.tasks.Task;
import chat.tasks.Todo;

/**
 * Base object containing the main function.
 */
public class Chat {
    private static final String DATE_FORMAT = "dd/MM/yyyy HHmm";
    private static final String REGEX_BY = "/by";
    private static final String REGEX_FROM = "/from";
    private static final String REGEX_TO = "/to";

    private final Storage storage;
    private TaskList tasks;

    /**
     * Constructs a Chat object containing Ui, Storage and TaskList.
     */
    public Chat() {
        this.storage = new Storage("data/chat.txt");
        try {
            this.tasks = storage.loadTasks();
        } catch (ChatException e) {
            this.tasks = new TaskList();
        }
    }

    /**
     * Determine and execute command to run.
     *
     * @param job Job object containing Function and description.
     * @return String response for MainWindow.
     */
    public String getResponse(Job job) {
        assert job != null;
        String response = processJob(job);
        storage.saveData(tasks);
        return response;
    }

    private String processJob(Job job) {
        DateTimeFormatter d = DateTimeFormatter.ofPattern(DATE_FORMAT);
        String response = "";
        switch (job.getFunction()) {
        case bye:
            response = handleBye();
            break;
        case list:
            response = handleList();
            break;
        case mark:
            response = handleMark(job);
            break;
        case unmark:
            response = handleUnmark(job);
            break;
        case delete:
            response = handleDelete(job);
            break;
        case todo:
            response = addTodo(job);
            break;
        case deadline:
            response = addDeadline(job, d);
            break;
        case event:
            response = addEvent(job, d);
            break;
        case find:
            response = handleFind(job);
            break;
        case error:
            response = handleError(job);
            break;
        default:
            response = handleUnknown();
            break;
        }
        return response;
    }

    private String handleBye() {
        return "Bye. Hope to see you again soon!";
    }

    private String handleList() {
        return tasks.toString();
    }

    private String handleMark(Job job) {
        if (job.hasIndex()) {
            return tasks.markTask(job.getIndex());
        } else {
            return "Error: mark function out of bounds!";
        }
    }

    private String handleUnmark(Job job) {
        if (job.hasIndex()) {
            return tasks.unmarkTask(job.getIndex());
        } else {
            return "Error: unmark function out of bounds!";
        }
    }

    private String handleDelete(Job job) {
        if (job.hasIndex()) {
            return tasks.deleteTask(job.getIndex(), true);
        } else {
            return "Error: delete function out of bounds!";
        }
    }

    private String addTodo(Job job) {
        Task task = new Todo(job.getDescription());
        if (tasks.checkDuplicate(task)) {
            return "Error: Task has already been added.";
        }
        return tasks.addTask(task, true);
    }

    private String addDeadline(Job job, DateTimeFormatter d) {
        String description = job.getDescription();
        if (!description.contains(REGEX_BY)) {
            return "Error: deadline function has bad arguments!";
        }
        String[] toSplit = description.split(REGEX_BY);
        if (toSplit.length != 2) {
            return "Error: deadline function has bad arguments!";
        }
        String taskName = toSplit[0].trim();
        String deadlineBy = toSplit[1].trim();
        if (taskName.isEmpty() || deadlineBy.isEmpty()) {
            return "Error: deadline function has bad arguments!";
        }
        try {
            Task task = new Deadline(taskName, LocalDateTime.from(d.parse(deadlineBy)));
            if (tasks.checkDuplicate(task)) {
                return "Error: Task has already been added.";
            }
            return tasks.addTask(task, true);
        } catch (DateTimeException e) {
            return "Error: bad date format (" + DATE_FORMAT + ")";
        }

    }

    private String addEvent(Job job, DateTimeFormatter d) {
        String description = job.getDescription();
        if (!description.contains(REGEX_FROM) || !description.contains(REGEX_TO)) {
            return "Error: event function has bad arguments!";
        }
        String[] toSplit = description.split(REGEX_FROM);
        if (toSplit.length != 2) {
            return "Error: deadline function has bad arguments!";
        }
        String taskName = toSplit[0].trim();
        String[] nextSplit = toSplit[1].split(REGEX_TO);
        if (nextSplit.length != 2) {
            return "Error: deadline function has bad arguments!";
        }
        String eventFrom = nextSplit[0].trim();
        String eventTo = nextSplit[1].trim();
        if (taskName.isEmpty() || eventFrom.isEmpty() || eventTo.isEmpty()) {
            return "Error: event function has bad arguments!";
        }
        try {
            Task task = new Event(taskName,
                    LocalDateTime.from(d.parse(eventFrom)),
                    LocalDateTime.from(d.parse(eventTo)));
            if (tasks.checkDuplicate(task)) {
                return "Error: Task has already been added.";
            }
            return tasks.addTask(task, true);
        } catch (DateTimeException e) {
            return "Error: bad date format (" + DATE_FORMAT + ")";
        }
    }

    private String handleFind(Job job) {
        return tasks.findTask(job.getDescription());
    }

    private String handleError(Job job) {
        return job.getDescription();
    }

    private String handleUnknown() {
        return "Unknown command";
    }
}
