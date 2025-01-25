public class Event extends Task {
    private final String start;
    private final String end;

    /**
     * Constructor for the Event class.
     *
     * @param desc the description of the task.
     * @param start the start time of the event.
     * @param end the end time of the event.
     */
    public Event(String desc, String start, String end) {
        super(desc);
        this.start = start;
        this.end = end;
    }

    /**
     * Returns a string representation of the Event Task.
     * The format includes the task type "[E]", the description from the
     * parent Task class, and the start and end timings.
     *
     * @return A string representation of the Event Task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
