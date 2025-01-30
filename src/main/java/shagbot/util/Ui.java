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
     * Sets a new message to be retrieved by GUI.
     *
     * @param message The message to store.
     */
    private void setLastMessage(String message) {
        lastMessage = message;
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
    public void printGreeting() {
        String message = "Hello! I'm " + botName + "\nWhat can I do for you?";
        setLastMessage(message);
        System.out.println(message);
    }

    /**
     * Prints the exit message.
     */
    public void printExit() {
        String message = "Bye. Hope to see you again soon!";
        setLastMessage(message);
        System.out.println(message);
    }

    /**
     * Echoes user input back to them.
     *
     * @param input The user input.
     */
    public void echo(String input) {
        setLastMessage(input);
        System.out.println(input);
    }

    /**
     * Prints a message when a task is added.
     *
     * @param task The task added.
     * @param taskCount Total number of tasks after adding.
     */
    public void printTaskAdded(String task, int taskCount) {
        String message = "Got it. I've added this task:\n  " + task +
                "\nNow you have " + taskCount + " tasks in the list.";
        setLastMessage(message);
        System.out.println(message);
    }

    /**
     * Prints the list of tasks.
     *
     * @param tasks An array of added tasks to display.
     */
    public void printTaskList(Task[] tasks) {
        if (tasks.length == 0) {
            setLastMessage("Your task list is empty!");
        } else {
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.length; i++) {
                sb.append((i + 1)).append(". ").append(tasks[i]).append("\n");
            }
            setLastMessage(sb.toString().trim());
        }
        System.out.println(lastMessage);
    }

    /**
     * Prints a message when a task is marked as done.
     *
     * @param task The task marked as done.
     */
    public void printTaskMarked(Task task) {
        String message = "Nice! I've marked this task as done:\n" + task;
        setLastMessage(message);
        System.out.println(message);
    }

    /**
     * Prints a message when a task is unmarked.
     *
     * @param task The task marked as not done.
     */
    public void printTaskUnmarked(Task task) {
        String message = "OK, I've marked this task as not done yet:\n" + task;
        setLastMessage(message);
        System.out.println(message);
    }

    /**
     * Prints an error message.
     *
     * @param message The error message.
     */
    public void printErrorMessage(String message) {
        String errorMessage = "WOOP WOOP!!! " + message;
        setLastMessage(errorMessage);
        System.out.println(errorMessage);
    }

    /**
     * Prints a message when a task is deleted.
     *
     * @param task The deleted task.
     * @param tasksSoFar The number of remaining tasks.
     */
    public void printTaskDeleted(Task task, int tasksSoFar) {
        String message = "Noted. I've removed this task:\n  " + task +
                "\nNow you have " + tasksSoFar + " tasks in the list.";
        setLastMessage(message);
        System.out.println(message);
    }

    /**
     * Prints tasks scheduled for a specific date.
     *
     * @param date The date to filter tasks by.
     * @param tasks The task list.
     */
    public void printTasksOnDate(LocalDate date, Task[] tasks) {
        StringBuilder sb = new StringBuilder("Tasks on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
        boolean isTaskFound = false;

        for (Task task : tasks) {
            if (task instanceof Deadline && ((Deadline) task).getByTiming().toLocalDate().equals(date)) {
                sb.append("  ").append(task).append("\n");
                isTaskFound = true;
            } else if (task instanceof Event
                    && (((Event) task).getStart().toLocalDate().equals(date)
                    || ((Event) task).getEnd().toLocalDate().equals(date))) {
                sb.append("  ").append(task).append("\n");
                isTaskFound = true;
            }
        }
        if (!isTaskFound) {
            sb.append("  No tasks are found for this date.");
        }

        setLastMessage(sb.toString().trim());
        System.out.println(lastMessage);
    }

    /**
     * Prints matching tasks based on keyword search.
     *
     * @param tasks An array of matched tasks.
     */
    public void printAnyMatchingTasks(Task[] tasks) {
        if (tasks.length == 0) {
            setLastMessage("No matching tasks found.");
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < tasks.length; i++) {
                sb.append((i + 1)).append(". ").append(tasks[i]).append("\n");
            }
            setLastMessage(sb.toString().trim());
        }
        System.out.println(lastMessage);
    }
}




