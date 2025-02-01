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

/**
 * Contains the operations used for storing and loading data.
 */
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

    /**
     * Loads the tasks from the file into a TaskList.
     *
     * @return TaskList containing the tasks stored in file.
     * @throws ChatFileException If File cannot be read properly.
     */
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

    /**
     * Stores the tasks in TaskList into the file.
     *
     * @param tasks TaskList containing the current tasks.
     * @throws ChatFileException If the file cannot be written to.
     */
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
