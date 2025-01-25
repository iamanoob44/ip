class Deadline extends Task {
    private final String byTiming;

    /**
     * Constructor for the Deadline class.
     *
     * @param desc The description of the task.
     * @param byTiming The deadline for the task.
     */
    public Deadline(String desc, String byTiming) {
        super(desc);
        this.byTiming = byTiming;
    }

    public String getByTiming() {
        return byTiming;
    }

    /**
     * Returns a string representation of the Deadline Task.
     * The format includes the task type "[D]", the description from the
     * parent Task class, and the deadline timing.
     *
     * @return A string representation of the Deadline Task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byTiming + ")";
    }
}
