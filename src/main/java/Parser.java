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
        try {
            if (command.equalsIgnoreCase("bye")) {
                ui.printExit();
                return false;
            } else if (command.equalsIgnoreCase("list")) {
                ui.printTaskList(manageTasks.getTasks());
            } else if (command.startsWith("mark ")) {
                handleMarkCommand(command, true);
            } else if (command.startsWith("unmark ")) {
                handleMarkCommand(command, false);
            } else if (command.startsWith("todo ")) {
                String description = command.substring(5).trim();
                Todo todo = new Todo(description);
                manageTasks.addTask(todo);
                ui.printTaskAdded(todo.toString(), manageTasks.getTasks().length);
            } else if (command.startsWith("deadline ")) {
                String[] cmd = command.substring(9).split(" /by ");
                String description = cmd[0].trim();
                String byTiming = cmd[1].trim();
                Deadline deadline = new Deadline(description, byTiming);
                manageTasks.addTask(deadline);
                ui.printTaskAdded(deadline.toString(), manageTasks.getTasks().length);
            } else if (command.startsWith("event ")) {
                String[] cmd = command.substring(6).split(" /from | /to ");
                String description = cmd[0].trim();
                String start = cmd[1].trim();
                String end = cmd[2].trim();
                Event event = new Event(description, start, end);
                manageTasks.addTask(event);
                ui.printTaskAdded(event.toString(), manageTasks.getTasks().length);
            } else if (command.trim().isEmpty()) {
                throw new ShagBotException("You did not enter anything... " +
                        "Please " +
                        "enter a valid input : todo, deadline, event, " +
                        "mark, unmark, or bye!");
            } else {
                throw new ShagBotException("Oopsiee! Your input is" +
                        " invalid. Please try again by entering a valid input : todo, " +
                        "deadline, event, " +
                        "mark, unmark, or bye!");
            }

        } catch (ShagBotException e) {
            ui.printErrorMessage(e.getMessage());
        } catch (Exception e) {
            ui.printErrorMessage("Weird, unrecognisable error... Please contact help services.");
        }
        return true;
    }


    /**
     * Mark or unmark the task.
     *
     * @param command the command given.
     * @param isMark mark or unmark for a task.
     */
    private void handleMarkCommand(String command, boolean isMark) throws ShagBotException {
        try {
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            Integer numOfTask = manageTasks.getTasks().length;
            if (taskIndex < 0 || taskIndex >= manageTasks.getTasks().length) {
                throw new ShagBotException("Task number is out of range! " +
                        "Enter from 1 to " + numOfTask + ".");
            }
            if (isMark) {
                manageTasks.markTask(taskIndex);
                ui.printTaskMarked(manageTasks.getTask(taskIndex));
            } else {
                manageTasks.unmarkTask(taskIndex);
                ui.printTaskUnmarked(manageTasks.getTask(taskIndex));
            }
        } catch (NumberFormatException e) {
            throw new ShagBotException("Invalid task number entered. Please try again!!");
        }
    }

}
