package shagbot.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class handles the "task on" command entered by user.
 */
public class HandleTaskOnCommand extends Commands {
    private static final String DATE_FORMAT = "dd/M/yyyy";
    private static final String INVALID_DATE_FORMAT_ERROR_MESSAGE = "OOPSIE!! Invalid date format: "
            + "Please use 'dd/M/yyyy'.";
    private final String dateString;

    /**
     * Constructor for the {@code HandleTaskOnCommand} class.
     *
     * @param dateString The string representation of the date of the task.
     */
    public HandleTaskOnCommand(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui cannot be null.";
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
            ui.printTasksOnDate(date, taskList.getTasks());
        } catch (DateTimeParseException e) {
            throw new ShagBotException(INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }
        return true;
    }
}
