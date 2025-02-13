package shagbot.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.tasks.Todo;

/**
 * Represents a parser class to handle users' commands and inputs.
 */
public class Parser {
    private static final String INVALID_FIND_ERROR_MESSAGE = "OOPSIE!! Please enter 'find' <something> again.";
    private static final String INVALID_DATE_FORMAT_ERROR_MESSAGE = "OOPSIE!! Invalid date format: "
            + "Please use 'dd/M/yyyy'.";
    private static final String DATE_FORMAT = "dd/M/yyyy";
    private static final String DATE_FORMAT_WITH_TIME = "dd/M/yyyy HHmm";
    private static final String INVALID_EVENT_ERROR_MESSAGE = "OOPSIE!! Invalid 'event' format. "
            + "Use: event <description> /from dd/M/yyyy hhmm /to dd/M/yy hhmm.";
    private static final String INVALID_DEADLINE_ERROR_MESSAGE = "OOPSIE!! Invalid format. Use: deadline <description>"
            + " /by <dd/M/yyyy hhmm>.";
    private static final String INVALID_TODO_ERROR_MESSAGE = "OOPSIE!! Description for 'todo' task cannot be empty.";
    private static final String TASK_INDEX_IS_ZERO_ERROR_MESSAGE = "OOPSIE!! Task number 0 is invalid! "
            + "Task numbers start from 1.";
    private static final String TASK_INDEX_IS_NEGATIVE_ERROR_MESSAGE = "OOPSIE!! Task number cannot be less than 1! "
            + "Please try again.";
    private static final String NO_TASKS_AT_THE_MOMENT_ERROR_MESSAGE = "No tasks at the moment.";
    private static final String INVALID_COMMANDS_ERROR_MESSAGE = "OOPSIE!! Unknown command. "
            + "Consider only these valid commands: list, todo, deadline, event, "
            + "mark, unmark, delete, task on, find, snooze or bye.";
    private static final String UNEXPECTED_ERROR_MESSAGE = "OOPSIE!! Unexpected error occurred... "
            + "Please contact help services.";
    private static final String NO_INPUT_ERROR_MESSAGE = "No input provided. Please enter a valid command.";
    private static final String INVALID_TASK_COMMAND_ERROR_MESSAGE = "OOPSIE!! Invalid 'task' command."
            + " Did you mean 'task on <date>'?";
    private static final String ENTER_A_NUMBER_ERROR_MESSAGE = "OOPSIE!! Please enter a number behind.";
    private static final String INVALID_TASK_NUMBER_ERROR_MESSAGE = "OOPSIE!! Invalid task number entered."
            + " Please try again!!";
    private static final String NO_NUMBER_BEHIND_ERROR_MESSAGE = "OOPSIE!! Please enter a number behind";
    private static final String BYE = "bye";
    private static final String LIST = "list";
    private static final String MARK = "mark";
    private static final String UNMARK = "unmark";
    private static final String TODO = "todo";
    private static final String DEADLINE = "deadline";
    private static final String EVENT = "event";
    private static final String DELETE = "delete";
    private static final String TASK = "task";
    private static final String FIND = "find";
    private static final String SNOOZE = "snooze";
    private static final String SPECIFY_TASK_TO_SNOOZE_ERROR_MESSAGE = "OOPSIE!! Please specify which task to snooze.";
    private static final String SPECIFY_TASK_TO_SNOOZE_AND_NEW_DATE_ERROR_MESSAGE = "OOPSIE!! Please specify which "
            + "task to snooze and new date/time.";
    private static final String CANNOT_SNOZE_TODO_ERROR_MESSAGE = "We only can snooze/reschedule deadlines or events.";
    private static final String SNOOZE_DEADLINE_FAIL_ERROR_MESSAGE = "To snooze a deadline, use: snooze <index> "
            + "/by dd/M/yyyy HHmm";
    private static final String DEADLINE_HAS_BEEN_RESCHEDULED_TO = "  , deadline of this task has been rescheduled "
            + "to: ";
    private static final String SNOOZE_EVENT_FAIL_ERROR_MESSAGE = "To snooze an event, use: snooze <index> "
            + "/from dd/M/yyyy HHmm /to dd/M/yyyy HHmm";
    private static final String TASK_INDEX_OUT_OF_RANGE_ERROR_MESSAGE = "OOPSIE!! Task number is out of range! "
            + "Enter a number from 1 to ";
    private static final String REMINDER = "reminder";
    private static final String NO_UPCOMING_TASKS_REMINDER_ERROR = "No upcoming tasks within the next 48 hours.";
    private static final String UPCOMING_TASKS_WITHIN_THE_NEXT_48_HOURS = "Upcoming tasks within the next 48 hours:\n";
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Constructor for the {@code Parser} class.
     *
     * @param taskList The {@link TaskList} instance to help manage tasks.
     * @param ui       The {@link Ui} instance to handle user interactions.
     */
    public Parser(TaskList taskList, Ui ui) {
        assert taskList != null : "TaskList instance cannot be null.";
        assert ui != null : "Ui instance cannot be null.";
        // Initialise the variables.
        this.taskList = taskList;
        this.ui = ui;
    }

    /**
     * Parses a user command and executes the corresponding action.
     *
     * @param command The user's command to parse and process accordingly.
     * @return True if {@link shagbot.Shagbot} continues running, False for exit
     */
    public boolean parseCommand(String command) {
        try {
            String[] parsedInput = parseUserCommand(command);
            return executeCommand(parsedInput[0], parsedInput[1]);
        } catch (ShagBotException e) {
            ui.printErrorMessage(e.getMessage());
        } catch (Exception e) {
            ui.printErrorMessage(UNEXPECTED_ERROR_MESSAGE);
        }
        return true;
    }



    /**
     * Executes the command based on parsed input.
     *
     * @param mainCommand The main command keyword entered by user.
     * @param description The command description (if any).
     * @return False If the command entered is "bye", otherwise True.
     * @throws ShagBotException If the command is invalid.
     */
    private boolean executeCommand(String mainCommand, String description) throws ShagBotException {
        switch (mainCommand) {
        case BYE:
            handleByeCommand();
            return false;

        case LIST:
            handleListCommand();
            break;

        case MARK:
            handleMarkCommand(mainCommand + " " + description, true);
            break;

        case UNMARK:
            handleMarkCommand(mainCommand + " " + description, false);
            break;

        case TODO:
            handleTodoCommand(description);
            break;

        case DEADLINE:
            handleDeadlineCommand(description);
            break;

        case EVENT:
            handleEventCommand(description);
            break;

        case DELETE:
            handleDeleteCommand(mainCommand + " " + description);
            break;

        case TASK:
            findTaskOnDateCommand(description);
            break;

        case FIND:
            handleFindCommand(description);
            break;

        case SNOOZE:
            handleSnoozeCommand(description);
            break;

        case REMINDER:
            handleReminderCommand();
            break;
        default:
            throw new ShagBotException(INVALID_COMMANDS_ERROR_MESSAGE);
        }
        return true;
    }

    /**
     * Parses user input into a command and its description.
     *
     * @param command The raw input command entered by user.
     * @return A two-element String array: [command, description].
     * @throws ShagBotException If the command is empty.
     */
    private String[] parseUserCommand(String command) throws ShagBotException {
        if (command == null || command.trim().isEmpty()) {
            throw new ShagBotException(NO_INPUT_ERROR_MESSAGE);
        }
        String[] splitCommand = command.split(" ", 2);
        String mainCommand = splitCommand[0];
        String description = splitCommand.length > 1 ? splitCommand[1].trim() : "";
        return new String[]{mainCommand, description};
    }


    /**
     * Look for matching tasks with the specified description.
     *
     * @param description Description of the task.
     * @throws ShagBotException If command entered to manage task is invalid.
     */
    private void findTaskOnDateCommand(String description) throws ShagBotException {
        if (description.startsWith("on ")) {
            handleTaskOnCommand(description.substring(3).trim());
        } else {
            throw new ShagBotException(INVALID_TASK_COMMAND_ERROR_MESSAGE);
        }
    }

    /**
     * Prints the lists of tasks so far.
     */
    private void handleListCommand() {
        ui.printTaskList(taskList.getTasks());
    }

    /**
     * Displays the exit message when users wishes to stop using the program.
     */
    private void handleByeCommand() {
        ui.printExit();
    }
    /**
     * Marks or unmarks the task based on user's command.
     *
     * @param command The user's command indicating mark or unmark.
     * @param isMark  True for Mark or False for unmark, for a task.
     * @throws ShagBotException If there are invalid inputs.
     */
    protected void handleMarkCommand(String command, boolean isMark) throws ShagBotException {
        handleInvalidMarkCommands(command);
        int taskIndex = parseTaskIndex(command);
        validateTaskIndex(taskIndex);

        Task task = taskList.getTask(taskIndex);
        if (isMark) {
            task.mark();
            ui.printTaskMarked(task);
        } else {
            task.unmark();
            ui.printTaskUnmarked(task);
        }
    }

    /**
     * Parses a command string to extract the task index.
     *
     * @param command The user's command, which must contain a numeric task index.
     * @return The task index extracted from the command
     * @throws ShagBotException If command does not contain valid numerical index.
     */
    private int parseTaskIndex(String command) throws ShagBotException {
        try {
            return Integer.parseInt(command.split(" ")[1]) - 1;
        } catch (NumberFormatException e) {
            throw new ShagBotException(INVALID_TASK_NUMBER_ERROR_MESSAGE);
        }
    }

    /**
     * Validates if the task index is valid or not.
     *
     * @param taskIndex The index of the task.
     * @throws ShagBotException If the task index is zero or less than zero.
     */
    private void validateTaskIndex(int taskIndex) throws ShagBotException {
        int numOfTasks = taskList.getTasks().length;

        if (taskIndex == -1) {
            throw new ShagBotException(TASK_INDEX_IS_ZERO_ERROR_MESSAGE);
        }

        if (taskIndex < 0) {
            throw new ShagBotException(TASK_INDEX_IS_NEGATIVE_ERROR_MESSAGE);
        }

        if (numOfTasks == 0) {
            throw new ShagBotException(NO_TASKS_AT_THE_MOMENT_ERROR_MESSAGE);
        }

        if (taskIndex >= numOfTasks) {
            throw new ShagBotException(TASK_INDEX_OUT_OF_RANGE_ERROR_MESSAGE
                    + numOfTasks + ".");
        }
    }
    /**
     * Handle invalid entries due to user forgetting to put a number behind.
     *
     * @param command "Mark" or "Unmark"
     * @throws ShagBotException If the user forgots to enter a number behind "Mark" or "Unmark".
     */
    private static void handleInvalidMarkCommands(String command) throws ShagBotException {
        if (command.trim().equalsIgnoreCase(MARK.toUpperCase())
                || command.trim().equalsIgnoreCase(UNMARK.toUpperCase())) {
            throw new ShagBotException(ENTER_A_NUMBER_ERROR_MESSAGE);
        }
    }
    /**
     * Deletes a task based on the user's command.
     *
     * @param command The user's command indicating which task to delete.
     * @throws ShagBotException If there are invalid inputs.
     */
    private void handleDeleteCommand(String command) throws ShagBotException {
        try {
            if (command.trim().equalsIgnoreCase(DELETE.toUpperCase())) {
                throw new ShagBotException(NO_NUMBER_BEHIND_ERROR_MESSAGE);
            }
            int taskIndex = Integer.parseInt(command.split(" ")[1]) - 1;
            validateTaskIndex(taskIndex);

            Task removedTask = taskList.deleteTask(taskIndex);
            ui.printTaskDeleted(removedTask, taskList.getTasks().length);
        } catch (NumberFormatException e) {
            throw new ShagBotException(INVALID_TASK_NUMBER_ERROR_MESSAGE);
        }
    }
    /**
     * Handles a {@link Todo} task after parsing the description of the todo task.
     * @param description The description of the todo task.
     * @throws ShagBotException If the description format is invalid.
     */
    private void handleTodoCommand(String description) throws ShagBotException {
        assert description != null : "Description of Todo task cannot be null.";
        if (description.isEmpty()) {
            throw new ShagBotException(INVALID_TODO_ERROR_MESSAGE);
        }
        Todo todo = new Todo(description);
        taskList.addTask(todo);
        ui.printTaskAdded(todo.toString(), taskList.getTasks().length);
    }

    /**
     * Handles a {@link Deadline} task after parsing the description of the deadline task and its due date.
     *
     * @param description The description of the deadline task , along with its due date.
     * @throws ShagBotException If the description format is invalid.
     */
    private void handleDeadlineCommand(String description) throws ShagBotException {
        assert description != null : "Description of Deadline task cannot be null.";
        String[] parts = description.split(" /by ", 2);
        if (parts.length < 2) {
            throw new ShagBotException(INVALID_DEADLINE_ERROR_MESSAGE);
        }
        try {
            Deadline deadline = new Deadline(parts[0].trim(), parts[1].trim());
            taskList.addTask(deadline);
            ui.printTaskAdded(deadline.toString(), taskList.getTasks().length);
        } catch (IllegalArgumentException e) {
            ui.printErrorMessage(e.getMessage());
        }
    }

    /**
     * Handles an {@link Event} task by parsing the description and start/end times of the event task.
     *
     * @param description The description of the event task, along with its start and end timings.
     * @throws ShagBotException If the description format is invalid.
     */
    private void handleEventCommand(String description) throws ShagBotException {
        assert description != null : "Description of Event task cannot be null.";
        String[] parts = description.split(" /from | /to ", 3);
        if (parts.length < 3) {
            throw new ShagBotException(INVALID_EVENT_ERROR_MESSAGE);
        }
        Event event = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        taskList.addTask(event);
        ui.printTaskAdded(event.toString(), taskList.getTasks().length);
    }

    /**
     * Handles the 'task on' command after parsing the specified date.
     *
     * @param dateString The date in "dd/M/yyyy" format to find tasks for.
     */
    private void handleTaskOnCommand(String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
            ui.printTasksOnDate(date, taskList.getTasks());
        } catch (DateTimeParseException e) {
            ui.printErrorMessage(INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }
    }

    /**
     * Handles the 'find' command after parsing the provided keyword.
     *
     * @param keyword The specified keyword used to filter out and search for in task descriptions.
     */
    private void handleFindCommand(String keyword) {
        assert keyword != null : "Search keyword cannot be null.";
        if (keyword.isEmpty()) {
            ui.printErrorMessage(INVALID_FIND_ERROR_MESSAGE);
            return;
        }
        Task[] foundTasks = searchForTasks(keyword);
        ui.printAnyMatchingTasks(foundTasks);
    }
    /**
     * Handles the snooze command by parsing and validating the user's input.
     *
     * @param description The String representation following the "snooze" command, containing task index
     *                    and new time info.
     * @throws ShagBotException If the format for "snooze" is invalid.
     */
    private void handleSnoozeCommand(String description) throws ShagBotException {
        validateSnoozeCommand(description);
        String[] snoozeSplitCommand = splitCommand(description);

        int taskIndex = parseTaskIndex(SNOOZE + " " + snoozeSplitCommand[0]);
        validateTaskIndex(taskIndex);
        String dateTimeInfo = snoozeSplitCommand[1].trim();
        Task taskToSnooze = taskList.getTask(taskIndex);
        if (taskToSnooze instanceof Deadline) {
            snoozeDeadline((Deadline) taskToSnooze, dateTimeInfo);
            return;
        }
        if (taskToSnooze instanceof Event) {
            snoozeEvent((Event) taskToSnooze, dateTimeInfo);
            return;
        }
        throw new ShagBotException(CANNOT_SNOZE_TODO_ERROR_MESSAGE);
    }

    /**
     * Validates if the input is not null or not empty.
     *
     * @param input The String input to validate.
     * @throws ShagBotException If the input is invalid (null or empty).
     */
    private void validateSnoozeCommand(String input) throws ShagBotException {
        if (input == null || input.trim().isEmpty()) {
            throw new ShagBotException(SPECIFY_TASK_TO_SNOOZE_ERROR_MESSAGE);
        }
    }

    /**
     * Splits the given string input, using the 'snooze' command, into tokens.
     *
     * @param input The input string to split.
     * @return A {@link String} array containing the split segments or tokens.
     * @throws ShagBotException If input by the user is invalid.
     */
    private String[] splitCommand(String input) throws ShagBotException {
        String[] tokensForSnooze = input.split(" ", 2);
        int expectedParts = 2;
        if (tokensForSnooze.length < expectedParts) {
            throw new ShagBotException(SPECIFY_TASK_TO_SNOOZE_AND_NEW_DATE_ERROR_MESSAGE);
        }
        return tokensForSnooze;
    }

    /**
     * Reschedules or snoozes a given deadline task with a new due date and time.
     *
     * @param deadline The {@link shagbot.tasks.Deadline} task to be rescheduled or snoozed.
     * @param dateTimePart A string representation of the new due date and time.
     * @throws ShagBotException if {@code dateTimePart} does not start with {@code "/by "} or/and if the
     *        new date and time string is not following a valid format.
     */
    private void snoozeDeadline(Deadline deadline, String dateTimePart) throws ShagBotException {
        if (!dateTimePart.startsWith("/by ")) {
            throw new ShagBotException(SNOOZE_DEADLINE_FAIL_ERROR_MESSAGE);
        }
        String newDateStr = dateTimePart.substring(4).trim();
        LocalDateTime newByTiming;
        try {
            newByTiming = LocalDateTime.parse(newDateStr, DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_TIME));
        } catch (DateTimeParseException e) {
            throw new ShagBotException(INVALID_DATE_FORMAT_ERROR_MESSAGE);
        }
        deadline.setByTiming(newByTiming);
        var snoozeDeadlineMessage = "For this task: " + deadline.getDescription() + DEADLINE_HAS_BEEN_RESCHEDULED_TO
                + newByTiming.format(DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_TIME));
        ui.displayMessage(snoozeDeadlineMessage);
    }

    /**
     * Reschedules or snoozes a given event task with the new start and end times.
     *
     * @param event The {@link shagbot.tasks.Event} task to be rescheduled.
     * @param dateTimePart A string representation of the new start and end times of the event.
     * @throws ShagBotException if {@code dateTimePart} does not start with {@code "/from "} or/ and lacks a proper
     *       {@code "/to "} section or/and if the new date and time string is not following a valid format.
     *
     */
    private void snoozeEvent(Event event, String dateTimePart) throws ShagBotException {
        String[] parts = dateTimePart.split(" /to ");
        if (!parts[0].startsWith("/from ") || parts.length < 2) {
            throw new ShagBotException(SNOOZE_EVENT_FAIL_ERROR_MESSAGE);
        }
        String startString = parts[0].substring(6).trim();
        String endString = parts[1].trim();

        DateTimeFormatter formattedDateAndTime = DateTimeFormatter.ofPattern(DATE_FORMAT_WITH_TIME);
        LocalDateTime newStart;
        LocalDateTime newEnd;
        try {
            newStart = LocalDateTime.parse(startString, formattedDateAndTime);
            newEnd = LocalDateTime.parse(endString, formattedDateAndTime);
        } catch (DateTimeParseException e) {
            throw new ShagBotException(SNOOZE_EVENT_FAIL_ERROR_MESSAGE);
        }
        event.setStart(newStart);
        event.setEnd(newEnd);

        var snoozeEventMessage = "This event has been rescheduled:  " + event.getDescription() + "\n\nFrom:"
                + newStart.format(formattedDateAndTime) + "\nTo: " + newEnd.format(formattedDateAndTime);
        ui.displayMessage(snoozeEventMessage);
    }
    /**
     * Handles the 'reminder' command by finding upcoming tasks within the next 48 hours.
     */
    private void handleReminderCommand() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime timePeriod = currentTime.plusHours(48);

        List<Task> upcomingTasks = getUpcomingTasks(currentTime, timePeriod);
        String reminderMessage = buildReminderMessage(upcomingTasks);
        ui.displayMessage(reminderMessage);
    }

    /**
     * Retrieves tasks based on the relevant date and time within the specified window period.
     *
     * @param currentTime  The current time of the interface used.
     * @param windowPeriod The end of the reminder window.
     * @return A {@link List} of tasks scheduled between current time and the end time of the window period.
     */
    private List<Task> getUpcomingTasks(LocalDateTime currentTime, LocalDateTime windowPeriod) {
        List<Task> upcomingTasks = new ArrayList<>();

        for (Task task : taskList.getTasks()) {
            LocalDateTime taskTime = getTasksWithinWindow(task);
            if (taskTime == null) {
                continue;
            }
            validateWindowPeriod(currentTime, windowPeriod, task, taskTime, upcomingTasks);
        }
        return upcomingTasks;
    }

    /**
     * Checks whether the specified task time falls within the defined time window. If so,
     * add the task into the list of upcoming tasks.
     *
     * @param currentTime The current time now, which is the lower bound of the window.
     * @param windowPeriod The upper bound of the time window.
     * @param task The task to be evaluated.
     * @param taskTime Time associated with the task.
     * @param upcomingTasks The list of tasks that occurs during that window frame.
     */
    private void validateWindowPeriod(LocalDateTime currentTime, LocalDateTime windowPeriod, Task task,
                                      LocalDateTime taskTime, List<Task> upcomingTasks) {
        if (taskTime.isAfter(currentTime) && taskTime.isBefore(windowPeriod)) {
            upcomingTasks.add(task);
        }
    }

    /**
     * Returns the date/time that is relevant for reminders from a task.
     * For Deadline tasks, it would be based on its due date.
     * For Event tasks, it would be based on its start time.
     *
     * @param task The {@link Task} task to extract the relevant time from.
     * @return The {@link LocalDateTime} representing the task's reminder time, or {@code null} if
     *         the following task, such as {@link Todo} task, is not applicable for reminders.
     */
    private LocalDateTime getTasksWithinWindow(Task task) {
        if (task instanceof Deadline) {
            return ((Deadline) task).getByTiming();
        }
        if (task instanceof Event) {
            return ((Event) task).getStart();
        }
        return null;
    }

    /**
     * Builds the reminder message based on the {@link List} of tasks to display to users.
     *
     * @param tasks The list of upcoming tasks within the window period of 48 hours.
     * @return A formatted string with the list of upcoming tasks or informs users if there
     *         are no tasks within that time frame.
     */
    private String buildReminderMessage(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return NO_UPCOMING_TASKS_REMINDER_ERROR;
        }

        StringBuilder sb = new StringBuilder(UPCOMING_TASKS_WITHIN_THE_NEXT_48_HOURS);
        for (int i = 0; i < tasks.size(); i++) {
            // Prints list of upcoming tasks in numerical order.
            sb.append(i + 1).append(". ").append(tasks.get(i).toString()).append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Searches for tasks that contain the given keyword in their description.
     *
     * @param keyword The keyword to search for in the tasks.
     * @return The array of tasks, as a {@link Task} array, that contains that keyword.
     */
    private Task[] searchForTasks(String keyword) {
        assert keyword != null : "Search keyword cannot be null.";

        return Arrays.stream(taskList.getTasks()).distinct()
                .filter(task -> task.getDescription().contains(keyword))
                .toArray(Task[]::new);
    }
}



