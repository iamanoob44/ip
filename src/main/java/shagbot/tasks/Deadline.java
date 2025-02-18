package shagbot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task of type 'Deadline'.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private static final String INVALID_DATE_MESSAGE =
            "Invalid date format. Please use 'dd/M/yyyy HHmm'.";
    private LocalDateTime byTiming;

    /**
     * Constructor for the {@code Deadline} class with specified description
     * of task and its deadline.
     *
     * @param desc     The description of the task.
     * @param byTiming The deadline for the task.
     */
    public Deadline(String desc, String byTiming) {
        super(desc);
        // Assert statements
        assert desc != null && !desc.trim().isEmpty() : "Deadline task description cannot be null or empty.";
        assert byTiming != null : "Deadline timing cannot be null.";
        assert !byTiming.trim().isEmpty() : "Deadline timing cannot be empty.";

        this.byTiming = parseStringToDateTime(byTiming);
    }

    /**
     * Parses a string representation of date and time into a {@link LocalDateTime} Object.
     *
     * @param dateTimeStr The string representation of date and time.
     * @return The parsed {@link LocalDateTime} object representing the deadline.
     * @throws IllegalArgumentException If the provided date or time format is invalid.
     */
    private LocalDateTime parseStringToDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_MESSAGE);
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
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        assert this.byTiming != null : "byTiming should never be null when calling toString().";
        return "[D]" + super.toString() + " (by: " + byTiming.format(OUTPUT_FORMATTER) + ")";
    }

    /**
     * Sets a new timing for the deadline task through the Snooze/Reschedule feature.
     *
     * @param newByTiming The {@link LocalDateTime} object representing the task's new deadline.
     */
    public void setByTiming(LocalDateTime newByTiming) {
        this.byTiming = newByTiming;
    }
}



