package shagbot.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.tasks.Todo;

/**
 * Represents a parser class to handle users' commands and inputs.
 */
public class Parser {
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructor for the {@code Parser} class.
     *
     * @param taskList The {@link TaskList} instance to help manage tasks.
     * @param ui       The {@link Ui} instance to handle user interactions.
     */
    public Parser(TaskList taskList, Ui ui) {
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Parses a user command and executes the corresponding action.
     *
     * @param command The user command to parse and process accordingly.
     * @return Returns True if {@link shagbot.Shagbot} continues running, false for exit.
     */
    public boolean parseCommand(String command) {
        try {
            if (command.equalsIgnoreCase("bye")) {
                ui.printExit();
                return false;
            } else if (command.equalsIgnoreCase("list")) {
                ui.printTaskList(taskList.getTasks());
            } else if (command.startsWith("mark ")) {
                handleMarkCommand(command, true);
            } else if (command.startsWith("unmark ")) {
                handleMarkCommand(command, false);
            } else if (command.startsWith("todo ")) {
                String description = command.substring(5).trim();
                Todo todo = new Todo(description);
                taskList.addTask(todo);
                ui.printTaskAdded(todo.toString(), taskList.getTasks().length);
            } else if (command.startsWith("deadline ")) {
                String[] comd = command.substring(9).split(" /by ");
                String description = comd[0].trim();
                String byTiming = comd[1].trim();

                try {
                    Deadline deadline = new Deadline(description, byTiming);
                    taskList.addTask(deadline);
                    ui.printTaskAdded(deadline.toString(), taskList.getTasks().length);
                } catch (IllegalArgumentException e) {
                    ui.printErrorMessage(e.getMessage());
                }

            } else if (command.startsWith("event ")) {
                String[] comd = command.substring(6).split(" /from | /to ");
                String description = comd[0].trim();
                String start = comd[1].trim();
                String end = comd[2].trim();
                Event event = new Event(description, start, end);
                taskList.addTask(event);
                ui.printTaskAdded(event.toString(), taskList.getTasks().length);
            } else if (command.startsWith("delete ")) {
                handleDeleteCommand(command);
            } else if (command.startsWith("task on ")) {
                String dateStr = command.substring(8).trim();
                try {
                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d/M/yyyy"));
                    ui.printTasksOnDate(date, taskList.getTasks());
                } catch (DateTimeParseException e) {
                    ui.printErrorMessage("Invalid date format. Please use 'dd/M/yyyy'.");
                }
            } else if (command.startsWith("find ")) {
                String keyword = command.substring(5).trim();
                Task[] foundTasks = searchForTasks(keyword);
                ui.printAnyMatchingTasks(foundTasks);
            } else if (command.trim().isEmpty()) {
                throw new ShagBotException("You did not enter anything... "
                        + "Please "
                        + "enter a valid input : todo, deadline, event, "
                        + "mark, unmark, delete, task on or bye!");
            } else {
                throw new ShagBotException("Oopsiee! Your input is"
                        + " invalid. Please try again by entering a valid input : todo, "
                        + "deadline, event, "
                        + "mark, unmark, delete, task on or bye!");
            }
        } catch (ShagBotException e) {
            ui.printErrorMessage(e.getMessage());
        } catch (Exception e) {
            ui.printErrorMessage("Weird, unrecognisable error... Please contact help services.");
        }
        return true;
    }


    /**
     * Marks or unmarks the task based on user's command.
     *
     * @param command The user's command indicating mark or unmark.
     * @param isMark  True for Mark or False for unmark, for a task.
     * @throws ShagBotException If there are invalid inputs.
     */
    protected void handleMarkCommand(String command, boolean isMark) throws ShagBotException {
        try {
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            int numOfTask = taskList.getTasks().length;
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
                    throw new ShagBotException("Task number is out of range! Enter a number from 1 to "
                            + numOfTask + ".");
                }
            }

            if (isMark) {
                taskList.markTask(taskIndex);
                ui.printTaskMarked(taskList.getTask(taskIndex));
            } else {
                taskList.unmarkTask(taskIndex);
                ui.printTaskUnmarked(taskList.getTask(taskIndex));
            }
        } catch (NumberFormatException e) {
            throw new ShagBotException("Invalid task number entered. Please try again!!");
        }
    }

    /**
     * Deletes a task based on the user's command.
     *
     * @param command The user's command indicating which task to delete.
     * @throws ShagBotException If there are invalid inputs.
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

            if (taskIndex >= taskList.getTasks().length) {
                if (taskList.getTasks().length == 0) {
                    throw new ShagBotException("No tasks at the moment.");
                } else {
                    throw new ShagBotException("Task number is out of range! Enter a number from 1 to "
                            + taskList.getTasks().length + ".");
                }
            }

            Task removedTask = taskList.deleteTask(taskIndex);
            ui.printTaskDeleted(removedTask, taskList.getTasks().length);
        } catch (NumberFormatException e) {
            throw new ShagBotException("Invalid task number entered. Please try again!");
        }
    }


    /**
     * Finds any matching tasks containing the keyword entered and returns an array of these matched tasks.
     *
     * @param keyword The keyword that was used to filter through the list of tasks.
     * @return An array of tasks that contains the keyword in their descriptions.
     */
    private Task[] searchForTasks(String keyword) {
        List<Task> tasksMatched = new ArrayList<>();
        for (Task task : taskList.getTasks()) {
            if (task.getDescription().contains(keyword)) {
                tasksMatched.add(task);
            }
        }
        return tasksMatched.toArray(new Task[0]);
    }

}



