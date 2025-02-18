package shagbot.commands;

import java.util.Arrays;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * This class represents a command to find tasks using a keyword entered.
 */
public class FindCommand extends Command {
    private static final String INVALID_FIND_ERROR_MESSAGE = "OOPSIE!! Please enter 'find' <something> again.";
    private final String keyword;

    /**
     * Constructor for the {@code FindCommand} class.
     *
     * @param keyword The keyword to search for in the task descriptions.
     */
    public FindCommand(String keyword) {
        assert keyword != null : "Keyword must not be null.";
        this.keyword = keyword;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing command.";
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


