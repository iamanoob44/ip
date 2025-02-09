package shagbot.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;

/**
 * Represents a Ui class that handles user interactions with Shagbot.
 */
public class Ui {
    public static final String MATCHING_TASKS_IN_THE_LIST = "Here are the matching tasks in your list:\n";
    private static final String DATE_FORMAT = "MMM dd yyyy";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon!";
    private static final String TASK_LIST_IS_EMPTY_MESSAGE = "Your task list is empty!";
    private static final String TASKS_IN_THE_LIST_MESSAGE = "Here are the tasks in your list:\n";
    private static final String PERIOD_WITH_SINGLE_SPACE = ". ";
    private static final String LINE_BREAK = "\n";
    private static final String MARKED_THIS_TASK_AS_DONE_MESSAGE = "Nice! I've marked this task as done:\n";
    private static final String MARKED_THIS_TASK_AS_NOT_DONE_YET_MESSAGE =
            "OK, I've marked this task as not done yet:\n";
    private static final String WOOP_WOOP = "WOOP WOOP!!! ";
    private static final String DOUBLE_SPACES = "  ";
    private static final String NO_TASKS_ARE_FOUND_FOR_THIS_DATE_MESSAGE = "  No tasks are found for this date.";
    private static final String NO_MATCHING_TASKS_FOUND_MESSAGE = "No matching tasks found.";
    private final String botName;
    private String lastMessage; // Stores the latest message for GUI display

    /**
     * Constructor for {@code Ui} class for the given chatbot name.
     *
     * @param botName The name of the chatbot.
     */
    public Ui(String botName) {
        this.botName = botName;
        this.lastMessage = "";
    }

    /**
     * Stores and displays the message to the user.
     *
     * @param message The message to be stored and displayed.
     */
    protected void displayMessage(String message) {
        lastMessage = message;
        System.out.println(message);
    }

    /**
     * Returns the last message for GUI display.
     *
     * @return The last stored message.
     */
    public String getLastMessage() {
        return lastMessage;
    }

    /**
     * Prints the greeting message.
     */
    protected void printGreeting() {
        displayMessage("Hello! I'm " + botName + "\nWhat can I do for you?");
    }

    /**
     * Prints the exit message.
     */
    protected void printExit() {
        displayMessage(GOODBYE_MESSAGE);
    }

    /**
     * Echoes user input back to them.
     *
     * @param input The user input.
     */
    protected void echo(String input) {
        displayMessage(input);
    }

    /**
     * Prints a message when a task is added.
     *
     * @param task The task added.
     * @param taskCount Total number of tasks after adding.
     */
    protected void printTaskAdded(String task, int taskCount) {
        displayMessage("Got it. I've added this task:\n  " + task
                + "\nNow you have " + taskCount + " tasks in the list.");
    }

    /**
     * Prints the list of tasks.
     *
     * @param tasks An array of added tasks to display.
     */
    protected void printTaskList(Task[] tasks) {
        if (tasks.length == 0) {
            displayMessage(TASK_LIST_IS_EMPTY_MESSAGE);
            return;
        }

        StringBuilder messageBuilder = new StringBuilder(TASKS_IN_THE_LIST_MESSAGE);
        for (int i = 0; i < tasks.length; i++) {
            messageBuilder.append(i + 1).append(PERIOD_WITH_SINGLE_SPACE).append(tasks[i]).append(LINE_BREAK);
        }

        String taskListRepresentation = messageBuilder.toString().trim();
        displayMessage(taskListRepresentation);
    }

    /**
     * Prints a message when a task is marked as done.
     *
     * @param task The task marked as done.
     */
    protected void printTaskMarked(Task task) {
        displayMessage(MARKED_THIS_TASK_AS_DONE_MESSAGE + task);
    }

    /**
     * Prints a message when a task is unmarked.
     *
     * @param task The task marked as not done.
     */
    protected void printTaskUnmarked(Task task) {
        displayMessage(MARKED_THIS_TASK_AS_NOT_DONE_YET_MESSAGE + task);
    }

    /**
     * Prints an error message.
     *
     * @param message The error message.
     */
    public void printErrorMessage(String message) {
        displayMessage(WOOP_WOOP + message);
    }

    /**
     * Prints a message when a task is deleted.
     *
     * @param task The deleted task.
     * @param tasksSoFar The number of remaining tasks.
     */
    protected void printTaskDeleted(Task task, int tasksSoFar) {
        displayMessage("Noted. I've removed this task:\n  " + task
                + "\nNow you have " + tasksSoFar + " tasks in the list.");
    }

    /**
     * Prints tasks scheduled for a specific date.
     *
     * @param date The date to filter tasks by.
     * @param tasks The task list.
     */
    protected void printTasksOnDate(LocalDate date, Task[] tasks) {
        StringBuilder sb = new StringBuilder("Tasks on "
                + date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + ":\n");
        boolean isTaskFound = false;

        for (Task task : tasks) {
            // Look for any matched deadline or event tasks.
            if (isTaskOnDateFound(task, date)) {
                sb.append(DOUBLE_SPACES).append(task).append(LINE_BREAK);
                isTaskFound = true;
            }
        }
        if (!isTaskFound) {
            sb.append(NO_TASKS_ARE_FOUND_FOR_THIS_DATE_MESSAGE);
        }

        displayMessage(sb.toString().trim());
    }

    /**
     * Prints matching tasks based on keyword search.
     *
     * @param tasks An array of matched tasks.
     */
    protected void printAnyMatchingTasks(Task[] tasks) {
        if (tasks.length == 0) {
            displayMessage(NO_MATCHING_TASKS_FOUND_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder(MATCHING_TASKS_IN_THE_LIST);
        for (int i = 0; i < tasks.length; i++) {
            sb.append(i + 1).append(PERIOD_WITH_SINGLE_SPACE).append(tasks[i]).append(LINE_BREAK);
        }
        String matchedTaskInStringRepresentation = sb.toString().trim();
        displayMessage(matchedTaskInStringRepresentation);
    }

    /**
     * Checks if a task is scheduled on a given date.
     *
     * @param task The task to check.
     * @param date The date to compare against.
     * @return True if the task is on the given date, false otherwise.
     */
    private boolean isTaskOnDateFound(Task task, LocalDate date) {
        return (task instanceof Deadline deadline && deadline.getByTiming().toLocalDate().equals(date))
                || (task instanceof Event event
                        && (event.getStart().toLocalDate().equals(date) || event.getEnd().toLocalDate().equals(date)));
    }
}




