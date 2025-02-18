package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {
    private static final String TASK_NUMBER_OUT_OF_RANGE_MESSAGE = "OOPSIE!! Task number is out of range! "
            + "Please enter a number from 1 to ";
    private static final String NO_TASKS_AT_THE_MOMENT_ERROR_MESSAGE = "Nothing to unmark. No tasks at the moment";
    private final int taskIndex;

    /**
     * Constructor for the {@code UnmarkCommand} class.
     *
     * @param taskIndex Index corresponding to the task to be unmarked.
     */
    public UnmarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing command.";
        int numOfTasks = taskList.getTasks().length;
        if (numOfTasks == 0) {
            throw new ShagBotException(NO_TASKS_AT_THE_MOMENT_ERROR_MESSAGE);
        }
        if (taskIndex < 0 || taskIndex >= numOfTasks) {
            throw new ShagBotException(TASK_NUMBER_OUT_OF_RANGE_MESSAGE + numOfTasks + ".");
        }
        Task task = taskList.getTask(taskIndex);
        task.unmark();
        ui.printTaskUnmarked(task);
        return true;
    }
}
