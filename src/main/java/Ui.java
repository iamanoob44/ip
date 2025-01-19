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
        System.out.println("     ____________________________________________________________");
        System.out.println("     Hello! I'm " + botName);
        System.out.println("     What can I do for you?");
        System.out.println("     ____________________________________________________________");
    }

    /*
     * Returns the exit message.
     */
    public void printExit() {
        System.out.println("     ____________________________________________________________");
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println("     ____________________________________________________________");
    }

    /**
     * Echoes input by user back to the user.
     *
     * @param input input by user to echo.
     */
    public void echo(String input) {
        System.out.println("     ____________________________________________________________");
        System.out.println("     " + input);
        System.out.println("     ____________________________________________________________");
    }

    /**
     * Prints a message when task is added.
     *
     * @param task the task to add.
     */
    public void printTaskAdded(String task) {
        System.out.println("     ____________________________________________________________");
        System.out.println("     added: " + task);
        System.out.println("     ____________________________________________________________");
    }


    /**
     * Prints the list of tasks.
     *
     * @param tasks an array of added tasks to display.
     */
    public void printTaskList(String[] tasks) {
        System.out.println("     ____________________________________________________________");
        if (tasks.length == 0) {
            System.out.println("     No tasks found.");
        } else {
            for (int i = 0; i < tasks.length; i++) {
                System.out.println("     " + (i + 1) + ". " + tasks[i]);
            }
        }
        System.out.println("     ____________________________________________________________");
    }

}
