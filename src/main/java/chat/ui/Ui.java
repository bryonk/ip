package chat.ui;

import java.util.Scanner;

import chat.exceptions.ChatException;
import chat.tasklist.TaskList;

/**
 * Contains the interactions with the user.
 */
public class Ui {
    private static final String LINE_SEPARATOR = "------------------------------------------------------------";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads the next line from user input.
     *
     * @return User input string.
     */
    public String readCommand() {
        return this.scanner.nextLine();
    }

    /**
     * Prints the welcome text.
     */
    public void printWelcome() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Hello! I'm Chat");
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    public void printLine() {
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Print each Task in the TaskList.
     *
     * @param tasks TaskList containing the Tasks.
     */
    public void printTasks(TaskList tasks) {
        System.out.println(tasks);
    }

    public void printExit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints the ChatException that occurred.
     *
     * @param e Exception that was passed.
     */
    public void printError(ChatException e) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(e.getMessage());
    }
}
