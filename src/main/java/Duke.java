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
            } else if (text.startsWith("todo")) {
                Todo todo = new Todo(text.substring(5));
                addTask(todo, list);
            } else if (text.startsWith("deadline") && (text.contains("/by"))) {
                String[] toSplit = text.substring(9).split("/");
                Deadline deadline = new Deadline(toSplit[0].trim(),
                                                 toSplit[1].substring(3).trim());
                addTask(deadline, list);
            } else if (text.startsWith("event") && (text.contains("/from")) && (text.contains("/to"))) {
                String[] toSplit = text.substring(6).split("/");
                Event event = new Event(toSplit[0].trim(),
                                        toSplit[1].substring(5).trim(),
                                        toSplit[2].substring(3).trim());
                addTask(event, list);
            } else {
                System.out.println("____________________________________________________________");
                System.out.println("Error: Task type not defined.");
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
            System.out.println("Here are the tasks in your list:");
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

    public static void addTask(Task task, ArrayList<Task> list) {
        list.add(task);
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}
