package shagbot.util;


import shagbot.tasks.*;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void testParseToDoCommand() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui("Shagbot");
        Parser parser = new Parser(taskList, ui);

        String[] commands = {
                "todo Read a book",
                "deadline Assignment /by 26/01/2025 1800",
                "todo Sleep even more",
                "deadline Project /by 28/01/2025 2000",
                "event Holiday to Maldives /from 28/01/2025 2000 /to 30/01/2025 2000"
        };

        String[] expectedDescriptions = {
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

        for (int i = 0; i < 5; i++) {
            assertTrue(tasks[i] instanceof Task,
                    "Task at index " + i + " should be of type Task.");
            assertEquals(expectedDescriptions[i], tasks[i].getDescription(),
                    "Task description at index " + i + " should match.");
        }
    }





    @Test
    void testParseCommand_byeCommand() {
        TaskList taskList = new TaskList();
        Ui ui = new Ui("Shagbot");
        Parser parser = new Parser(taskList, ui);

        boolean result = parser.parseCommand("bye");
        assertFalse(result, "The 'bye' command should return false.");
    }

}

