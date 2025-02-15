package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class handles the "delete" command entered by user.
 */
public class HandleDeleteCommand extends Commands {
    public static final String NO_TASKS_ERROR_MESSAGE = "No tasks at the moment.";
    public static final String PLEASE_ENTER_A_NUMBER = "OOPSIE!! Task number is out of range! "
            + "Please enter a number from 1 to ";
    private final int taskIndex;

    /**
     * Constructor for the {@code HandleDeleteCommand } class.
     *
     * @param taskIndex The index corresponding to the task to be deleted.
     */
    public HandleDeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui cannot be null.";
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

