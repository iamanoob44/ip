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
    public void printTaskAdded(String task, int taskcount) {
        System.out.println("     ____________________________________________________________");
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskcount + " tasks in the list.");
        System.out.println("     ____________________________________________________________");
    }


    /**
     * Prints the list of tasks.
     *
     * @param tasks an array of added tasks to display.
     */
    public void printTaskList(Task[] tasks) {
        System.out.println("     ____________________________________________________________");
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println("     " + (i + 1) + "." + tasks[i]);
        }
        System.out.println("     ____________________________________________________________");
    }


    /**
     * Prints a message indicating that the task has been marked as done.
     *
     * @param task the task that was marked as done.
     */
    public void printTaskMarked(Task task) {
        System.out.println("     ____________________________________________________________");
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("     " + task);
        System.out.println("     ____________________________________________________________");
    }

    /**
     * Prints message indicating that the task has been marked as not done.
     *
     * @param task the task that was marked as not done.
     */
    public void printTaskUnmarked(Task task) {
        System.out.println("     ____________________________________________________________");
        System.out.println("     OK, I've marked this task as not done yet:");
        System.out.println("     " + task);
        System.out.println("     ____________________________________________________________");
    }


    /**
     * Prints messages when errors are encountered
     *
     * @param message the error message
     */
    public void printErrorMessage(String message) {
        System.out.println("     WOOP WOOP!!! " + message);
    }


}
