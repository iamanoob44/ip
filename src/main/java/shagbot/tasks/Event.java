package shagbot.tasks;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

public class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructor for the {@code Event} class with the specified description of event,
     * start timing and end timing of the event.
     *
     * @param desc  The description of the task.
     * @param start The start time of the event.
     * @param end   The end time of the event.
     */
    public Event(String desc, String start, String end) {
        super(desc);
        this.start = parseStringToDateTime(start);
        this.end = parseStringToDateTime(end);
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
            throw new IllegalArgumentException("Invalid date format. Please use 'd/M/yyyy HHmm'.");
        }
    }

    /**
     * Retrieves the start timing of the event.
     *
     * @return The {@link LocalDateTime} object representing the event's start time.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Retrieves the end timing of the event.
     *
     * @return The {@link LocalDateTime} object representing the event's end time.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns a string representation of the {@code Event} Task.
     * The format includes the task type "[E]", the description from the
     * parent {@link Task} class, and the start and end timings.
     *
     * @return A string representation of the Event Task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start.format(OUTPUT_FORMATTER) +
                " to: " + end.format(OUTPUT_FORMATTER) + ")";
    }

}

