package shagbot.commands;

import shagbot.exceptions.ShagBotDateException;
import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Event;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to add events.
 */
public class EventCommand extends Command {
    private static final String INVALID_EVENT_FORMAT_ERROR_MESSAGE =
            "OOPSIE!! Invalid 'event' format. " + "Use: event <description> /from dd/M/yyyy hhmm /to dd/M/yyyy hhmm.";
    private final String description;

    /**
     * Constructor for the {@code EventCommand} class.
     *
     * @param description Description of the {@link Event} task.
     */
    public EventCommand(String description) {
        this.description = description;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing command.";
        String[] parts = description.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new ShagBotException(INVALID_EVENT_FORMAT_ERROR_MESSAGE);
        }
        try {
            Event event = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
            event.validateDate();
            taskList.addTask(event);
            ui.printTaskAdded(event.toString(), taskList.getTasks().length);
        } catch (ShagBotDateException e) {
            ui.printErrorMessage(e.getMessage());
        } catch (IllegalArgumentException e) {
            ui.printErrorMessage(INVALID_EVENT_FORMAT_ERROR_MESSAGE);
        }
        return true;
    }
}
