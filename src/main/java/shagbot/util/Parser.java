package shagbot.util;

import shagbot.commands.ByeCommand;
import shagbot.commands.Command;
import shagbot.commands.DeadlineCommand;
import shagbot.commands.DeleteCommand;
import shagbot.commands.EventCommand;
import shagbot.commands.FindCommand;
import shagbot.commands.ListCommand;
import shagbot.commands.MarkCommand;
import shagbot.commands.ReminderCommand;
import shagbot.commands.SnoozeCommand;
import shagbot.commands.TaskOnCommand;
import shagbot.commands.TodoCommand;
import shagbot.commands.UnmarkCommand;
import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;

/**
 * Represents a parser class to handle users' commands and inputs.
 */
public class Parser {
    private static final String BYE = "bye";
    private static final String LIST = "list";
    private static final String TODO = "todo";
    private static final String EVENT = "event";
    private static final String MARK = "mark";
    private static final String UNMARK = "unmark";
    private static final String DELETE = "delete";
    private static final String TASK = "task";
    private static final String FIND = "find";
    private static final String SNOOZE = "snooze";
    private static final String REMINDER = "reminder";
    private static final String DEADLINE = "deadline";
    private static final String NO_INPUT_ERROR_MESSAGE = "No input provided. Please enter a valid command.";
    private static final String INVALID_COMMANDS_ERROR_MESSAGE = "OOPSIE!! Unknown command. "
            + "Consider only these valid commands:\n\nlist, todo, deadline, event, "
            + "mark, unmark, delete, task on, find, snooze, reminder or bye.";
    private static final String UNEXPECTED_ERROR_MESSAGE = "OOPSIE!! Unexpected error occurred...";
    private static final String INVALID_TASK_COMMAND_ERROR_MESSAGE = "OOPSIE!! Invalid 'task' command. "
            + "Did you mean 'task on <date>'?";
    private static final String INVALID_TASK_NUMBER_ERROR_MESSAGE = "OOPSIE!! Invalid task number entered. "
            + "Please try again!!";
    private static final String TASK_INDEX_IS_NEGATIVE_ERROR_MESSAGE = "OOPSIE!! Task number cannot be less than 1! "
            + "Please try again.";
    private static final String SPECIFY_TASK_TO_SNOOZE_AND_NEW_DATE_ERROR_MESSAGE = "OOPSIE!! Please specify which "
            + "task to snooze and new date/time.";
    private static final String ENTER_TASK_NUMBER_ERROR_MESSAGE = "Please enter your task number behind your command. "
            + "Examples:\n\n" + "Mark <Number>\n" + "Unmark <Number>\n" + "Delete <Number>\n";

    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructor for the {@code Parser} class.
     *
     * @param taskList The {@link TaskList} instance to help manage tasks.
     * @param ui The {@link Ui} instance to handle user interaction.
     */
    public Parser(TaskList taskList, Ui ui) {
        assert taskList != null : "TaskList instance cannot be null.";
        assert ui != null : "Ui instance cannot be null.";
        // Initialise the variables.
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Parses a user command and executes the corresponding action.
     *
     * @param inputCommand The user's command to parse and process accordingly.
     * @return {@code true} if the application continues running, {@code false} if it exits.
     */
    public boolean parseCommand(String inputCommand) {
        try {
            Command parsedCommand = parseInputToCommand(inputCommand);
            return parsedCommand.executeCommand(taskList, ui);
        } catch (ShagBotException e) {
            ui.printErrorMessage(e.getMessage());
        } catch (Exception e) {
            ui.printErrorMessage(UNEXPECTED_ERROR_MESSAGE);
        }
        return true;
    }

    /**
     * Converts the raw input entered by user to a command.
     *
     * @param input The raw input entered.
     * @return The command, which will be parsed and handled accordingly.
     * @throws ShagBotException If the input is invalid or invalid command found.
     */
    Command parseInputToCommand(String input) throws ShagBotException {
        if (input == null || input.trim().isEmpty()) {
            throw new ShagBotException(NO_INPUT_ERROR_MESSAGE);
        }

        String[] parts = input.trim().split(" ", 2);
        String mainCommand = parts[0].toLowerCase();
        String description = parts.length > 1 ? parts[1].trim() : "";

        switch (mainCommand) {
        case BYE:
            return new ByeCommand();

        case LIST:
            return new ListCommand();

        case MARK:
            return new MarkCommand(parseTaskIndex(description));

        case UNMARK:
            return new UnmarkCommand(parseTaskIndex(description));

        case TODO:
            return new TodoCommand(description);

        case DEADLINE:
            return new DeadlineCommand(description);

        case EVENT:
            return new EventCommand(description);

        case DELETE:
            return new DeleteCommand(parseTaskIndex(description));

        case TASK:
            return parseTaskOnCommand(description);

        case FIND:
            return new FindCommand(description);

        case SNOOZE:
            return parseSnoozeCommand(description);

        case REMINDER:
            return new ReminderCommand();

        default:
            throw new ShagBotException(INVALID_COMMANDS_ERROR_MESSAGE);
        }
    }

    /**
     * Parses the task index from the provided command description.
     * <p>
     * This method expects the first token of the description to be a number,
     * which will be then converted to a zeroed-based index.
     * </p>
     *
     * @param description The command description containing the task number or index.
     * @return Zeroed-based index of the task.
     * @throws ShagBotException If task index is not within the range.
     */
    int parseTaskIndex(String description) throws ShagBotException {
        try {
            int taskNumber = Integer.parseInt(description.split(" ")[0]);
            if (taskNumber < 1) {
                throw new ShagBotException(TASK_INDEX_IS_NEGATIVE_ERROR_MESSAGE);
            }
            return taskNumber - 1;
        } catch (NumberFormatException e) {
            throw new ShagBotException(ENTER_TASK_NUMBER_ERROR_MESSAGE);
        }
    }

    /**
     * Parses a 'task on' command, with the keyword "task" already processed.
     *
     * @param description Command description which is expected to start with "on ".
     * @return A {@link TaskOnCommand} instance, coupled with the provided date string.
     * @throws ShagBotException If the command description does not start with "on ".
     */
    private Command parseTaskOnCommand(String description) throws ShagBotException {
        if (description.startsWith("on ")) {
            String dateString = description.substring(3).trim();
            return new TaskOnCommand(dateString);
        }
        throw new ShagBotException(INVALID_TASK_COMMAND_ERROR_MESSAGE);
    }

    /**
     * Parses a "snooze" command through the task index and new date/time information.
     *
     * @param description The command description containing task index and the rescheduled date/time.
     * @return A {@link SnoozeCommand} instance, coupled with its extracted task index and date/time info.
     * @throws ShagBotException If description does not contain both a task index and scheduling information, or
     *                          invalid task index.
     */
    private Command parseSnoozeCommand(String description) throws ShagBotException {
        String[] parts = description.split(" ", 2);
        if (parts.length < 2) {
            throw new ShagBotException(SPECIFY_TASK_TO_SNOOZE_AND_NEW_DATE_ERROR_MESSAGE);
        }
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(parts[0]) - 1;
        } catch (NumberFormatException e) {
            throw new ShagBotException(INVALID_TASK_NUMBER_ERROR_MESSAGE);
        }
        return new SnoozeCommand(taskIndex, parts[1].trim());
    }
}




