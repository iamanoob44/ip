package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class handles the "bye" command entered by user.
 */
public class HandleByeCommand extends Commands {
    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui cannot be null.";
        ui.printExit();
        return false;
    }
}
