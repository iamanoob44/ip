package shagbot.tasks;

public class Todo extends Task {

    /**
     * Constructor for the {@code Todo} class with the specified description of that task.
     *
     * @param desc The description of the task.
     */
    public Todo(String desc) {
        super(desc);
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


