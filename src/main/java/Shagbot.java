import java.util.Scanner;

public class Shagbot {

    private final String botName;
    private final Ui ui;

    /**
     * Constructor for Shagbot
     *
     * @param name the name of the chatbot.
     */
    public Shagbot(String name) {
        botName = name;
        this.ui = new Ui(name);
    }

    /**
     * Starts the chatbot and handles user input.
     */
    public void start() {
        ui.printGreeting();
        Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine().trim();
            if (userInput.equalsIgnoreCase("Bye")) {
                ui.printExit();
                break;
            } else {
                ui.echo(userInput);
            }
        }
        scanner.close();
    }



    public static void main(String[] args) {
        Shagbot shagbot = new Shagbot("Shagbot");
        shagbot.start();
    }

    /*
     * Returns the greeting.
     */
    public static String printGreeting() {
        return "Hello! I'm Shagbot\n" + "What can I do for you?\n";
    }

    /*
     * Returns the exit message.
     */
    public static String printExit() {
        return "Bye. Hope to see you again soon!\n";
    }

}





