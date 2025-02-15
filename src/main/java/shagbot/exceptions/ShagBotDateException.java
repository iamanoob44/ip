package shagbot.exceptions;

/**
 * This handles exception pertaining to invalid dates and times.
 */
public class ShagBotDateException extends Exception {
    /**
     * Constructor for the {@code ShagBotDateException} class.
     * This exception is thrown to indicate errors specific to events in {@link shagbot.tasks.Event} ,
     * where the entered start date/time by the user is later than its corresponding end date/time,
     * which does not make sense.
     *
     * @param message Error message displayed for exceptions.
     */
    public ShagBotDateException(String message) {
        super(message);
    }
}
