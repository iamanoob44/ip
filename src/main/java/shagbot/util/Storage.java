package shagbot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import shagbot.tasks.Deadline;
import shagbot.tasks.Event;
import shagbot.tasks.Task;
import shagbot.tasks.Todo;

/**
 * Represents a storage class used to load and save tasks to the txt file.
 */
public class Storage {
    private static final String DATE_FORMAT = "dd/M/yyyy HHmm";
    private static final String INVALID_TASK_TYPE_ERROR_MESSAGE = "Invalid task type";
    private static final String COMPLETE_TASK = "1";
    private static final String TODO = "T";
    private static final String DEADLINE = "D";
    private static final String EVENT = "E";
    private static final String INVALID_TODO_FORMAT = "Invalid Todo format, skipping line: ";
    private static final String INVALID_DEADLINE_FORMAT = "Invalid Deadline format, skipping line: ";
    private static final String INVALID_EVENT_FORMAT = "Invalid Event format, skipping line: ";
    private static final String UNSUPPORTED_TASK_TYPE = "Unsupported task type, skipping line: ";
    private static final String ERROR_PARSING_LINE = "Error parsing line: ";
    private final String filePath;

    /**
     * Constructor for the {@code Storage} class.
     *
     * @param filePath The relative filepath to the file where saved tasks are stored.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty.";
        this.filePath = filePath;
    }

    /**
     * Loads saved tasks from the file.
     * <p>
     * This method also utilises try-with-resources to ensure the {@code BufferedReader} is
     * automatically closed after reading the file, increasing code maintainability.
     * </p>
     *
     * @return An {@link ArrayList} of saved tasks loaded from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public ArrayList<Task> loadSavedTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            // Create the file and directory if they don't exist
            file.getParentFile().mkdirs();
            file.createNewFile();
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        }
        return tasks;
    }

    /**
     * Parses a line from the storage file into a {@code Task} object.
     *
     * @param line The line to parse, expected to be formatted as per the task type.
     * @return The corresponding {@code Task} object, or {@code null} if parsing fails.
     */
    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");

        // One way to check if lines are erroneous entries / corrupted file
        if (parts.length < 3) {
            System.err.println("Skipping corrupted line: " + line);
            return null;
        }

        String type = parts[0];
        boolean isDone = COMPLETE_TASK.equals(parts[1]);
        String description = parts[2];

        try {
            switch (type) {
            case TODO:
                if (parts.length != 3) {
                    System.err.println(INVALID_TODO_FORMAT + line);
                    return null;
                }
                return createTodo(description, isDone);
            case DEADLINE:
                if (parts.length != 4) {
                    System.err.println(INVALID_DEADLINE_FORMAT + line);
                    return null;
                }
                return createDeadline(description, parts[3], isDone);
            case EVENT:
                if (parts.length != 5) {
                    System.err.println(INVALID_EVENT_FORMAT + line);
                    return null;
                }
                return createEvent(description, parts[3], parts[4], isDone);
            default:
                System.err.println(UNSUPPORTED_TASK_TYPE + line);
                return null;
            }
        } catch (Exception e) {
            System.err.println(ERROR_PARSING_LINE + line);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new Todo task.
     *
     * @param description The description of Todo task.
     * @param isDone Whether the task is completed or not.
     * @return The created Todo task.
     */
    private Todo createTodo(String description, boolean isDone) {
        Todo todo = new Todo(description);
        if (isDone) {
            todo.mark();
        }
        return todo;
    }

    /**
     * Creates a new Deadline task.
     *
     * @param description The description of the deadline task.
     * @param by The deadline timing.
     * @param isDone Whether the task is completed or not.
     * @return The created Deadline task.
     */
    private Deadline createDeadline(String description, String by, boolean isDone) {
        Deadline deadline = new Deadline(description, by);
        if (isDone) {
            deadline.mark();
        }
        return deadline;
    }

    /**
     * Creates a new Event task.
     *
     * @param description The description of event task.
     * @param start The start time of the event.
     * @param end The end time of the event.
     * @param isDone Whether the task is completed or not.
     * @return The created Event task.
     */
    private Event createEvent(String description, String start, String end, boolean isDone) {
        Event event = new Event(description, start, end);
        if (isDone) {
            event.mark();
        }
        return event;
    }


    /**
     * Saves the lists of tasks to the file.
     * <p>
     * This method also utilises try-with-resources to ensure the {@code BufferedReader} is
     * automatically closed after writing to the file, increasing maintainability.
     * </p>
     *
     * @param tasks The Arraylist of tasks to save.
     * @throws IOException If an I/O error occurs while writing tasks to file.
     */
    public void saveTasksToFile(ArrayList<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(taskToFileFormat(task));
                writer.newLine();
            }
        }
    }

    /**
     * Converts a {@code Task} object into a file format for saving to file.
     *
     * @param task The {@code Todo}, {@code Event}, or {@code Deadline} task to convert to string representation
     *             and saved to the dataoftasks.txt file.
     * @return A string representation of the task in file format.
     * @throws IllegalArgumentException If task type is invalid and not supported.
     */
    private String taskToFileFormat(Task task) {
        assert task != null : "Task cannot be null";
        var completionStatus = (task.isDone() ? COMPLETE_TASK : "0") + " | " + task.getDescription();

        if (task instanceof Deadline deadline) {
            return "D | " + completionStatus + " | " + formattedDateOfTask(deadline.getByTiming());
        } else if (task instanceof Event event) {
            return "E | " + completionStatus + " | " + formattedDateOfTask(event.getStart()) + " | "
                    + formattedDateOfTask(event.getEnd());
        } else if (task instanceof Todo) {
            return "T | " + completionStatus;
        } else {
            throw new IllegalArgumentException(INVALID_TASK_TYPE_ERROR_MESSAGE);
        }
    }

    /**
     * Formats date using the predefined date format specified.
     *
     * @param date The date to format for deadline and event tasks.
     * @return A formatted date string.
     */
    private String formattedDateOfTask(java.time.LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}



