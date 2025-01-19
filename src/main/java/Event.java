public class Event extends Task {
    private final String start;
    private final String end;

    /**
     * Constructor for Event task
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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }
}
