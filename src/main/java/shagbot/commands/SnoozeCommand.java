package shagbot.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import shagbot.exceptions.ShagBotDateException;
import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to snooze or reschedule a task.
 */
public class SnoozeCommand extends Command {
    private static final String SNOOZE_DEADLINE_FAIL_ERROR_MESSAGE = "To snooze a deadline, use: snooze <index> "
            + "/by dd/M/yyyy HHmm";
    private static final String SNOOZE_EVENT_FAIL_ERROR_MESSAGE = "To snooze an event, use: snooze <index> "
            + "/from dd/M/yyyy HHmm /to dd/M/yyyy HHmm";
    private static final String INVALID_DATE_FORMAT_ERROR_MESSAGE = "OOPSIE!! Invalid date format: "
            + "Please use 'dd/M/yyyy HHmm'.";
    private static final String CANNOT_SNOOZE_TODO_ERROR_MESSAGE =
            "We only can snooze/reschedule deadlines or events.";
    private static final String DATE_FORMAT_WITH_TIME = "dd/M/yyyy HHmm";
    private static final String DEADLINE_HAS_BEEN_RESCHEDULED_TO =
            "  , deadline of this task has been rescheduled to: ";
    private static final String PLEASE_ENTER_A_NUMBER_FROM_1_TO = "OOPSIE!! Task number is out of range! "
            + "Please enter a number from 1 to ";
    private final int taskIndex;
    private final String dateTimeInfo;


    /**
     * Constructor for the {@code SnoozeCommand} class.
     *
     * @param taskIndex Index of the task to be snoozed.
     * @param dateTimeInfo String representation of the new scheduling information for the task.
     */
    public SnoozeCommand(int taskIndex, String dateTimeInfo) {
        this.taskIndex = taskIndex;
        this.dateTimeInfo = dateTimeInfo;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing command.";
        int numOfTasks = taskList.getTasks().length;
        if (taskIndex < 0 || taskIndex >= numOfTasks) {
            throw new ShagBotException(PLEASE_ENTER_A_NUMBER_FROM_1_TO + numOfTasks + ".");
        }
        Task taskToSnooze = taskList.getTask(taskIndex);
        if (taskToSnooze instanceof Deadline) {
            return snoozeDeadline((Deadline) taskToSnooze, ui);
        } else if (taskToSnooze instanceof Event) {
            return snoozeEvent((Event) taskToSnooze, ui);
        } else {
            throw new ShagBotException(CANNOT_SNOOZE_TODO_ERROR_MESSAGE);
        }
    }

    /**
     * Reschedules a {@link Deadline} task to its new date and time.
     *
     * @param deadline Deadline task to be snoozed or rescheduled.
     * @param ui The Ui instance to display message.
     * @return {@code true} if the deadline was successfully rescheduled, else {@code false}.
     * @throws ShagBotException If the date/time format is invalid.
     */
    private boolean snoozeDeadline(Deadline deadline, Ui ui) throws ShagBotException {
        if (!dateTimeInfo.startsWith("/by ")) {
            throw new ShagBotException(SNOOZE_DEADLINE_FAIL_ERROR_MESSAGE);
        }
        String newDateStr = dateTimeInfo.substring(4).trim();
        LocalDateTime newByTiming;
        try {
            newByTiming = LocalDateTime.parse(newDateStr, DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_TIME));
        } catch (DateTimeParseException e) {
            throw new ShagBotException(INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }
        deadline.setByTiming(newByTiming);
        String message = "For this task: " + deadline.getDescription() + DEADLINE_HAS_BEEN_RESCHEDULED_TO
                + newByTiming.format(DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_TIME));
        ui.displayMessage(message);
        return true;
    }

    /**
     * Reschedules a {@link Event} task to its new start and end dates and timings.
     *
     * @param event Event task to be snoozed or rescheduled.
     * @param ui The Ui instance to display message.
     * @return {@code true} If the event was successfully rescheduled, else {@code false}.
     * @throws ShagBotException If the date/time format is invalid.
     */
    private boolean snoozeEvent(Event event, Ui ui) throws ShagBotException {
        String[] parts = dateTimeInfo.split(" /to ");
        if (!parts[0].startsWith("/from ") || parts.length < 2) {
            throw new ShagBotException(SNOOZE_EVENT_FAIL_ERROR_MESSAGE);
        }
        String startString = parts[0].substring(6).trim();
        String endString = parts[1].trim();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_TIME);
        LocalDateTime newStart;
        LocalDateTime newEnd;
        try {
            newStart = LocalDateTime.parse(startString, formatter);
            newEnd = LocalDateTime.parse(endString, formatter);
        } catch (DateTimeParseException e) {
            throw new ShagBotException(SNOOZE_EVENT_FAIL_ERROR_MESSAGE);
        }
        try {
            event.setStart(newStart);
            event.setEnd(newEnd);
            event.validateDate();
        } catch (ShagBotDateException e) {
            throw new ShagBotException(e.getMessage());
        }

        String message = "This event has been rescheduled:  " + event.getDescription() + "\n\nFrom: "
                + newStart.format(formatter) + "\nTo: " + newEnd.format(formatter);
        ui.displayMessage(message);
        return true;
    }
}
