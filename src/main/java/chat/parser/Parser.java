package chat.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     */
    public static Job parseInput(String input) {
        try {
            String[] inputArr = input.split(" ", 2);
            if (inputArr.length == 1) {
                return new Job(Function.valueOf(inputArr[0]));
            } else {
                return new Job(Function.valueOf(inputArr[0]), inputArr[1]);
            }
        } catch (IllegalArgumentException e) {
            return new Job(Function.error, "Error: Function not recognised");
        }
    }

    /**
     * Parses the input from the file into a Task object
     *
     * @param input String array containing the input type, description and date time if needed.
     * @return Task object
     */
    public static Task parseFileInput(String[] input) {
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        Task task = switch (input[0].trim()) {
        case "T" -> new Todo(input[2].trim());
        case "D" -> new Deadline(input[2].trim(),
                LocalDateTime.from(dateTime.parse(input[3].trim())));
        case "E" -> new Event(input[2].trim(),
                LocalDateTime.from(dateTime.parse(input[3].trim())),
                LocalDateTime.from(dateTime.parse(input[4].trim())));
        default -> new Task("");
        };
        if (input[1].trim().equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}
