package shagbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * </p>
 *
 * @author Chin Chong
 */
public class Shagbot {

    private static final String BYE_MESSAGE = "Bye! Hope to see you again soon!";
    private static final String FAILED_TO_SAVE_TASKS_MESSAGE = "Failed to save tasks: ";
    private final String botName;
    private final Ui ui;
    private final TaskList taskList;
    private final Parser parser;
    private final Storage storage;

    /**
     * Constructor for the {@code Shagbot} class with its specified chatbot name.
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

        // Load any saved tasks when startup the program
        try {
            ArrayList<Task> tasks = storage.loadSavedTasks();
            for (Task task : tasks) {
                taskList.addTask(task);
            }
        } catch (IOException e) {
            ui.printErrorMessage("Failed to load tasks: " + e.getMessage());
        }
    }

    /**
     * Retrieves the response based on the user's input.
     *
     * @param input The command entered by the user.
     * @return The response retrieved, which is of string representation.
     */
    public String getResponse(String input) {
        assert input != null : "input cannot be null";
        try {
            // Execute the command and return the response
            if (!parser.parseCommand(input)) {
                // Printed on terminal if run on IntelliJ, else , GUI app will close by itself
                return BYE_MESSAGE;
            }
            storage.saveTasksToFile(new ArrayList<>(List.of(taskList.getTasks())));
            return ui.getLastMessage(); // Fetches the last response from Shagbot
        } catch (IOException e) {
            return FAILED_TO_SAVE_TASKS_MESSAGE + e.getMessage();
        }
    }
}





