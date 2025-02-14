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
        String response = "";
        Task task;
        boolean hasError = false;
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        try {
            switch (job.getFunction()) {
            case bye:
                response = "Bye. Hope to see you again soon!";
                break;
            case list:
                response = tasks.toString();
                break;
            case mark:
                if (job.hasIndex()) {
                    response = tasks.markTask(job.getIndex());
                } else {
                    response = "Error: mark function out of bounds!";
                }
                break;
            case unmark:
                if (job.hasIndex()) {
                    response = tasks.unmarkTask(job.getIndex());
                } else {
                    response = "Error: unmark function out of bounds!";
                }
                break;
            case delete:
                if (job.hasIndex()) {
                    response = tasks.deleteTask(job.getIndex(), true);
                } else {
                    response = "Error: delete function out of bounds!";
                }
                break;
            case todo:
                task = new Todo(job.getDescription());
                response = tasks.addTask(task, true);
                break;
            case deadline:
                if (job.getDescription().contains("/by")) {
                    String[] toSplit = job.getDescription().split("/by");
                    String taskName = toSplit[0].trim();
                    String deadlineBy = toSplit[1].trim();
                    if (taskName.isEmpty() || deadlineBy.isEmpty()) {
                        hasError = true;
                    }
                    try {
                        task = new Deadline(taskName, LocalDateTime.from(dateTime.parse(deadlineBy.trim())));
                        response = tasks.addTask(task, true);
                    } catch (DateTimeException e) {
                        response = "Error: bad date format (dd/MM/yyyy HHmm)";
                    }

                } else {
                    hasError = true;
                }
                if (hasError) {
                    response = "Error: deadline function has bad arguments!";
                }
                break;
            case event:
                if (job.getDescription().contains("/from") && job.getDescription().contains("/to")) {
                    String[] toSplit = job.getDescription().split("/from");
                    String taskName = toSplit[0].trim();
                    String[] nextSplit = toSplit[1].split("/to");
                    String eventFrom = nextSplit[0].trim();
                    String eventTo = nextSplit[1].trim();
                    if (taskName.isEmpty() || eventFrom.isEmpty() || eventTo.isEmpty()) {
                        hasError = true;
                    }
                    try {
                        task = new Event(taskName,
                                LocalDateTime.from(dateTime.parse(eventFrom.trim())),
                                LocalDateTime.from(dateTime.parse(eventTo.trim())));
                        response = tasks.addTask(task, true);
                    } catch (DateTimeException e) {
                        response = "Error: bad date format (dd/MM/yyyy HHmm)";
                    }
                } else {
                    hasError = true;
                }
                if (hasError) {
                    response = "Error: event function has bad arguments!";
                }
                break;
            case find:
                response = tasks.findTask(job.getDescription());
                break;
            default:
                response = "Unknown command";
                break;
            };
        } catch (ChatException e) {
            response = e.getMessage();
        }
        storage.saveData(tasks);
        return response;
    }
}
