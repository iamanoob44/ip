package shagbot.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;


public class ParserTest {

    @Test
    void testParseCommandDoTasksCommand() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui("Shagbot");
        Parser parser = new Parser(taskList, ui);

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
                "Task list should contain the correct number of valid tasks.");

        for (int i = 0; i < numOfValidTasks; i++) {
            assertTrue(tasks[i] instanceof Task,
                    "Task at index " + i + " should be of type Task.");
            assertEquals(expectedDescriptionsForValidCommands[i], tasks[i].getDescription(),
                    "Task description at index " + i + " should match.");
        }
    }







    @Test
    void testParseCommand_byeCommand() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui("Shagbot");
        Parser parser = new Parser(taskList, ui);

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
    public void testHandleMarkCommand() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui("Shagbot");
        Parser parser = new Parser(taskList, ui);

        taskList.getTasksForTesting().add(new Task("Task 1"));
        taskList.getTasksForTesting().add(new Task("Task 2"));
        taskList.getTasksForTesting().add(new Task("Task 3"));

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
        ShagBotException exceptionForNegative = assertThrows(ShagBotException.class, () ->
                parser.handleMarkCommand(negativeCommand, true)
        );
        assertEquals("OOPSIE!! Task number cannot be less than 1! Please try again.",
                exceptionForNegative.getMessage(), "Negative task numbers are not allowed");

    }

}


