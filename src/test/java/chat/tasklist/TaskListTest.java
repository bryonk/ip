package chat.tasklist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import chat.tasks.Todo;

public class TaskListTest {
    private TaskList tasks;

    @BeforeEach
    public void init() {
        this.tasks = new TaskList();
    }

    @Test
    public void getSize_emptyTaskListTest() {
        assertEquals(0, tasks.getSize());
    }

    @Test
    public void addTask_oneTaskListTest() {
        tasks.addTask(new Todo("test"), false);
        assertEquals(1, tasks.getSize());
    }

    @Test
    public void deleteTask_emptyTaskListTest() {
        tasks.addTask(new Todo("test"), false);
        tasks.deleteTask(1, false);
        assertEquals(0, tasks.getSize());
    }

    @Test
    public void findTaskTest() {
        tasks.addTask(new Todo("test"), false);
        assertEquals("[T][ ] test", tasks.getTask(1).toString());
    }
}
