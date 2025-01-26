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


public class Storage {
    private final String filePath;

    /**
     * Constructor for the Storage class.
     *
     * @param filePath The relative filepath to the file where saved tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads saved tasks from the file.
     *
     * @return An ArrayList of saved tasks loaded from the file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            // Create the file and directory if they don't exist
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                case "T":
                    Todo todo = new Todo(description);
                    if (isDone) {
                        todo.mark();
                    }
                    tasks.add(todo);
                    break;
                case "D":
                    Deadline deadline = new Deadline(description, parts[3]);
                    if (isDone) {
                        deadline.mark();
                    }
                    tasks.add(deadline);
                    break;
                case "E":
                    Event event = new Event(description, parts[3], parts[4]);
                    if (isDone) {
                        event.mark();
                    }
                    tasks.add(event);
                    break;
                default:
                    break; // Do nothing for unsupported task type.
                }
            }
        }
        return tasks;
    }

    /**
     * Saves the lists of tasks to the file.
     *
     * @param tasks The Arraylist of tasks to save.
     * @throws IOException If an I/O error occurs while writing tasks to file.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(taskToFileFormat(task));
                writer.newLine();
            }
        }
    }

    /**
     * Converts a Task object into file format.
     *
     * @param task The task to convert to the data.txt file.
     * @return A string representation of the task in file format.
     * @throws IllegalArgumentException If task type is invalid and not supported.
     */
    private String taskToFileFormat(Task task) {
        if (task instanceof Deadline) {
            return "D | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription() + " | " +
                    ((Deadline) task).getByTiming().format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        } else if (task instanceof Event) {
            return "E | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription() + " | " +
                    ((Event) task).getStart().format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm")) + " | " +
                    ((Event) task).getEnd().format(DateTimeFormatter.ofPattern("d/M/yyyy HHmm"));
        } else if (task instanceof Todo) {
            return "T | " + (task.isDone() ? "1" : "0") + " | " + task.getDescription();
        } else {
            throw new IllegalArgumentException("Invalid task type");
        }
    }

}


