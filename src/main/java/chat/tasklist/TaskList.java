package chat.tasklist;

import chat.exceptions.ChatEditException;
import chat.tasks.Task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task, boolean isVerbose) {
        this.tasks.add(task);
        if (isVerbose) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    public void markTask(int index) throws ChatEditException {
        try {
            this.tasks.get(index - 1).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + this.tasks.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Mark function out of bounds!");
        }
    }

    public void unmarkTask(int index) throws ChatEditException {
        try {
            this.tasks.get(index - 1).markAsUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + this.tasks.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Unmark function out of bounds!");
        }
    }

    public void deleteTask(int index) throws ChatEditException {
        try {
            Task task = this.tasks.get(index - 1);
            this.tasks.remove(index - 1);
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Delete function out of bounds!");
        }
    }

    public int getSize() {
        return this.tasks.size();
    }

    public Task findTask(int index) {
        return this.tasks.get(index);
    }

    public String toString() {
        if (tasks.isEmpty()) {
            return "There are no Tasks.";
        } else {
            StringBuilder output = new StringBuilder();
            output.append("Here are the tasks in your list:\n");
            for (int i = 0; i < this.getSize(); i++) {
                output.append(i + 1).append(".").append(this.findTask(i));
                if (i != this.getSize() - 1) {
                    output.append("\n");
                }
            }
            return output.toString();
        }
    }

    public ArrayList<String> convertToDataFormat() {
        ArrayList<String> strings = new ArrayList<String>();
        for (Task task : tasks) {
            strings.add(task.toDataString());
        }
        return strings;
    }

}
