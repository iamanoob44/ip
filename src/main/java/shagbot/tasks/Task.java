package shagbot.tasks;

/**
 * Represents the Parent class to handle all task types: todos, deadlines and events.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Constructor for the {@code Task} class with specified description and
     * the completion status of the task, initially marked as not done.
     *
     * @param desc The description of the task.
     */
    public Task(String desc) {
        this.description = desc;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Retrieves the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Retrieves the completion status of the task.
     *
     * @return True for task that is marked and false for unmarked.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns a string representation of the task.
     * The format includes the completion status (marked as "X" for done, or a space for not done)
     * and the task's description.
     *
     * @return The string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + (isDone ? "X" : " ") + "] " + description;
    }

}

