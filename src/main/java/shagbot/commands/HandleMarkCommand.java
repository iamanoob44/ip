package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class handles the "mark" command entered by user.
 */
public class HandleMarkCommand extends Commands {
    private static final String PLEASE_ENTER_A_NUMBER_FROM_1_TO = "OOPSIE!! Task number is out of range! "
            + "Please enter a number from 1 to ";
    private final int taskIndex;

    /**
     * Constructor for the {@code HandleMarkCommand } class.
     *
     * @param taskIndex Index corresponding to the task to be marked.
     */
    public HandleMarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui cannot be null.";
        int numOfTasks = taskList.getTasks().length;
        if (taskIndex < 0 || taskIndex >= numOfTasks) {
            throw new ShagBotException(PLEASE_ENTER_A_NUMBER_FROM_1_TO + numOfTasks + ".");
        }
        Task task = taskList.getTask(taskIndex);
        task.mark();
        ui.printTaskMarked(task);
        return true;
    }
}



