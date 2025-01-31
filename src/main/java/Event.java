import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description);
        try {
            DateTimeFormatter d = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
            this.from = LocalDateTime.from(d.parse(from.trim()));
            this.to = LocalDateTime.from(d.parse(to.trim()));
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