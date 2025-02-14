package chat.tasklist;

import java.util.ArrayList;

import chat.tasks.Task;

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
     * @param isVerbose If response is required.
     * @return Response of the add function
     */
    public String addTask(Task task, boolean isVerbose) {
        this.tasks.add(task);
        if (isVerbose) {
            return "Got it. I've added this task:\n  " + task
                    + "\nNow you have " + tasks.size() + " tasks in the list.";
        }
        return "";
    }

    /**
     * Marks a Task in the TaskList.
     *
     * @param index Index of the Task.
     * @return Response of the mark function
     */
    public String markTask(int index) {
        try {
            Task task = this.getTask(index);
            task.markAsDone();
            return "Nice! I've marked this task as done:\n  " + task;
        } catch (IndexOutOfBoundsException e) {
            return "Error: mark function out of bounds!";
        }
    }

    /**
     * Unmarks a Task in the TaskList.
     *
     * @param index Index of the Task.
     * @return Response of the unmark function
     */
    public String unmarkTask(int index) {
        try {
            Task task = this.getTask(index);
            task.markAsUndone();
            return "OK, I've marked this task as not done yet:\n  " + task;
        } catch (IndexOutOfBoundsException e) {
            return "Error: unmark function out of bounds!";
        }
    }

    /**
     * Deletes a Task from the TaskList.
     *
     * @param index Index of the Task.
     * @param isVerbose If printing is required.
     * @return Response of the delete function
     */
    public String deleteTask(int index, boolean isVerbose) {
        try {
            Task task = this.getTask(index);
            this.tasks.remove(index - 1);
            if (isVerbose) {
                return "Noted. I've removed this task:\n  "
                        + task + "\nNow you have " + tasks.size() + " tasks in the list.";
            }
            return "";
        } catch (IndexOutOfBoundsException e) {
            return "Error: delete function out of bounds!";
        }
    }

    public int getSize() {
        return this.tasks.size();
    }

    public Task getTask(int index) {
        return this.tasks.get(index - 1);
    }

    /**
     * Finds Tasks that contain the input string.
     *
     * @param input String to match with each Task.
     * @return Response of the find function
     */
    public String findTask(String input) {
        StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.contains(input)) {
                response.append((i + 1)).append(".").append(task);
            }
        }
        return response.toString();
    }

    /**
     * Returns the TaskList in a printable format.
     *
     * @return String of TaskList in printable format.
     */
    public String toString() {
        if (tasks.isEmpty()) {
            return "There are no Tasks.";
        } else {
            StringBuilder output = new StringBuilder();
            output.append("Here are the tasks in your list:\n");
            for (int i = 1; i < this.getSize() + 1; i++) {
                output.append(i).append(".").append(this.getTask(i));
                if (i != this.getSize()) {
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

    public boolean checkDuplicate(Task t) {
        for (Task task : tasks) {
            if (task.compareWith(t)) {
                return true;
            }
        }
        return false;
    }

}
