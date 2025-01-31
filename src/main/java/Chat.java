import java.util.Scanner;

public class Chat {
    public static void main(String[] args) throws ChatException {
        Scanner scanner = new Scanner(System.in);
        printWelcome();
        Storage storage = new Storage("data/chat.txt");
        TaskList tasks = storage.loadData();
        while (true) {
            try {
                Job job = parseInput(scanner.nextLine());
                if (job.getFunction() == Function.bye) {
                    break;
                } else if (job.getFunction() == Function.list) {
                    printList(tasks);
                } else if (job.getFunction() == Function.mark) {
                    Integer index = convertToInt(job.getDescription());
                    markAsDone(index, tasks);
                } else if (job.getFunction() == Function.unmark) {
                    Integer index = convertToInt(job.getDescription());
                    markAsUndone(index, tasks);
                } else if (job.getFunction() == Function.delete) {
                    Integer index = convertToInt(job.getDescription());
                    deleteTask(index, tasks);
                } else if (job.getFunction() == Function.todo) {
                    Todo todo = new Todo(job.getDescription());
                    addTask(todo, tasks);
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
                        addTask(deadline, tasks);
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
                        addTask(event, tasks);
                    } catch (IndexOutOfBoundsException e) {
                        throw new ChatAddException("ChatAddException: Function event has bad arguments!");
                    }
                } else {
                    throw new ChatInvalidException("ChatInvalidException: Invalid Function!");
                }
                storage.saveData(tasks);
            } catch (ChatException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            }

        }
        scanner.close();
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public static void printWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Chat");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public static void printList(TaskList tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        System.out.println(tasks);
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

    public static void markAsDone(Integer index, TaskList tasks) throws ChatEditException {
        try {
            tasks.markTask(index - 1);
            System.out.println("____________________________________________________________");
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks.findTask(index - 1));
            System.out.println("____________________________________________________________");
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Out of bounds!");
        }
    }

    public static void markAsUndone(Integer index, TaskList tasks) throws ChatEditException {
        try {
            tasks.unmarkTask(index - 1);
            System.out.println("____________________________________________________________");
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + tasks.findTask(index - 1));
            System.out.println("____________________________________________________________");
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Out of bounds!");
        }
    }

    public static void deleteTask(Integer index, TaskList tasks) throws ChatParseException {
        try {
            Task task = tasks.findTask(index - 1);
            tasks.deleteTask(index - 1);
            System.out.println("____________________________________________________________");
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } catch (IndexOutOfBoundsException e) {
            throw new ChatParseException("ChatEditException: Out of bounds!");
        }

    }

    public static void addTask(Task task, TaskList tasks) {
        tasks.addTask(task);
        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.getSize() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }
}
