import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<String>();
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Chat");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
        while (true) {
            String text = scanner.nextLine();
            if (text.equals("bye")) {
                break;
            } else if (text.equals("list")) {
                print_list(list);
            } else {
                list.add(text);
                System.out.println("____________________________________________________________");
                System.out.println("added: " + text);
                System.out.println("____________________________________________________________");
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public static void print_list(ArrayList<String> list) {
        System.out.println("____________________________________________________________");
        if (list.size() == 0) {
            System.out.println("list is empty...");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + 1 + ". " + list.get(i));
            }
        }
        System.out.println("____________________________________________________________");
    }
}
