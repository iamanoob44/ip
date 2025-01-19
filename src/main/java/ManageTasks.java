import java.util.ArrayList;

public class ManageTasks {
    private final ArrayList<Task> tasks;
    private int taskCount;


    /**
     * Constructor for ManageTasks
     */
    public ManageTasks() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the arraylist of tasks
     *
     * @param task the task to add.
     */
    public void addTask(Task task) {
            tasks.add(task);
    }


    /**
     * Deletes a task from the arraylist by index.
     *
     * @param index the index of the task to be deleted.
     * @return the task that is deleted.
     */
    public Task deleteTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves all tasks stored in the arraylist
     *
     * @return an arraylist of tasks.
     */
    public Task[] getTasks() {
        return tasks.toArray(new Task[0]);
    }

    /**
     * Retrieves a specific task by index.
     *
     * @param index the index of the task to retrieve.
     * @return the task at the specified index.
     */
    public Task getTask(int index) {
            return tasks.get(index);
    }


    /**
     * Marks a task as done by index.
     *
     * @param index the index of the task to mark.
     */
    public void markTask(int index) {
            tasks.get(index).mark();
    }

    /**
     * Marks a task as not done by index.
     *
     * @param index the index of the task to unmark.
     */
    public void unmarkTask(int index) {
            tasks.get(index).unmark();
    }

}
