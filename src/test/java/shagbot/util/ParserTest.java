package shagbot.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shagbot.commands.Commands;
import shagbot.commands.HandleTaskOnCommand;
import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.tasks.Todo;


public class ParserTest {
    private TaskList taskList;
    private Ui ui;
    private Parser parser;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        ui = new Ui("Shagbot");
        parser = new Parser(taskList, ui);
    }


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

    @Test
    void testParseCommand_byeCommand() {

        boolean result = parser.parseCommand("bye");
        boolean secondResult = parser.parseCommand("Todo borrow");
        boolean thirdResult = parser.parseCommand("Deadline ....");
        boolean fourthResult = parser.parseCommand("mark 2");

        assertFalse(result, "The 'bye' command should return false after executing.");
        assertTrue(secondResult, "Todo command should return true after executing.");
        assertTrue(thirdResult, "Deadline command should return true after executing.");
        assertTrue(fourthResult, "Mark/Unmark command should return true after executing.");

    }

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
    @Test
    void testParseInputToCommand_validTaskOnCommand() throws ShagBotException {
        String expectedErrorMessage = "OOPSIE!! Unknown command. "
                + "Consider only these valid commands:\n\nlist, todo, deadline, event, "
                + "mark, unmark, delete, task on, find, snooze or bye.";

        Commands firstValidCommand = parser.parseInputToCommand("task on 16/02/2025");
        Commands secondValidCommand = parser.parseInputToCommand("task on 24/04/2025");
        Commands invalidCommand = parser.parseInputToCommand("find perry");

        ShagBotException error = assertThrows(ShagBotException.class, () -> parser
                .parseInputToCommand("taskon 24/04/2025"));
        assertTrue(firstValidCommand instanceof HandleTaskOnCommand,
                "Should return a HandleTaskOnCommand instance");
        assertTrue(secondValidCommand instanceof HandleTaskOnCommand,
                "Should return a HandleTaskOnCommand instance");
        assertFalse(invalidCommand instanceof HandleTaskOnCommand,
                "This is not a HandleTaskOnCommand instance");
        assertEquals(expectedErrorMessage, error.getMessage(), "This is not a HandleTaskOnCommand "
                + " instance due to wrong format ");
    }

    @Test
    void testParseInputToCommand_invalidCommand() {
        ShagBotException firstError = assertThrows(ShagBotException.class, () -> {
            parser.parseInputToCommand("belhhhhh");
        });

        ShagBotException secondError = assertThrows(ShagBotException.class, () -> {
            parser.parseInputToCommand("todos borrow a book"); // should be todo borrow a book
        });


        String expectedErrorMessage = "OOPSIE!! Unknown command. Consider only these valid commands:\n\nlist, "
                + "todo, deadline, event, mark, unmark, delete, task on, find, snooze or bye.";
        assertEquals(expectedErrorMessage, firstError.getMessage());
        assertEquals(expectedErrorMessage, secondError.getMessage());
    }

    @Test
    void testParseInputToCommand_blankCommand() {
        ShagBotException blankInputError = assertThrows(ShagBotException.class, () -> {
            parser.parseInputToCommand(" ");
        });

        String expectedErrorMessage = "No input provided. Please enter a valid command.";
        assertEquals(expectedErrorMessage, blankInputError.getMessage());
    }
}





