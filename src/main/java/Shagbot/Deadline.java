package Shagbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private final LocalDateTime byTiming;
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructor for the Deadline class.
     *
     * @param desc The description of the task.
     * @param byTiming The deadline for the task.
     */
    public Deadline(String desc, String byTiming) {
        super(desc);
        this.byTiming = parseStringtoDateTime(byTiming);
    }

    /**
     *
     * @param dateTimeStr The string representation of date and time.
     * @return The date and timing represented in local date format.
     */
    private LocalDateTime parseStringtoDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'd/M/yyyy HHmm'.");
        }
    }

    /**
     *
     * @return The date and timing of task.
     */
    public LocalDateTime getByTiming() {
        return byTiming;
    }

    /**
     * Returns a string representation of the Deadline Task.
     * The format includes the task type "[D]", the description from the
     * parent Task class, and the deadline date and timing.
     *
     * @return A string representation of the Deadline Task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byTiming.format(OUTPUT_FORMATTER) + ")";
    }
}
