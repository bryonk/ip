package chat.tasks;

/**
 * Todo task that stores the description of the task.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo object.
     *
     * @param description Description of the Task.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String toDataString() {
        return "T" + super.toDataString();
    }
}
