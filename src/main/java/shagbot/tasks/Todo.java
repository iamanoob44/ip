package shagbot.tasks;

/**
 * Represents a task that is of the 'Todo' category.
 */
public class Todo extends Task {

    /**
     * Constructor for the {@code Todo} class with the specified description of that task.
     *
     * @param desc The description of the task.
     */
    public Todo(String desc) {
        super(desc);
        assert desc != null && !desc.trim().isEmpty() : "Description of Todo task cannot be null or empty.";
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
}


