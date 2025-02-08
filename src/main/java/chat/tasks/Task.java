package chat.tasks;

/**
 * Task to be completed
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Constructs a Task object.
     *
     * @param description Description of the Task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Converts Task description and completion into file storage format.
     *
     * @return String containing the completion boolean and description.
     */
    public String toDataString() {
        if (isDone) {
            return "/-/1/-/" + this.description;
        } else {
            return "/-/0/-/" + this.description;
        }
    }

    private String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public boolean contains(String input) {
        return this.description.toLowerCase().contains(input.toLowerCase());
    }
}
