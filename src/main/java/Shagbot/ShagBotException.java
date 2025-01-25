package Shagbot;

public class ShagBotException extends Exception{

    /**
     * Constructor for the ShagBotException class.
     * This exception is thrown to indicate errors specific to ShagBot operations
     *
     * @param message Error message displayed for exceptions.
     */
    public ShagBotException(String message) {
        super(message);
    }

}
