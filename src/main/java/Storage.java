import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    private void checkFile() {
        try {
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();
                this.file.createNewFile();
            }
        } catch (IOException e) {
            throw new ChatFileException("ChatFileException: File cannot be handled");
        }

    }

    public TaskList loadData() throws ChatException {
        TaskList tasks = new TaskList();
        try {
            this.checkFile();
            Scanner scanner = new Scanner(this.file);
            while (scanner.hasNext()) {
                String[] strings = scanner.nextLine().split("/-/");
                Task task = switch (strings[0].trim()) {
                    case "T" -> new Todo(strings[2].trim());
                    case "D" -> new Deadline(strings[2].trim(), strings[3].trim());
                    case "E" -> new Event(strings[2].trim(), strings[3].trim(), strings[4].trim());
                    default -> new Task("");
                };
                if (strings[1].trim().equals("1")) {
                    task.markAsDone();
                }
                tasks.addTask(task);
            }
            return tasks;
        } catch (ChatException e) {
            return new TaskList();
        } catch (FileNotFoundException | IndexOutOfBoundsException e ) {
            throw new ChatFileException("ChatFileException: File format error!");
        }
    }

    public void saveData(TaskList tasks) {
        try {
            FileWriter fileWriter = new FileWriter(this.file, false);
            ArrayList<String> taskListData = tasks.convertToDataFormat();
            for (String taskData : taskListData) {
                fileWriter.write(taskData + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new ChatFileException("ChatFileException: Cannot write to file!");
        }
    }
}
