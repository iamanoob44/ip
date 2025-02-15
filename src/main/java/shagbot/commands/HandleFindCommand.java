package shagbot.commands;

import java.util.Arrays;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class handles the "find" command entered by user.
 */
public class HandleFindCommand extends Commands {
    private static final String INVALID_FIND_ERROR_MESSAGE = "OOPSIE!! Please enter 'find' <something> again.";
    private final String keyword;

    /**
     * Constructor for the {@code HandleFindCommand } class.
     *
     * @param keyword The keyword to search for in the task descriptions.
     */
    public HandleFindCommand(String keyword) {
        assert keyword != null : "the keyword to filter through the tasks containing the keyword must not be empty";
        this.keyword = keyword;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        if (keyword.isEmpty()) {
            throw new ShagBotException(INVALID_FIND_ERROR_MESSAGE);
        }
        Task[] foundTasks = Arrays.stream(taskList.getTasks()).distinct()
                .filter(task -> task.getDescription().contains(keyword))
                .toArray(Task[]::new);
        ui.printAnyMatchingTasks(foundTasks);
        return true;
    }
}


