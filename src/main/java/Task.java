public class Task {
    private final String[] tasks;
    private int taskCount;


    /*
     * Constructor for Task
     */
    public Task() {
        this.tasks = new String[100]; // Holds 100 tasks
        this.taskCount = 0;
    }

    /**
     * Adds a task to the array tasks.
     *
     * @param task the task to add.
     */
    public void addTask(String task) {
            tasks[taskCount] = task;
            taskCount++;
    }

    /**
     * Retrieves all tasks stored in the array
     *
     * @return an array of tasks.
     */
    public String[] getTasks() {
        String[] currentTasks = new String[taskCount];
        for (int i = 0; i < taskCount; i++) {
            currentTasks[i] = tasks[i];
        }
        return currentTasks;
    }

}




