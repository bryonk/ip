package chat.parser;

import chat.exceptions.ChatParseException;
import chat.tasks.Deadline;
import chat.tasks.Event;
import chat.tasks.Task;
import chat.tasks.Todo;


public class Parser {
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

    public static Task parseFileInput(String[] input) {
        Task task = switch (input[0].trim()) {
            case "T" -> new Todo(input[2].trim());
            case "D" ->  new Deadline(input[2].trim(), input[3].trim());
            case "E" -> new Event(input[2].trim(), input[3].trim(), input[4].trim());
            default -> new Task("");
        };
        if (input[1].trim().equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    public static Integer convertToInt(String input) throws ChatParseException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ChatParseException("ChatParseException: Not a number!");
        }
    }
}
