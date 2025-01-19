public class Parser {
    private final ManageTasks manageTasks;
    private final Ui ui;

    /**
     * Constructor for Parser class
     *
     * @param manageTasks the ManageTasks to help manage tasks.
     * @param ui the Ui to handle user interactions.
     */
    public Parser(ManageTasks manageTasks, Ui ui) {
        this.manageTasks = manageTasks;
        this.ui = ui;
    }

    /**
     * Parses a user command and executes the corresponding action.
     *
     * @param command the user command to parse.
     * @return true if Shagbot continues running, false to exit.
     */
    public boolean parseCommand(String command) {
        if (command.equalsIgnoreCase("bye")) {
            ui.printExit();
            return false;
        } else if (command.equalsIgnoreCase("list")) {
            ui.printTaskList(manageTasks.getTasks());
        } else if (command.startsWith("mark ")) {
            handleMarkCommand(command, true);
        } else if (command.startsWith("unmark ")) {
            handleMarkCommand(command, false);
        } else {
            manageTasks.addTask(command);
            ui.printTaskAdded(command);
        }
        return true;
    }


    /**
     * Mark or unmark the task.
     *
     * @param command the command given.
     * @param isMark mark or unmark for a task.
     */
    private void handleMarkCommand(String command, boolean isMark) {
        int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
        if (isMark) {
            manageTasks.markTask(taskIndex);
            ui.printTaskMarked(manageTasks.getTask(taskIndex));
        } else {
            manageTasks.unmarkTask(taskIndex);
            ui.printTaskUnmarked(manageTasks.getTask(taskIndex));
        }
    }

}

