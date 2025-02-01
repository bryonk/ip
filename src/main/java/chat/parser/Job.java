package chat.parser;

import chat.exceptions.ChatParseException;

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

    public String getDescription() throws ChatParseException {
        if (!this.description.isEmpty()) {
            return this.description;
        } else {
            throw new ChatParseException("ChatParseException: Function "
                    + this.function.name() + " is missing arguments!");
        }
    }

    public Function getFunction() {
        return this.function;
    }
}
