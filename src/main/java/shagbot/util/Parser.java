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
            if (command == null || command.trim().isEmpty()) {
                throw new ShagBotException("No input provided. Please enter a valid command.");
            }

            String[] splitCommand = command.split(" ", 2);
            String mainCommand = splitCommand[0];
            String description = splitCommand.length > 1 ? splitCommand[1].trim() : "";

            switch (mainCommand) {
            case "bye":
                ui.printExit();
                return false;

            case "list":
                ui.printTaskList(taskList.getTasks());
                break;

            case "mark":
                handleMarkCommand(command, true);
                break;

            case "unmark":
                handleMarkCommand(command, false);
                break;

            case "todo":
                handleTodoCommand(description);
                break;

            case "deadline":
                handleDeadlineCommand(description);
                break;

            case "event":
                handleEventCommand(description);
                break;

            case "delete":
                handleDeleteCommand(command);
                break;

            case "task":
                if (description.startsWith("on ")) {
                    handleTaskOnCommand(description.substring(3).trim());
                } else {
                    throw new ShagBotException("OOPSIE!! Invalid 'task' command. Did you mean 'task on <date>'?");
                }
                break;

            case "find":
                handleFindCommand(description);
                break;

            default:
                throw new ShagBotException("OOPSIE!! Unknown command. "
                        + "Consider only these valid commands: list, todo, deadline, event, "
                        + "mark, unmark, delete, task on, find, or bye.");
            }
        } catch (ShagBotException e) {
            ui.printErrorMessage(e.getMessage());
        } catch (Exception e) {
            ui.printErrorMessage("OOPSIE!! Unexpected error occurred... Please contact help services.");
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
            if (command.trim().equalsIgnoreCase("MARK")
                    || command.trim().equalsIgnoreCase("UNMARK")) {
                throw new ShagBotException("OOPSIE!! Please enter a number behind.");
            }
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            int numOfTask = taskList.getTasks().length;
            if (taskIndex < 0) {
                if (taskIndex == -1) {
                    throw new ShagBotException("OOPSIE!! Task number 0 is invalid! Task numbers start from 1.");
                }
                throw new ShagBotException("OOPSIE!! Task number cannot be less than 1! Please try again.");
            }
            if (taskIndex >= numOfTask) {
                if (numOfTask == 0) {
                    throw new ShagBotException("No tasks at the moment.");
                } else {
                    throw new ShagBotException("OOPSIE!! Task number is out of range! Enter a number from 1 to "
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
            throw new ShagBotException("OOPSIE!! Invalid task number entered. Please try again!!");
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
            if (command.trim().equalsIgnoreCase("DELETE")) {
                throw new ShagBotException("OOPSIE!! Please enter a number behind");
            }
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            if (taskIndex < 0) {
                if (taskIndex == -1) {
                    throw new ShagBotException("OOPSIE!! Task number 0 is invalid! Task numbers start from 1.");
                }
                throw new ShagBotException("OOPSIE!! Task number cannot be less than 1! Please try again.");
            }

            if (taskIndex >= taskList.getTasks().length) {
                if (taskList.getTasks().length == 0) {
                    throw new ShagBotException("No tasks at the moment.");
                } else {
                    throw new ShagBotException("OOPSIE!! Task number is out of range! Enter a number from 1 to "
                            + taskList.getTasks().length + ".");
                }
            }

            Task removedTask = taskList.deleteTask(taskIndex);
            ui.printTaskDeleted(removedTask, taskList.getTasks().length);
        } catch (NumberFormatException e) {
            throw new ShagBotException("OOPSIE!! Invalid task number entered. Please try again!");
        }
    }

    /**
     * Handles a {@link Todo} task after parsing the description of the todo task.
     * @param description The description of the todo task.
     * @throws ShagBotException If the description format is invalid.
     */
    private void handleTodoCommand(String description) throws ShagBotException {
        if (description.isEmpty()) {
            throw new ShagBotException("OOPSIE!! Description for 'todo' task cannot be empty.");
        }
        Todo todo = new Todo(description);
        taskList.addTask(todo);
        ui.printTaskAdded(todo.toString(), taskList.getTasks().length);
    }

    /**
     * Handles a {@link Deadline} task after parsing the description of the deadline task and its due date.
     *
     * @param description The description of the deadline task , along with its due date.
     * @throws ShagBotException If the description format is invalid.
     */
    private void handleDeadlineCommand(String description) throws ShagBotException {
        String[] parts = description.split(" /by ", 2);
        if (parts.length < 2) {
            throw new ShagBotException("OOPSIE!! Invalid format. Use: deadline <description> /by <dd/M/yyyy hhmm>.");
        }
        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            taskList.addTask(deadline);
            ui.printTaskAdded(deadline.toString(), taskList.getTasks().length);
        } catch (IllegalArgumentException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    /**
     * Handles an {@link Event} task by parsing the description and start/end times of the event task.
     *
     * @param description The description of the event task, along with its start and end timings.
     * @throws ShagBotException If the description format is invalid.
     */
    private void handleEventCommand(String description) throws ShagBotException {
        String[] parts = description.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new ShagBotException("OOPSIE!! Invalid 'event' format. "
                    + "Use: event <description> /from dd/M/yyyy hhmm /to dd/M/yy hhmm.");
        }
        Event event = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        taskList.addTask(event);
        ui.printTaskAdded(event.toString(), taskList.getTasks().length);
    }

    /**
     * Handles the 'task on' command after parsing the specified date.
     *
     * @param dateString The date in "dd/M/yyyy" format to find tasks for.
     */
    private void handleTaskOnCommand(String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/M/yyyy"));
            ui.printTasksOnDate(date, taskList.getTasks());
        } catch (DateTimeParseException e) {
            ui.printErrorMessage("OOPSIE!! Invalid date format: Please use 'dd/M/yyyy'.");
        }
    }

    /**
     * Handles the 'find' command after parsing the provided keyword.
     *
     * @param keyword The specified keyword used to filter out and search for in task descriptions.
     */
    private void handleFindCommand(String keyword) {
        if (keyword.isEmpty()) {
            ui.printErrorMessage("OOPSIE!! Please enter 'find' <something> again.");
            return;
        }
        Task[] foundTasks = searchForTasks(keyword);
        ui.printAnyMatchingTasks(foundTasks);
    }

    /**
     * Searches for tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for in the tasks.
     * @return The array of tasks, as a {@link Task} array, that contains that keyword.
     */
    private Task[] searchForTasks(String keyword) {
        List<Task> retrievedTasks = new ArrayList<>();
        for (Task task : taskList.getTasks()) {
            if (task.getDescription().contains(keyword)) {
                retrievedTasks.add(task);
            }
        }
        return retrievedTasks.toArray(new Task[0]);
    }
}



