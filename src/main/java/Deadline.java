public class Deadline extends Task {
    private final String byTiming;

    /**
     * Constructor for the Deadline class
     *
     * @param desc the description of the task.
     * @param byTiming the deadline for the task.
     */
    public Deadline(String desc, String byTiming) {
        super(desc);
        this.byTiming = byTiming;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byTiming + ")";
    }
}

