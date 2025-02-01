package chat.ui;

import chat.exceptions.ChatException;
import chat.tasklist.TaskList;

import java.util.Scanner;

public class Ui {
    private static final String LINE_SEPARATOR = "------------------------------------------------------------";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }

    public void printWelcome() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Hello! I'm Chat");
        System.out.println("What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    public void printLine() {
        System.out.println(LINE_SEPARATOR);
    }

    public void printTasks(TaskList tasks) {
        System.out.println(tasks);
    }

    public void printExit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void printError(ChatException e) {
        System.out.println(LINE_SEPARATOR);
        System.out.println(e.getMessage());
    }
}
