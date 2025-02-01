package chat.tasks;

import chat.exceptions.ChatAddException;

import java.time.DateTimeException;
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
    public Event(String description, String from, String to) {
        super(description);
        try {
            DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            this.from = LocalDateTime.from(dateTime.parse(from.trim()));
            this.to = LocalDateTime.from(dateTime.parse(to.trim()));
        } catch (DateTimeException e) {
            throw new ChatAddException("ChatAddException: Date time in wrong format (dd/MM/yyyy HHmm)");
        }
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