package chat.parser;

import chat.exceptions.ChatParseException;
import chat.tasks.Deadline;
import chat.tasks.Event;
import chat.tasks.Task;
import chat.tasks.Todo;


/**
 * Parses input from users/files to the required format.
 */
public class Parser {

    /**
     * Parses input from the user into a Job object.
     *
     * @param input User input.
     * @return Job object containing the function and description if present.
     * @throws ChatParseException If the function is not recognised.
     */
    public static Job parseInput(String input) throws ChatParseException {
        try {
            String[] inputArr = input.split(" ", 2);
            if (inputArr.length == 1) {
                return new Job(Function.valueOf(inputArr[0]));
            } else {
                return new Job(Function.valueOf(inputArr[0]), inputArr[1]);
            }
        } catch (IllegalArgumentException e) {
            throw new ChatParseException("ChatParseException: Function not recognised");
        }
    }

    /**
     * Parses the input from the file into a Task object
     *
     * @param input String array containing the input type, description and date time if needed.
     * @return Task object
     */
    public static Task parseFileInput(String[] input) {
        Task task = switch (input[0].trim()) {
            case "T" -> new Todo(input[2].trim());
            case "D" -> new Deadline(input[2].trim(), input[3].trim());
            case "E" -> new Event(input[2].trim(), input[3].trim(), input[4].trim());
            default -> new Task("");
        };
        if (input[1].trim().equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Converts the input into Integer.
     *
     * @param input String input.
     * @return Integer parsed from input.
     * @throws ChatParseException If the input is not a number.
     */
    public static Integer convertToInt(String input) throws ChatParseException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ChatParseException("ChatParseException: Not a number!");
        }
    }
}
