import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Chat");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            String text = scanner.nextLine();
            if (text.equals("bye")) {
                break;
            }
            System.out.println("____________________________________________________________");
            System.out.println(text);
            System.out.println("____________________________________________________________");
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
