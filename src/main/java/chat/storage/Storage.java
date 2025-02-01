package chat.storage;

import chat.exceptions.ChatFileException;
import chat.tasklist.TaskList;
import chat.tasks.Task;
import chat.parser.Parser;

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

    public TaskList loadTasks() throws ChatFileException {
        TaskList tasks = new TaskList();
        try {
            this.checkFile();
            Scanner scanner = new Scanner(this.file);
            while (scanner.hasNext()) {
                String[] strings = scanner.nextLine().split("/-/");
                Task task = Parser.parseFileInput(strings);
                tasks.addTask(task, false);
            }
            return tasks;
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
