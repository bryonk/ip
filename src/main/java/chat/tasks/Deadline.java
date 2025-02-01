package chat.tasks;

import chat.exceptions.ChatAddException;

import java.time.DateTimeException;
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
    public Deadline(String description, String by) {
        super(description);
        try {
            DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            this.by = LocalDateTime.from(d.parse(by.trim()));
        } catch (DateTimeException e) {
            throw new ChatAddException("ChatAddException: Date time in wrong format (dd/MM/yyyy HHmm)");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + this.by.format(DateTimeFormatter.ofPattern("MMM dd yyyy, HHmm")) + ")";
    }

    @Override
    public String toDataString() {
        return "D" + super.toDataString() + "/-/" + this.by.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"));
    }
}