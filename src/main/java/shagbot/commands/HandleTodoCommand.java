package shagbot.commands;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.TaskList;
import shagbot.tasks.Todo;
import shagbot.util.Ui;

/**
 * This class handles the "todo" command entered by user.
 */
public class HandleTodoCommand extends Commands {
    private static final String INVALID_TODO_ERROR_MESSAGE = "OOPSIE!! Description for 'todo' task cannot be empty.";
    private final String description;

    /**
     * Constructor for the {@code HandleToDoCommand} class.
     *
     * @param description The description of the {@link Todo} task.
     */
    public HandleTodoCommand(String description) {
        this.description = description;
    }

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui cannot be null.";
        if (description.isEmpty()) {
            throw new ShagBotException(INVALID_TODO_ERROR_MESSAGE);
        }
        Todo todo = new Todo(description);
        taskList.addTask(todo);
        ui.printTaskAdded(todo.toString(), taskList.getTasks().length);
        return true;
    }
}

