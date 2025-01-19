public class ManageTasks {
    private final Task[] tasks;
    private int taskCount;


    /**
     * Constructor for ManageTasks
     */
    public ManageTasks() {
        this.tasks = new Task[100];
        this.taskCount = 0;
    }

    /**
     * Adds a task to the array tasks
     *
     * @param task the task to add.
     */
    public void addTask(Task task) {
            tasks[taskCount] = task;
            taskCount++;
    }

    /**
     * Retrieves all tasks stored in the array
     *
     * @return an array of tasks.
     */
    public Task[] getTasks() {
        Task[] currentTasks = new Task[taskCount];
        System.arraycopy(tasks, 0, currentTasks, 0, taskCount);
        return currentTasks;
    }

    /**
     * Retrieves a specific task by index.
     *
     * @param index the index of the task to retrieve.
     * @return the task at the specified index.
     */
    public Task getTask(int index) {
            return tasks[index];
    }


    /**
     * Marks a task as done by index.
     *
     * @param index the index of the task to mark.
     */
    public void markTask(int index) {
            tasks[index].mark();
    }

    /**
     * Marks a task as not done by index.
     *
     * @param index the index of the task to unmark.
     */
    public void unmarkTask(int index) {
            tasks[index].unmark();
    }


}
