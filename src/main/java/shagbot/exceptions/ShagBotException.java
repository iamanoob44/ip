package shagbot.exceptions;

/**
 * This class represents an exception caused by invalid commands or inputs in ShagBot.
 */
public class ShagBotException extends Exception {

    /**
     * Constructor for the {@code ShagBotException} class.
     * This exception is thrown to indicate errors specific to {@link shagbot.Shagbot} operations.
     *
     * @param message Error message displayed for exceptions.
     */
    public ShagBotException(String message) {
        super(message);
    }
}

