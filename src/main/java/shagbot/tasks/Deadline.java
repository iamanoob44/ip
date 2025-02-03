package shagbot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that is of the 'Deadline' category.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM"
                    + " dd yyyy, h:mma");
    private final LocalDateTime byTiming;
    /**
     * Constructor for the {@code Deadline} class with specified description
     * of task and its deadline.
     *
     * @param desc     The description of the task.
     * @param byTiming The deadline for the task.
     */
    public Deadline(String desc, String byTiming) {
        super(desc);
        this.byTiming = parseStringToDateTime(byTiming);
    }

    /**
     * Parses a string representation of date and time into a {@link LocalDateTime} Object.
     *
     * @param dateTimeStr The string representation of date and time.
     * @return The parsed {@link LocalDateTime} object representing the deadline.
     * @throws IllegalArgumentException if provided date or time is invalid.
     */
    private LocalDateTime parseStringToDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'dd/M/yyyy HHmm'.");
        }
    }

    /**
     * Retrieves the date and time associated with the task.
     *
     * @return The {@link LocalDateTime} object representing the task's deadline.
     */
    public LocalDateTime getByTiming() {
        return byTiming;
    }

    /**
     * Returns a string representation of the {@code Deadline} task.
     * The format includes the task type "[D]", the description from the
     * parent {@link Task} class, and the date and timing of the deadline.
     *
     * @return A string representation of the Deadline Task.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byTiming.format(OUTPUT_FORMATTER) + ")";
    }

}



