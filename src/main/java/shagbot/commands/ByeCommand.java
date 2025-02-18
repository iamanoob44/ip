package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command that handles the termination of the program when the user enters "bye".
 */
public class ByeCommand extends Command {
    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when command is executed.";
        ui.printExit();
        return false;
    }
}
