package shagbot.tasks;

/**
 * Represents a task of type 'Todo'.
 */
public class Todo extends Task {

    /**
     * Constructor for the {@code Todo} class with the specified description of that task.
     *
     * @param desc The description of the task.
     */
    public Todo(String desc) {
        super(validateDescription(desc));
    }

    /**
     * Returns a string representation of the {@code Todo} task.
     * The format includes the task type "[T]" and the description from the
     * parent {@link Task} class.
     *
     * @return A string representation of the {@code Todo} task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Validates the task description to ensure it is not null or empty.
     *
     * @param desc The task's description.
     * @return The validated description of the task.
     * @throws AssertionError If the description of the task is null or empty.
     */
    private static String validateDescription(String desc) {
        assert desc != null && !desc.trim().isEmpty() : "Description of Todo task cannot be null or empty.";
        return desc;
    }
}


