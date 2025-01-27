package shagbot.tasks;

public class Todo extends Task {

    /**
     * Constructor for the Todo class with the specified description of the Todo task.
     *
     * @param desc The description of the task.
     */
    public Todo(String desc) {
        super(desc);
    }

    /**
     * Returns a string representation of the Todo Task.
     * The format includes the task type "[T]" and the description from the
     * parent Task class.
     *
     * @return A string representation of the Todo Task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}

