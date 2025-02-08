package chat.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deadline task that stores description and date to be done by.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a Deadline object.
     *
     * @param description Description of the Task.
     * @param by Date to complete the Task by.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, HHmm")) + ")";
    }

    @Override
    public String toDataString() {
        return "D" + super.toDataString() + "/-/"
                + this.by.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }
}