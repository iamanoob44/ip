package shagbot.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import shagbot.exceptions.ShagBotDateException;

/**
 * Represents a task of type 'Event'.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("dd/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private static final String INVALID_DATE_FORMAT =
            "Invalid date format. Please use 'dd/M/yyyy HHmm'.";
    private LocalDateTime start;
    private LocalDateTime end;

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
        // Assert statements
        assert desc != null && !desc.trim().isEmpty() : "Description of event cannot be null or empty.";
        assert start != null && !start.trim().isEmpty() : "Event start time cannot be null or empty.";
        assert end != null && !end.trim().isEmpty() : "Event end time cannot be null or empty.";
        // Parse start and end times
        this.start = parseStringToDateTime(start);
        this.end = parseStringToDateTime(end);
    }

    /**
     * Parses a string representation of date and time into a {@link LocalDateTime} Object.
     *
     * @param dateTimeStr The string representation of the date and time.
     * @return The parsed {@link LocalDateTime} object representing the date and time.
     * @throws IllegalArgumentException If the provided date or time is invalid.
     */
    private LocalDateTime parseStringToDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_DATE_FORMAT);
        }
    }

    /**
     * Retrieves the start date/time of the event.
     *
     * @return The {@link LocalDateTime} object representing the event's start date/time.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Retrieves the end date/time of the event.
     *
     * @return The {@link LocalDateTime} object representing the event's end date/time.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns a string representation of the {@code Event} Task.
     * The format includes the task type "[E]", the description from the
     * parent {@link Task} class, and the start and end date/times.
     *
     * @return A string representation of the Event Task.
     */
    @Override
    public String toString() {
        //Assert statements
        assert start != null : "Event start time should not be null.";
        assert end != null : "Event end time should not be null.";
        return "[E]" + super.toString() + " (from: " + start.format(OUTPUT_FORMATTER)
                + " to: " + end.format(OUTPUT_FORMATTER) + ")";
    }

    /**
     * Sets the new start date/time for the event in the list.
     *
     * @param newStart The {@link LocalDateTime} object representing the event task's new start date/time.
     */
    public void setStart(LocalDateTime newStart) {
        this.start = newStart;
    }

    /**
     * Sets the new end date/time for the event in the list.
     *
     * @param newEnd The {@link LocalDateTime} object representing the event task's new end date/time.
     */
    public void setEnd(LocalDateTime newEnd) {
        this.end = newEnd;
    }

    /**
     * Verifies that start date/time is earlier than the end date/time entered.
     */
    public void validateDate() throws ShagBotDateException {
        if (!this.start.isBefore(this.end)) {
            throw new ShagBotDateException("Start date and time "
                    + "must be before end date and time.");
        }
    }
}

