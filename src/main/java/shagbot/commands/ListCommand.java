package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to list all tasks.
 */
public class ListCommand extends Command {
    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing command.";
        ui.printTaskList(taskList.getTasks());
        return true;
    }
}
