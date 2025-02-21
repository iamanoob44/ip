package shagbot.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shagbot.commands.Command;
import shagbot.commands.TaskOnCommand;
import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.tasks.Todo;

/**
 * A test class to test certain methods of {@link Parser}.
 */
public class ParserTest {
    private static final String UNKNOWN_COMMANDS_ERROR_MESSAGE = "OOPSIE!! Unknown command. "
            + "Consider only these valid commands:\n\nlist, todo, deadline, event, "
            + "mark, unmark, delete, task on, find, snooze, reminder or bye.";
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    /**
     * Initialise a test setup for testing.
     */
    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        ui = new Ui("Shagbot");
        parser = new Parser(taskList, ui);
    }


    /**
     * Test whether {@code parseCommand(String inputCommand)} can add valid tasks properly in {@link Parser},
     * and not add any invalid tasks due to invalid commands.
     */
    @Test
    void testParseCommand_doTasksCommand() {

        String[] commands = {
            "todo Read a book",
            "deadline Assignment /by 26/01/2025 1800",
            "todo Sleep even more",
            "deadline Project /by 28/01/2025 2000",
            "blehhh", // invalid command
            "event Holiday to Maldives /from 28/01/2025 2000 /to 30/01/2025 2000"
        };

        String[] expectedDescriptionsForValidCommands = {
            "Read a book",
            "Assignment",
            "Sleep even more",
            "Project",
            "Holiday to Maldives"
        };

        // Execute each every commands
        for (String command : commands) {
            parser.parseCommand(command);
        }

        int numOfValidTasks = 5;
        Task[] tasks = taskList.getTasks();
        assertEquals(numOfValidTasks, tasks.length,
                "Task list should contain the correct number of valid tasks, which is 5.");

        for (int i = 0; i < numOfValidTasks; i++) {
            assertTrue(tasks[i] instanceof Task,
                    "Task at index " + i + " should be of type Task.");
            assertEquals(expectedDescriptionsForValidCommands[i], tasks[i].getDescription(),
                    "Task description at index " + i + " should match.");
        }
    }

    /**
     * Test whether {@code parseCommand(String inputCommand)} can handle the "bye" command properly.
     * "bye" returns {@code false}, while other commands returns {@code true}.
     */
    @Test
    void testParseCommand_byeCommand() {

        boolean isFirstTrue = parser.parseCommand("bye");
        boolean isSecondTrue = parser.parseCommand("Todo borrow");
        boolean isThirdTrue = parser.parseCommand("Deadline ....");
        boolean isFourthTrue = parser.parseCommand("mark 2");

        assertFalse(isFirstTrue, "The 'bye' command should return false after executing.");
        assertTrue(isSecondTrue, "Todo command should return true after executing.");
        assertTrue(isThirdTrue, "Deadline command should return true after executing.");
        assertTrue(isFourthTrue, "Mark/Unmark command should return true after executing.");

    }

    /**
     * Test whether {@code parseCommand(String inputCommand)} can handle {@code mark}
     * or {@code unmark} tasks commands properly.
     */
    @Test
    public void testParseCommand_handleMarkOrUnmarkCommand() {

        taskList.getTasksForTesting().add(new Todo("Task 1"));
        taskList.getTasksForTesting().add(new Deadline("Task 2", "24/04/2002 1900"));
        taskList.getTasksForTesting().add(new Event("Task 3", "24/04/2002 2000", "26/04/2005 1800"));

        String validCommand = "mark 1";
        parser.parseCommand(validCommand);
        assertTrue(taskList.getTasksForTesting().get(0).isDone(),
                "Task 1 should be marked as done.");

        String markCommand = "mark 2";
        parser.parseCommand(markCommand);
        assertTrue(taskList.getTasksForTesting().get(0).isDone(),
                "Task 2 should be marked as done.");

        String unmarkCommand = "unmark 2";
        parser.parseCommand(unmarkCommand);
        assertFalse(taskList.getTasksForTesting().get(1).isDone(),
                "Task 2 should be unmarked.");

        parser.parseCommand("unmark 3");
        assertFalse(taskList.getTasksForTesting().get(2).isDone(),
                "Task 3 should be unmarked.");

        String negativeCommand = "mark -2";
        parser.parseCommand(negativeCommand);
        String errorMessage = ui.getLastMessage();
        String expectedError = "WOOP WOOP!!! OOPSIE!! Task number cannot be less than 1! Please try again.";
        assertEquals(expectedError, errorMessage, "Negative task numbers "
                + "should display an appropriate error message.");
    }

    /**
     * Test whether the {@code parseTaskIndex(String description)} handles task indexes for marking and
     * unmarking tasks.
     *
     * @throws ShagBotException If the indexes, in its string representation form, are out of range.
     */
    @Test
    void testParseTaskIndex() throws ShagBotException {
        int firstResult = parser.parseTaskIndex("3"); // Mark 3, Delete 3, Unmark 3
        int secondResult = parser.parseTaskIndex("5"); // Mark 5, Delete 5, Unmark 5
        int thirdResult = parser.parseTaskIndex("11"); // Mark 11, Delete 11, Unmark 11

        // Invalid command was entered since index cannot be less than 1
        ShagBotException error = assertThrows(ShagBotException.class, () -> parser.parseTaskIndex("-1"));
        String expectedErrorMessage = "OOPSIE!! Task number cannot be less than 1! Please try again.";

        assertEquals(2, firstResult);
        assertEquals(4, secondResult);
        assertEquals(10, thirdResult);
        assertEquals(expectedErrorMessage, error.getMessage());
    }

    /**
     * Test whether the {@code parseInputToCommand(String input)} handles the command "task on" properly.
     *
     * @throws ShagBotException If the given command does not follow {@code task on DD/M/YYYY} format.
     */
    @Test
    void testParseInputToCommand_validTaskOnCommand() throws ShagBotException {

        Command firstValidCommand = parser.parseInputToCommand("task on 16/02/2025");
        Command secondValidCommand = parser.parseInputToCommand("task on 24/04/2025");
        Command invalidCommand = parser.parseInputToCommand("find perry");

        ShagBotException error = assertThrows(ShagBotException.class, () -> parser
                .parseInputToCommand("taskon 24/04/2025"));
        assertTrue(firstValidCommand instanceof TaskOnCommand,
                "Should return a HandleTaskOnCommand instance");
        assertTrue(secondValidCommand instanceof TaskOnCommand,
                "Should return a HandleTaskOnCommand instance");
        assertFalse(invalidCommand instanceof TaskOnCommand,
                "This is not a HandleTaskOnCommand instance");
        assertEquals(UNKNOWN_COMMANDS_ERROR_MESSAGE, error.getMessage(),
                "This is not a HandleTaskOnCommand " + " instance due to wrong format ");
    }

    /**
     * Test whether the {@code parseInputToCommand(String input)} handles invalid entries/commands properly.
     */
    @Test
    void testParseInputToCommand_invalidCommand() {
        ShagBotException firstError = assertThrows(ShagBotException.class, () -> {
            parser.parseInputToCommand("belhhhhh");
        });

        ShagBotException secondError = assertThrows(ShagBotException.class, () -> {
            parser.parseInputToCommand("todos borrow a book"); // should be todo borrow a book
        });

        ShagBotException thirdError = assertThrows(ShagBotException.class, () -> {
            parser.parseInputToCommand("list3"); // should be list
        });

        assertEquals(UNKNOWN_COMMANDS_ERROR_MESSAGE, firstError.getMessage());
        assertEquals(UNKNOWN_COMMANDS_ERROR_MESSAGE, secondError.getMessage());
        assertEquals(UNKNOWN_COMMANDS_ERROR_MESSAGE, thirdError.getMessage());
    }

    /**
     * Test whether the {@code parseInputToCommand(String input)} can inform user to enter a valid command if
     * they did not enter anything.
     */
    @Test
    void testParseInputToCommand_blankCommand() {
        ShagBotException blankInputError = assertThrows(ShagBotException.class, () -> {
            parser.parseInputToCommand(" ");
        });

        String expectedErrorMessage = "No input provided. Please enter a valid command.";
        assertEquals(expectedErrorMessage, blankInputError.getMessage());
    }
}





