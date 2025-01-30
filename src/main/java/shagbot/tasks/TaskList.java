package shagbot.tasks;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class that helps to manage the tasks in Shagbot.
 */
public class TaskList {
    private final ArrayList<Task> tasks;


    /**
     * Default Constructor for {@code TaskList} class.
     * Constructs an empty TaskList.
     *
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor for {@code TasKList} used for JUnit Testing only.
     * Constructs an {@code ArrayList} array with an initial set of tasks.
     *
     * @param initialTasks Accepts a task array used for JUnit Testing.
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
     * @return The removed task.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves all tasks in the lists as an array.
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

    /**
     * Retrieves the list of tasks.
     * This method is primarily intended for JUnit testing only.
     *
     * @return The ArrayList of tasks.
     */
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

