package chat.parser;

import chat.exceptions.ChatParseException;

/**
 * Contains the Function type and String description for identifying and parsing function.
 */
public class Job {
    private final Function function;
    private final String description;

    /**
     * Constructor for Job using Function.
     *
     * @param function Function type parsed.
     */
    public Job(Function function) {
        this.function = function;
        this.description = "";
    }

    /**
     * Constructor for Job using Function and Description.
     *
     * @param function Function type parsed.
     * @param description Description following Function type.
     */
    public Job(Function function, String description) {
        this.function = function;
        this.description = description;
    }

    /**
     * Checks and returns the description if parsed correctly.
     *
     * @return The description that follows the command.
     * @throws ChatParseException If the Job object is missing expected description.
     */
    public String getDescription() throws ChatParseException {
        if (!this.description.isEmpty()) {
            return this.description;
        } else {
            throw new ChatParseException("ChatParseException: Function "
                    + this.function.name() + " is missing arguments!");
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
}
