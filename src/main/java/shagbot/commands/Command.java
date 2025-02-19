package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * Represents the Parent class to handle the various commands.
 */
public abstract class Command {
    /**
     * Handles and executes the following actions based on the commands entered by user.
     *
     * @param taskList The {@link TaskList} instance.
     * @param ui       The {@link Ui} instance.
     * @return Returns false if the command terminates the program through the keyword "bye", true otherwise.
     * @throws ShagBotException If an error occurs during execution of command.
     */
    public abstract boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException;
}
