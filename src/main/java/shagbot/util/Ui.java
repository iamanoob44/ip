package shagbot.util;

import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ui {
    private final String botName;
    private static final String LINE_SEPARATOR =
            "     ____________________________________________________________";


    /**
     * Constructor for Ui object for the given chatbot name.
     *
     * @param botName The name of the chatbot.
     */
    public Ui(String botName) {
        this.botName = botName;
    }

    /**
     * Returns the greeting.
     */
    public void printGreeting() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     Hello! I'm " + botName);
        System.out.println("     What can I do for you?");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Returns the exit message.
     */
    public void printExit() {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Echoes input by user back to the user.
     *
     * @param input Input by user to echo.
     */
    public void echo(String input) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     " + input);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints a message when the task is added.
     *
     * @param task The task to add.
     */
    public void printTaskAdded(String task, int taskCount) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }


    /**
     * Prints the list of tasks.
     *
     * @param tasks An array of added tasks to display.
     */
    public void printTaskList(Task[] tasks) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println("     " + (i + 1) + "." + tasks[i]);
        }
        System.out.println(LINE_SEPARATOR);
    }


    /**
     * Prints a message indicating that the task has been marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void printTaskMarked(Task task) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("     " + task);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints message indicating that the task has been marked as not done.
     *
     * @param task The task that was marked as not done.
     */
    public void printTaskUnmarked(Task task) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     OK, I've marked this task as not done yet:");
        System.out.println("     " + task);
        System.out.println(LINE_SEPARATOR);
    }


    /**
     * Prints messages when errors are encountered.
     *
     * @param message The error message.
     */
    public void printErrorMessage(String message) {
        System.out.println("     WOOP WOOP!!! " + message);
    }

    /**
     * Prints message indicating a task is deleted.
     *
     * @param task The task that was deleted.
     * @param tasksSoFar The number of tasks that remains in the arraylist.
     */
    public void printTaskDeleted(Task task, int tasksSoFar) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     Noted. I've removed this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + tasksSoFar + " tasks in the list.");
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints message if task is found for that specific date.
     *
     * @param date Date of the task.
     * @param tasks The array of tasks.
     */
    public void printTasksOnDate(LocalDate date, Task[] tasks) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("     Tasks on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":");
        boolean isTaskFound = false;

        for (Task task : tasks) {
            if (task instanceof Deadline && ((Deadline) task).getByTiming().toLocalDate().equals(date)) {
                System.out.println("       " + task);
                isTaskFound = true;
            } else if (task instanceof Event &&
                    (((Event) task).getStart().toLocalDate().equals(date) ||
                            ((Event) task).getEnd().toLocalDate().equals(date))) {
                System.out.println("       " + task);
                isTaskFound = true;
            }
        }
        if (!isTaskFound) {
            System.out.println("       No tasks are found for this date.");
        }
        System.out.println(LINE_SEPARATOR);
    }
}

