package shagbot.commands;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import shagbot.exceptions.ShagBotException;
import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;
import shagbot.tasks.TaskList;
import shagbot.util.Ui;

/**
 * The class represents a command to remind users of any upcoming tasks within 48 hours.
 */
public class ReminderCommand extends Command {
    private static final String NO_UPCOMING_TASKS_REMINDER_ERROR =
            "No upcoming tasks within the next 48 hours.";
    private static final String UPCOMING_TASKS_WITHIN_THE_NEXT_48_HOURS =
            "Upcoming tasks within the next 48 hours:\n";
    private static final int WINDOW_PERIOD = 48;

    @Override
    public boolean executeCommand(TaskList taskList, Ui ui) throws ShagBotException {
        assert ui != null : "ui instance cannot be null when executing command.";
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime windowEnd = currentTime.plusHours(WINDOW_PERIOD);

        List<Task> upcomingTasks = getUpcomingTasks(taskList, currentTime, windowEnd);
        String reminderMessage = buildReminderMessage(upcomingTasks);

        ui.displayMessage(reminderMessage);
        return true;
    }

    /**
     * Retrieves the relevant time information, {@code byTiming} in {@link Deadline} or {@code start} in
     * {@link Event}.
     *
     * @param task The task from which to extract the time information.
     * @return The {@link LocalDateTime} representation of the time for the task, or {@code null} if
     *         the task is neither a deadline nor an event.
     */
    private LocalDateTime getTaskTime(Task task) {
        if (task instanceof Deadline) {
            return ((Deadline) task).getByTiming();
        }
        if (task instanceof Event) {
            return ((Event) task).getStart();
        }
        return null;
    }

    /**
     * Checks whether the specified task's time falls within the 48 hours window period.
     *
     * @param taskTime Time associated with the task.
     * @param windowStart Beginning of window.
     * @param windowEnd End of window.
     * @return {@code true} if {@code taskTime} is strictly after {@code windowStart} and also strictly before
     *         {@code windowEnd} ; Within the Window Period. Otherwise, it returns {@code false}.
     */
    private boolean isWithinWindowPeriod(LocalDateTime taskTime, LocalDateTime windowStart, LocalDateTime windowEnd) {
        return taskTime.isAfter(windowStart) && taskTime.isBefore(windowEnd);
    }

    /**
     * Filters and returns tasks from the given task list that occurs within the specified time window.
     *
     * @param taskList List of tasks to search and filter through.
     * @param windowStart Beginning of window.
     * @param windowEnd End of window.
     * @return A {@link List} of tasks that occur within the specified window;
     *         if no tasks are found, an empty list is returned.
     */
    private List<Task> getUpcomingTasks(TaskList taskList, LocalDateTime windowStart, LocalDateTime windowEnd) {
        List<Task> upcomingTasks = new ArrayList<>();
        for (Task task : taskList.getTasks()) {
            LocalDateTime taskTime = getTaskTime(task);
            if (taskTime != null && isWithinWindowPeriod(taskTime, windowStart, windowEnd)) {
                upcomingTasks.add(task);
            }
        }
        return upcomingTasks;
    }

    /**
     * Builds the reminder message from the list of upcoming tasks within the 48 hours period.
     *
     * @param upcomingTasks List of upcoming tasks.
     * @return A formatted reminder message listing all the upcoming tasks, or a message indicating no tasks are found
     *         if there are no upcoming tasks in the list.
     */
    private String buildReminderMessage(List<Task> upcomingTasks) {
        if (upcomingTasks.isEmpty()) {
            return NO_UPCOMING_TASKS_REMINDER_ERROR;
        }
        StringBuilder sb = new StringBuilder(UPCOMING_TASKS_WITHIN_THE_NEXT_48_HOURS);
        for (int i = 0; i < upcomingTasks.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(upcomingTasks.get(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }
}


