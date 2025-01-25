package shagbot;

import shagbot.tasks.Task;
import shagbot.util.Parser;
import shagbot.util.Storage;
import shagbot.util.TaskList;
import shagbot.util.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Shagbot {

    private final String botName;
    private final Ui ui;
    private final TaskList taskList;
    private final Parser parser;
    private final Storage storage;

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
     * Returns the greeting by the bot.
     */
    public static String printGreeting() {
        return "Hello! I'm shagbot\n" + "What can I do for you?\n";
    }

    /**
     * Returns the exit message by the bot.
     */
    public static String printExit() {
        return "Bye. Hope to see you again soon!\n";
    }

}
