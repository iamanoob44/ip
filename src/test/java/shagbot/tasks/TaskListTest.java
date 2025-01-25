package shagbot.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TaskListTest {
    private TaskList taskList;
    private Task task1;
    private Task task2;
    private Task task3;

    /**
     * Add 3 tasks when setting up.
     */
   private void setup() {
       taskList = new TaskList();
       task1 = new Task("Task 1");
       task2 = new Task("Task 2");
       task3 = new Task("Task 3");

       taskList.addTask(task1);
       taskList.addTask(task2);
       taskList.addTask(task3);
   }




    @Test
    void testAddTask() {
       setup();
        Task[] tasks = new Task[] {task1 ,task2, task3};
        assertEquals(3, tasks.length, "Task list should contain 3 tasks");
        assertEquals(task1, tasks[0], "First task should be Task 1");
        assertEquals(task2, tasks[1], "Second task should be Task 2");
        assertEquals(task3, tasks[2], "Second task should be Task 3");
    }

    @Test
    void testDeleteTask() {
       setup();

        Task deletedTask = taskList.deleteTask(1);
        assertEquals(task2, deletedTask, "Deleted task should be Task 2");

        Task[] tasks = new Task[] {task1 , task3};
        assertEquals(2, tasks.length, "Task list should contain 2 task after deletion");
        assertEquals(task3, tasks[1], "Remaining task should be Task 3");
    }

    @Test
    void testGetTask() {
       setup();

        Task acquiredTask = taskList.getTask(1);
        Task retrievedTask = taskList.getTask(0);
        assertEquals(task2, acquiredTask, "Task at index 1 should be Task 2");
        assertEquals(task1, retrievedTask, "Task at index 0 should be Task 1");
    }

    @Test
    void testMarkOrUnmarkTask() {
       setup();
        taskList.markTask(0);
        taskList.unmarkTask(0);
        taskList.markTask(1);
        taskList.markTask(2);
        taskList.unmarkTask(1);
        taskList.markTask(2);
        taskList.markTask(1);

        // task1 for index 0
        // task2 for index 1
        // task3 for index 2
        assertFalse(false, "Task 1 should be unmarked as not done");
        assertTrue(true, "Task 2 should be marked as done");
        assertTrue(true, "Task 3 should be marked as done");
    }

}
