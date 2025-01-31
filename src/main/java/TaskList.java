import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void markTask(int index) {
        this.tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        this.tasks.get(index).markAsUndone();
    }

    public void deleteTask(int index) {
        this.tasks.remove(index);
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
