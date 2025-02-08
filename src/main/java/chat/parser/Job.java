package chat.parser;

import java.util.regex.Pattern;

/**
 * Contains the Function type and String description for identifying and parsing function.
 */
public class Job {
    private final Function function;
    private final String description;

    public Job(Function function) {
        this.function = function;
        this.description = "";
    }

    public Job(Function function, String description) {
        this.function = function;
        this.description = description;
    }

    /**
     * Checks and returns the description if parsed correctly.
     *
     * @return The description that follows the command.
     */
    public String getDescription() {
        if (!this.description.isEmpty()) {
            return this.description;
        } else {
            return "Error: " + this.function.name() + " function is missing arguments!";
        }
    }

    /**
     * Returns the Function type for identification.
     *
     * @return Function type.
     */
    public Function getFunction() {
        return this.function;
    }

    public boolean hasIndex() {
        Pattern pattern = Pattern.compile("\\d+");
        return pattern.matcher(description).matches();
    }

    public int getIndex() {
        return Integer.parseInt(description);
    }
}
