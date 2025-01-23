import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<Task>();
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Chat");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            String text = scanner.nextLine();
            if (text.equals("bye")) {
                break;
            } else if (text.equals("list")) {
                printList(list);
            } else if (text.startsWith("mark")) {
                Integer index = Integer.parseInt(text.substring(5));
                markAsDone(index, list);
            } else if (text.startsWith("unmark")) {
                Integer index = Integer.parseInt(text.substring(7));
                markAsUndone(index, list);
            } else {
                Task task = new Task(text);
                list.add(task);
                System.out.println("____________________________________________________________");
                System.out.println("added: " + text);
                System.out.println("____________________________________________________________");
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public static void printList(ArrayList<Task> list) {
        System.out.println("____________________________________________________________");
        if (list.isEmpty()) {
            System.out.println("list is empty...");
        } else {
            for (int i = 0; i < list.size(); i++) {
                Task task = list.get(i);
                System.out.println(i + 1 + "." + task);
            }
        }
        System.out.println("____________________________________________________________");
    }

    public static void markAsDone(Integer index, ArrayList<Task> list) {
        System.out.println("____________________________________________________________");
        if (index > list.size()) {
            System.out.println("Error: Index provided larger than list");
        } else {
            Task task = list.get(index - 1);
            task.markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + task);
        }
        System.out.println("____________________________________________________________");
    }

    public static void markAsUndone(Integer index, ArrayList<Task> list) {
        System.out.println("____________________________________________________________");
        if (index > list.size()) {
            System.out.println("Error: Index provided larger than list");
        } else {
            Task task = list.get(index - 1);
            task.markAsUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
        }
        System.out.println("____________________________________________________________");
    }
}
