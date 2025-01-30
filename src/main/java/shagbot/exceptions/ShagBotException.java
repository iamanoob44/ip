package shagbot.exceptions;

/**
 * Represents a shagbotexception due to invalid commands or inputs.
 */
public class ShagBotException extends Exception {

    /**
     * Constructor for the {@code ShagBotException} class for specified exceptions.
     * This exception is thrown to indicate errors specific to {@link shagbot.Shagbot} operations.
     *
     * @param message Error message displayed for exceptions.
     */
    public ShagBotException(String message) {
        super(message);
    }

}

