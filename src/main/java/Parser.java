import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private final ManageTasks manageTasks;
    private final Ui ui;

    /**
     * Constructor for the Parser class.
     *
     * @param manageTasks The ManageTasks class to help manage tasks.
     * @param ui The Ui class to handle user interactions.
     */
    public Parser(ManageTasks manageTasks, Ui ui) {
        this.manageTasks = manageTasks;
        this.ui = ui;
    }

    /**
     * Parses a user command and executes the corresponding action.
     *
     * @param command The user command to parse.
     * @return Returns True if Shagbot continues running, false to exit.
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
                String[] comd = command.substring(9).split(" /by ");
                String description = comd[0].trim();
                String byTiming = comd[1].trim();

                try {
                    Deadline deadline = new Deadline(description, byTiming);
                    manageTasks.addTask(deadline);
                    ui.printTaskAdded(deadline.toString(), manageTasks.getTasks().length);
                } catch (IllegalArgumentException e) {
                    ui.printErrorMessage(e.getMessage());
                }

            } else if (command.startsWith("event ")) {
                String[] comd = command.substring(6).split(" /from | /to ");
                String description = comd[0].trim();
                String start = comd[1].trim();
                String end = comd[2].trim();
                Event event = new Event(description, start, end);
                manageTasks.addTask(event);
                ui.printTaskAdded(event.toString(), manageTasks.getTasks().length);
            } else if (command.startsWith("delete ")) {
                handleDeleteCommand(command);
            } else if (command.startsWith("task on ")) {
                String dateStr = command.substring(9).trim();
                try {
                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d/M/yyyy"));
                    ui.printTasksOnDate(date, manageTasks.getTasks());
                } catch (DateTimeParseException e) {
                    ui.printErrorMessage("Invalid date format. Please use 'dd/M/yyyy'.");
                }
            } else if (command.trim().isEmpty()) {
                throw new ShagBotException("You did not enter anything... " +
                        "Please " +
                        "enter a valid input : todo, deadline, event, " +
                        "mark, unmark, delete, task on or bye!");
            } else {
                throw new ShagBotException("Oopsiee! Your input is" +
                        " invalid. Please try again by entering a valid input : todo, " +
                        "deadline, event, " +
                        "mark, unmark, delete, task on or bye!");
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
     * @param command The command given, that is, mark or unmark.
     * @param isMark Mark or unmark for a task.
     * @throws ShagBotException Throws an error for invalid inputs.
     */
    private void handleMarkCommand(String command, boolean isMark) throws ShagBotException {
        try {
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            Integer numOfTask = manageTasks.getTasks().length;
            if (taskIndex < 0) {
                if (taskIndex == -1) {
                    throw new ShagBotException("Task number 0 is invalid! Task numbers start from 1.");
                }
                throw new ShagBotException("Task number cannot be less than 1! Please try again.");
            }
            if (taskIndex >= numOfTask) {
                if (numOfTask == 0) {
                    throw new ShagBotException("No tasks at the moment.");
                } else {
                    throw new ShagBotException("Task number is out of range! Enter a number from 1 to " +
                            numOfTask + ".");
                }
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

    /**
     *
     * @param command The command given, that is, delete
     * @throws ShagBotException Throws an error for invalid inputs
     */
    private void handleDeleteCommand(String command) throws ShagBotException {
        try {
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            if (taskIndex < 0) {
                if (taskIndex == -1) {
                    throw new ShagBotException("Task number 0 is invalid! Task numbers start from 1.");
                }
                throw new ShagBotException("Task number cannot be less than 1! Please try again.");
            }

            if (taskIndex >= manageTasks.getTasks().length) {
                if (manageTasks.getTasks().length == 0) {
                    throw new ShagBotException("No tasks at the moment.");
                } else {
                    throw new ShagBotException("Task number is out of range! Enter a number from 1 to " +
                            manageTasks.getTasks().length + ".");
                }
            }

            Task removedTask = manageTasks.deleteTask(taskIndex);
            ui.printTaskDeleted(removedTask, manageTasks.getTasks().length);
        } catch (NumberFormatException e) {
            throw new ShagBotException("Invalid task number entered. Please try again!");
        }
    }

}

