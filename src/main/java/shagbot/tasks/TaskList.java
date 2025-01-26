package shagbot.tasks;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskList {
    private final ArrayList<Task> tasks;


    /**
     * Default Constructor for TaskList class.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor for TaskList used for JUnit Testing only.
     *
     * @param initialTasks accepts a task array used for JUnit Testing
     */
    public TaskList(Task[] initialTasks) {
        this.tasks = new ArrayList<>(Arrays.asList(initialTasks));
    }


    /**
     * Adds a task to the list of tasks.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }


    /**
     * Deletes a task from the list by its index.
     *
     * @param index The index of the task to be deleted.
     * @return The task that is deleted.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves all tasks as an array.
     *
     * @return An array of all tasks in the list.
     */
    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }

    /**
     * Retrieves a specific task by index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getTasksForTesting() {
        return this.tasks;
    }


    /**
     * Marks a task as done by index.
     *
     * @param index The index of the task to mark.
     */
    public void markTask(int index) {
        tasks.get(index).mark();
    }

    /**
     * Marks a task as not done by index.
     *
     * @param index The index of the task to unmark.
     */
    public void unmarkTask(int index) {
        tasks.get(index).unmark();
    }

}
