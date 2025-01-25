import java.util.Scanner;

public class Shagbot {

    private final String botName;
    private final Ui ui;
    private final ManageTasks manageTasks;
    private final Parser parser;

    /**
     * Constructor for the Shagbot class.
     *
     * @param name The name of the chatbot.
     */
    public Shagbot(String name) {
        botName = name;
        this.ui = new Ui(name);
        this.manageTasks = new ManageTasks();
        this.parser = new Parser(manageTasks, ui);
    }

    /**
     * Starts the chatbot and handles user input.
     */
    public void start() {
        ui.printGreeting();
        Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine().trim();
            if (!parser.parseCommand(userInput)) {
                break;
            }
        }
        scanner.close();
    }



    public static void main(String[] args) {
        Shagbot shagbot = new Shagbot("Shagbot");
        shagbot.start();
    }

    /**
     * Returns the greeting by the bot.
     */
    public static String printGreeting() {
        return "Hello! I'm Shagbot\n" + "What can I do for you?\n";
    }

    /**
     * Returns the exit message by the bot.
     */
    public static String printExit() {
        return "Bye. Hope to see you again soon!\n";
    }

}

