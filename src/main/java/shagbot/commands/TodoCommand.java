package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.tasks.Todo;
import shagbot.util.Ui;

/**
 * This class represents a command to add a todo task.
 */
public class TodoCommand extends Command {
    private static final String INVALID_TODO_ERROR_MESSAGE =
            "OOPSIE!! Description for 'todo' task cannot be empty.";
    private final String description;

    /**
     * Constructor for the {@code TodoCommand} class.
     *
     * @param description The description of the {@link Todo} task.
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing a command.";
        if (description.isEmpty()) {
            throw new ShagBotException(INVALID_TODO_ERROR_MESSAGE);
        }
        Todo todo = new Todo(description);
        taskList.addTask(todo);
        ui.printTaskAdded(todo.toString(), taskList.getTasks().length);
        return true;
    }
}

