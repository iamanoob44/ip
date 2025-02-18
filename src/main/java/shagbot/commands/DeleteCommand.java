package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to delete tasks.
 */
public class DeleteCommand extends Command {
    private static final String NO_TASKS_ERROR_MESSAGE = "Nothing to delete. No tasks at the moment.";
    private static final String PLEASE_ENTER_A_NUMBER =
            "OOPSIE!! Task number is out of range! " + "Please enter a number from 1 to ";
    private final int taskIndex;

    /**
     * Constructor for the {@code DeleteCommand} class.
     *
     * @param taskIndex The index corresponding to the task to be deleted.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing command";
        int numOfTasks = taskList.getTasks().length;
        if (numOfTasks == 0) {
            throw new ShagBotException(NO_TASKS_ERROR_MESSAGE);
        }
        if (taskIndex < 0 || taskIndex >= numOfTasks) {
            throw new ShagBotException(PLEASE_ENTER_A_NUMBER + numOfTasks + ".");
        }
        Task deletedTask = taskList.deleteTask(taskIndex);
        int updatedNumOfTasks = taskList.getTasks().length;
        ui.printTaskDeleted(deletedTask, updatedNumOfTasks);
        return true;
    }
}

