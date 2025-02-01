package chat.tasklist;

import chat.exceptions.ChatEditException;
import chat.tasks.Task;

import java.util.ArrayList;

/**
 * Stores a list of Task objects and operates on it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Adds a Task into the TaskList.
     *
     * @param task Task to be added.
     * @param isVerbose If printing is required.
     */
    public void addTask(Task task, boolean isVerbose) {
        this.tasks.add(task);
        if (isVerbose) {
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        }
    }

    /**
     * Marks a Task in the TaskList.
     *
     * @param index Index of the Task.
     * @throws ChatEditException If the index is out of bounds.
     */
    public void markTask(int index) throws ChatEditException {
        try {
            this.tasks.get(index - 1).markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + this.tasks.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Mark function out of bounds!");
        }
    }

    /**
     * Unmarks a Task in the TaskList.
     *
     * @param index Index of the Task.
     * @throws ChatEditException If the index is out of bounds.
     */
    public void unmarkTask(int index) throws ChatEditException {
        try {
            this.tasks.get(index - 1).markAsUndone();
            System.out.println("OK, I've marked this task as not done yet:");
            System.out.println("  " + this.tasks.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Unmark function out of bounds!");
        }
    }

    /**
     * Deletes a Task from the TaskList.
     *
     * @param index Index of the Task.
     * @param isVerbose If printing is required.
     * @throws ChatEditException If the index is out of bounds.
     */
    public void deleteTask(int index, boolean isVerbose) throws ChatEditException {
        try {
            Task task = this.tasks.get(index - 1);
            this.tasks.remove(index - 1);
            if (isVerbose) {
                System.out.println("Noted. I've removed this task:");
                System.out.println("  " + task);
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new ChatEditException("ChatEditException: Delete function out of bounds!");
        }
    }

    public int getSize() {
        return this.tasks.size();
    }

    public Task findTask(int index) {
        return this.tasks.get(index - 1);
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

    /**
     * Converts the Tasks in TaskList into the file format for storage.
     *
     * @return ArrayList of Strings containing each Task in file storage format.
     */
    public ArrayList<String> convertToDataFormat() {
        ArrayList<String> strings = new ArrayList<String>();
        for (Task task : tasks) {
            strings.add(task.toDataString());
        }
        return strings;
    }

}
