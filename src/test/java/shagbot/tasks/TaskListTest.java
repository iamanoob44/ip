package shagbot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TaskListTest {
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;

    @BeforeEach
    void setUp() {
        task1 = new Deadline("Task 1", "22/4/2002 2000");
        task2 = new Deadline("Task 2", "23/4/2002 2000");
        task3 = new Event("Task 3", "19/4/2002 1800", "21/4/2002 2006");
        task4 = new Todo("Task 4");

        taskList = new TaskList(new Task[]{task1, task2, task3, task4});
    }

    @Test
    void testAddTask() {
        Task newTask = new Todo("Task 5");
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
        // We are testing the getTask(int index), not the getTasks() method.
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
        taskList.markTask(3);
        taskList.unmarkTask(1);
        taskList.markTask(2);
        taskList.unmarkTask(3);
        taskList.markTask(0);
        taskList.markTask(1);
        taskList.unmarkTask(0);
        taskList.unmarkTask(3);

        // isDone() method used to retrieve completion status of the task, acts as a getter.
        assertFalse(task1.isDone(), "Task 1 should be unmarked as not done");
        assertTrue(task2.isDone(), "Task 2 should be marked as done");
        assertTrue(task3.isDone(), "Task 3 should be marked as done");
        assertFalse(task4.isDone(), "Task 4 should be unmarked as not done");
    }
}
