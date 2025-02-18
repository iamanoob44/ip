package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Deadline;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to add a deadline task.
 */
public class DeadlineCommand extends Command {
    private static final String INVALID_DEADLINE_ERROR_MESSAGE =
            "OOPSIE!! Invalid format. Use: deadline <description>" + " /by <dd/M/yyyy hhmm>.";
    private final String description;

    /**
     * Constructor for the {@code DeadlineCommand} class.
     *
     * @param description Description of the deadline task.
     */
    public DeadlineCommand(String description) {
        this.description = description;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing a command.";
        String[] parts = description.split(" /by ", 2);
        if (parts.length < 2) {
            throw new ShagBotException(INVALID_DEADLINE_ERROR_MESSAGE);
        }
        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            taskList.addTask(deadline);
            ui.printTaskAdded(deadline.toString(), taskList.getTasks().length);
        } catch (IllegalArgumentException e) {
            throw new ShagBotException(e.getMessage());
        }
        return true;
    }
}


