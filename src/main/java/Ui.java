public class Ui {
    private final String botName;

    /**
     * Constructor for Ui object for the given chatbot name.
     *
     * @param botName the name of the chatbot.
     */
    public Ui(String botName) {
        this.botName = botName;
    }

    /*
     * Returns the greeting.
     */
    public void printGreeting() {
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm " + botName);
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    /*
     * Returns the exit message.
     */
    public void printExit() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    /**
     * Echoes input by user back to the user.
     *
     * @param input input by user to echo.
     */
    public void echo(String input) {
        System.out.println("____________________________________________________________");
        System.out.println(input);
        System.out.println("____________________________________________________________");
    }
    
}
