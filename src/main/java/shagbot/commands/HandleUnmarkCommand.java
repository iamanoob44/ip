package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class handles the "unmark" command entered by user.
 */
public class HandleUnmarkCommand extends Commands {
    private static final String PLEASE_ENTER_A_NUMBER_FROM_1_TO = "OOPSIE!! Task number is out of range! "
            + "Please enter a number from 1 to ";
    private static final String NO_TASKS_AT_THE_MOMENT_ERROR_MESSAGE = "Nothing to unmark. No tasks at the moment";
    private final int taskIndex;

    /**
     * Constructor for the {@code HandleUnmarkCommand } class.
     *
     * @param taskIndex Index corresponding to the task to be unmarked.
     */
    public HandleUnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui cannot be null.";
        int numOfTasks = taskList.getTasks().length;
        if (numOfTasks == 0) {
            throw new ShagBotException(NO_TASKS_AT_THE_MOMENT_ERROR_MESSAGE);
        }
        if (taskIndex < 0 || taskIndex >= numOfTasks) {
            throw new ShagBotException(PLEASE_ENTER_A_NUMBER_FROM_1_TO + numOfTasks + ".");
        }
        Task task = taskList.getTask(taskIndex);
        task.unmark();
        ui.printTaskUnmarked(task);
        return true;
    }
}
