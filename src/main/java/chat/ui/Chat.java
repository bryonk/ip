package chat.ui;

import chat.exceptions.ChatException;
import chat.exceptions.ChatInvalidException;
import chat.parser.Parser;
import chat.parser.Function;
import chat.parser.Job;
import chat.storage.Storage;
import chat.tasklist.TaskList;
import chat.tasks.Task;
import chat.tasks.Event;
import chat.tasks.Todo;
import chat.tasks.Deadline;

/**
 * Base object containing the main function.
 */
public class Chat {
    private final Storage storage;
    private TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a Chat object containing Ui, Storage and TaskList.
     *
     * @param filePath File path containing storage data.
     */
    public Chat(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = storage.loadTasks();
        } catch (ChatException e) {
            ui.printError(e);
            this.tasks = new TaskList();
        }
    }

    public void run() {
        ui.printWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                Job job = Parser.parseInput(ui.readCommand());
                ui.printLine();
                isExit = runCommand(job);
                storage.saveData(tasks);
            } catch (ChatException e) {
                ui.printError(e);
            } finally {
                ui.printLine();
            }
        }
    }

    public static void main(String[] args) {
        new Chat("data/chat.txt").run();
    }

    /**
     * Determine and execute command to run.
     *
     * @param job Job object containing Function and description.
     * @return Boolean isExit to exit program.
     */
    public boolean runCommand(Job job) {
        boolean isExit = false;
        if (job.getFunction() == Function.bye) {
            ui.printExit();
            isExit = true;
        } else if (job.getFunction() == Function.list) {
            ui.printTasks(tasks);
        } else if (job.getFunction() == Function.mark) {
            Integer index = Parser.convertToInt(job.getDescription());
            tasks.markTask(index);
        } else if (job.getFunction() == Function.unmark) {
            Integer index = Parser.convertToInt(job.getDescription());
            tasks.unmarkTask(index);
        } else if (job.getFunction() == Function.delete) {
            Integer index = Parser.convertToInt(job.getDescription());
            tasks.deleteTask(index, true);
        } else if (job.getFunction() == Function.todo) {
            Task task = new Todo(job.getDescription());
            tasks.addTask(task, true);
        } else if (job.getFunction() == Function.deadline &&
                job.getDescription().contains("/by")) {
            String[] toSplit = job.getDescription().split("/by");
            try {
                String taskName = toSplit[0].trim();
                String deadlineBy = toSplit[1].trim();
                if (taskName.isEmpty() || deadlineBy.isEmpty()) {
                    throw new ChatInvalidException("ChatInvalidException: Function deadline is missing arguments!");
                }
                Task task = new Deadline(taskName, deadlineBy);
                tasks.addTask(task, true);
            } catch (IndexOutOfBoundsException e) {
                throw new ChatInvalidException("ChatInvalidException: Function deadline has bad arguments!");
            }

        } else if (job.getFunction() == Function.event &&
                job.getDescription().contains("/from") &&
                job.getDescription().contains("/to")) {
            String[] toSplit = job.getDescription().split("/from");
            try {
                String taskName = toSplit[0].trim();
                String[] nextSplit = toSplit[1].split("/to");
                String eventFrom = nextSplit[0].trim();
                String eventTo = nextSplit[1].trim();
                if (taskName.isEmpty() || eventFrom.isEmpty() || eventTo.isEmpty()) {
                    throw new ChatInvalidException("ChatInvalidException: Function event is missing arguments!");
                }
                Task task = new Event(taskName, eventFrom, eventTo);
                tasks.addTask(task, true);
            } catch (IndexOutOfBoundsException e) {
                throw new ChatInvalidException("ChatInvalidException: Function event has bad arguments!");
            }
        } else {
            throw new ChatInvalidException("ChatInvalidException: Invalid Function!");
        }
        return isExit;
    }
}
