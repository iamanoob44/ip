package shagbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Parser;
import shagbot.util.Storage;
import shagbot.util.Ui;

/**
 * This class represents Shagbot, which is a chatbot program.
 * <p>
 * The Shagbot class serves as the main starting point to run the chatbot. It initialises
 * essential classes such as the UI, TaskList, Parser and Storage Classes.
 * This class also handles the main execution loop for user interaction with Shagbot.
 *
 * @author Chin Chong
 */
public class Shagbot {

    private final String botName;
    private final Ui ui;
    private final TaskList taskList;
    private final Parser parser;
    private final Storage storage;

    /**
     * Constructor for the Shagbot class with its specified chatbot name.
     * Initialises the Ui, taskList, parser and storage objects.
     * It also loads any previously saved tasks from the specified file.
     *
     * @param name The name of the chatbot.
     */
    public Shagbot(String name) {
        botName = name;
        this.ui = new Ui(name);
        this.taskList = new TaskList();
        this.parser = new Parser(taskList, ui);
        this.storage = new Storage("./data/dataoftasks.txt");

        // Load any saved tasks when startup
        try {
            ArrayList<Task> tasks = storage.load();
            for (Task task : tasks) {
                taskList.addTask(task);
            }
        } catch (IOException e) {
            ui.printErrorMessage("Failed to load tasks: " + e.getMessage());
        }
    }

    /**
     * Starts Shagbot's user-interaction loop.
     */
    public void start() {
        ui.printGreeting();
        Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine().trim();
            if (!parser.parseCommand(userInput)) {
                break;
            }

            // Save any tasks after each command
            try {
                storage.save(new ArrayList<>(List.of(taskList.getTasks())));
            } catch (IOException e) {
                ui.printErrorMessage("Failed to save tasks: " + e.getMessage());
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        Shagbot shagbot = new Shagbot("shagbot");
        shagbot.start();
    }

    /**
     * Returns the greeting message from Shagbot.
     *
     * @return The greeting by Shagbot.
     */
    public static String printGreeting() {
        return "Hello! I'm shagbot\n" + "What can I do for you?\n";
    }

    /**
     * Returns the exit message from Shagbot.
     *
     * @return The exit message by Shagbot.
     */
    public static String printExit() {
        return "Bye. Hope to see you again soon!\n";
    }

}


