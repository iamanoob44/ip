package shagbot.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to find tasks that occurs on the specified date.
 */
public class TaskOnCommand extends Command {
    private static final String DATE_FORMAT = "dd/M/yyyy";
    private static final String INVALID_DATE_FORMAT_ERROR_MESSAGE = "OOPSIE!! Invalid date format: "
            + "Please use 'dd/M/yyyy'.";
    private final String dateString;

    /**
     * Constructor for the {@code TaskOnCommand} class.
     *
     * @param dateString The string representation of the date of the task.
     */
    public TaskOnCommand(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing a command.";
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
            ui.printTasksOnDate(date, taskList.getTasks());
        } catch (DateTimeParseException e) {
            throw new ShagBotException(INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }
        return true;
    }
}
