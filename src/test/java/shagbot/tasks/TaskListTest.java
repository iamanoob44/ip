package shagbot.tasks;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTest {
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @BeforeEach
    void setUp() {
        task1 = new Task("Task 1");
        task2 = new Task("Task 2");
        task3 = new Task("Task 3");
        task4 = new Task("Task 4");

        taskList = new TaskList(new Task[]{task1, task2, task3, task4});
    }

    @Test
    void testAddTask() {
        Task newTask = new Task("Task 5");
        taskList.addTask(newTask);
        Task[] tasks = taskList.getTasks();

        assertEquals(5, tasks.length, "Task list should contain 5 tasks");
        assertEquals(newTask, tasks[4], "Last task should be Task 5");
    }

    @Test
    void testDeleteTask() {
        Task deletedTask = taskList.deleteTask(2);
        assertEquals(task3, deletedTask, "Deleted task should be Task 3");

        Task[] tasks = taskList.getTasks();
        assertEquals(3, tasks.length, "Task list should contain 3 tasks after deletion");
        assertEquals(task1, tasks[0], "First task should be Task 1");
        assertEquals(task4, tasks[2], "Third task should be Task 4");
    }

    @Test
    void testGetTask() {
        assertEquals(task1, taskList.getTask(0), "Task at index 0 should be Task 1");
        assertEquals(task2, taskList.getTask(1), "Task at index 1 should be Task 2");
        assertEquals(task3, taskList.getTask(2), "Task at index 2 should be Task 3");
        assertEquals(task4, taskList.getTask(3), "Task at index 3 should be Task 4");
    }

    @Test
    void testMarkOrUnmarkTask() {
        taskList.markTask(0);
        taskList.unmarkTask(0);
        taskList.markTask(1);
        taskList.markTask(2);
        taskList.unmarkTask(1);
        taskList.markTask(2);
        taskList.markTask(1);

        assertFalse(taskList.getTask(0).isDone(), "Task 1 should be unmarked as not done");
        assertTrue(taskList.getTask(1).isDone(), "Task 2 should be marked as done");
        assertTrue(taskList.getTask(2).isDone(), "Task 3 should be marked as done");
        assertFalse(taskList.getTask(3).isDone(), "Task 4 should be unmarked as not done");
    }
}
