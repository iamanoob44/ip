public class Todo extends Task{

    /**
     * Constructor for the Todo class
     *
     * @param desc the description of the task.
     */
    public Todo(String desc) {
        super(desc);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
