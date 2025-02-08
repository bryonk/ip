package chat.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Event task that stores description and dates from and to the event occurs.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Constructs an Event object.
     *
     * @param description Description of the Task.
     * @param from Date the Event starts from.
     * @param to Date the Event ends.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: "
                + this.from.format(DateTimeFormatter.ofPattern("MMM dd yyyy, HHmm")) + " to: "
                + this.to.format(DateTimeFormatter.ofPattern("MMM dd yyyy, HHmm")) + ")";
    }

    @Override
    public String toDataString() {
        return "D" + super.toDataString()
                + "/-/" + this.from.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"))
                + "/-/" + this.to.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }
}
