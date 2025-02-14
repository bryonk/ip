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

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks a task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    public boolean contains(String input) {
        return this.description.toLowerCase().contains(input.toLowerCase());
    }

    /**
     * Compares if a Task has the same output string.
     *
     * @param task Task to compare with.
     * @return if the two Tasks are the same.
     */
    public boolean compareWith(Task task) {
        if (this.isDone) {
            task.markAsDone();
        }
        boolean isEquals = this.toString().equalsIgnoreCase(task.toString());
        if (this.isDone) {
            task.markAsUndone();
        }
        return isEquals;
    }
}
