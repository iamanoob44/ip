import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Event extends Task {
    private final LocalDateTime start;
    private final LocalDateTime end;
    private static final DateTimeFormatter INPUT_FORMATTER =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    /**
     * Constructor for the Event class.
     *
     * @param desc The description of the task.
     * @param start The start time of the event.
     * @param end The end time of the event.
     */
    public Event(String desc, String start, String end) {
        super(desc);
        this.start = parseStringToDateTime(start);
        this.end = parseStringToDateTime(end);
    }

    /**
     *
     * @param dateTimeStr The string representation of date and time.
     * @return The date and timing represented in local date format.
     */
    private LocalDateTime parseStringToDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Please use 'd/M/yyyy HHmm'.");
        }
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
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
        return "[E]" + super.toString() + " (from: " + start.format(OUTPUT_FORMATTER)+
                " to: " + end.format(OUTPUT_FORMATTER) + ")";
    }
}
