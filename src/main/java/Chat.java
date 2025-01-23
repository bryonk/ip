import java.util.Scanner;
import java.util.ArrayList;

public class Chat {
    public static void main(String[] args) throws ChatException {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<Task>();
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Chat");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            try {
                Job job = parseInput(scanner.nextLine());
                if (job.getFunction() == Function.bye) {
                    break;
                } else if (job.getFunction() == Function.list) {
                    printList(list);
                } else if (job.getFunction() == Function.mark) {
                    Integer index = convertToInt(job.getDescription());
                    markAsDone(index, list);
                } else if (job.getFunction() == Function.unmark) {
                    Integer index = convertToInt(job.getDescription());
                    markAsUndone(index, list);
                } else if (job.getFunction() == Function.delete) {
                    Integer index = convertToInt(job.getDescription());
                    deleteTask(index, list);
                } else if (job.getFunction() == Function.todo) {
                    Todo todo = new Todo(job.getDescription());
                    addTask(todo, list);
                } else if (job.getFunction() == Function.deadline &&
                        job.getDescription().contains("/by")) {
                    String[] toSplit = job.getDescription().split("/");
                    try {
                        String taskName = toSplit[0].trim();
                        String deadlineBy = toSplit[1].substring(3).trim();
                        if (taskName.isEmpty() || deadlineBy.isEmpty()) {
                            throw new ChatAddException("ChatAddException: Function deadline is missing arguments!");
                        }
                        Deadline deadline = new Deadline(taskName, deadlineBy);
                        addTask(deadline, list);
                    } catch (IndexOutOfBoundsException e) {
                        throw new ChatAddException("ChatAddException: Function deadline has bad arguments!");
                    }

                } else if (job.getFunction() == Function.event &&
                        job.getDescription().contains("/from") &&
                        job.getDescription().contains("/to")) {
                    String[] toSplit = job.getDescription().split("/");
                    try {
                        String taskName = toSplit[0].trim();
                        String eventFrom = toSplit[1].substring(5).trim();
                        String eventTo = toSplit[2].substring(3).trim();
                        if (taskName.isEmpty() || eventFrom.isEmpty() || eventTo.isEmpty()) {
                            throw new ChatAddException("ChatAddException: Function event is missing arguments!");
                        }
                        Event event = new Event(taskName, eventFrom, eventTo);
                        addTask(event, list);
                    } catch (IndexOutOfBoundsException e) {
                        throw new ChatAddException("ChatAddException: Function event has bad arguments!");
                    }
                } else {
                    throw new ChatInvalidException("ChatInvalidException: Invalid Function!");
                }
            } catch (ChatException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
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
            System.out.println("The list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < list.size(); i++) {
                Task task = list.get(i);
                System.out.println(i + 1 + "." + task);
            }
        }
        System.out.println("____________________________________________________________");
    }

    public static Job parseInput(String input) throws ChatParseException, ChatInvalidException {
        try {
            String[] inputArr = input.split(" ", 2);
            if (inputArr.length == 1) {
                return new Job(Function.valueOf(inputArr[0]));
            } else {
                return new Job(Function.valueOf(inputArr[0]), inputArr[1]);
            }
        } catch (IllegalArgumentException e) {
            throw new ChatInvalidException("ChatInvalidException: Function not recognised");
        }
    }

    public static Integer convertToInt(String input) throws ChatParseException {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new ChatParseException("ChatParseException: Not a number!");
        }
    }

    public static void markAsDone(Integer index, ArrayList<Task> list) throws ChatEditException {
        try {
            Task task = list.get(index - 1);
            task.markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + task);
            System.out.println("____________________________________________________________");
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Out of bounds!");
        }
    }

    public static void markAsUndone(Integer index, ArrayList<Task> list) throws ChatEditException {
        try {
            Task task = list.get(index - 1);
            task.markAsUndone();
            System.out.println("____________________________________________________________");
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + task);
            System.out.println("____________________________________________________________");
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Out of bounds!");
        }
    }

    public static void deleteTask(Integer index, ArrayList<Task> list) throws ChatParseException {
        try {
            Task task = list.get(index - 1);
            list.remove(task);
            System.out.println("____________________________________________________________");
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } catch (IndexOutOfBoundsException e) {
            throw new ChatParseException("ChatEditException: Out of bounds!");
        }

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
