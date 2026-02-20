package tanka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Loads and saves tasks to a text file using {@link Parser#parseFromFile} and {@link Task#toFileString}.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage that uses the given file path.
     *
     * @param filePath path to the data file
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "file path must not be null or empty";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file. Creates parent directories if needed. Returns an empty list if file does not exist.
     *
     * @return list of tasks (may be empty)
     * @throws TankaException if a line in the file is invalid
     */
    public ArrayList<Task> loadTasks() throws TankaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        ensureParentDirExists(file);

        if (!file.exists()) {
            return tasks;
        }

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                tasks.add(Parser.parseFromFile(line));
            }
            scanner.close();
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) {
                return tasks;
            }
            throw new TankaException("Cannot read the task file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Overwrites the file with the given tasks. Creates parent directories if needed.
     *
     * @param tasks the tasks to save
     * @throws TankaException if writing fails
     */
    public void saveTasks(ArrayList<Task> tasks) throws TankaException {
        assert tasks != null : "task list must not be null";
        // Get File and parent dir, use FileWriter to write it
        try {
            File file = new File(filePath);
            ensureParentDirExists(file);
            FileWriter writer = new FileWriter(file);
            String content = tasks.stream()
                    .map(Task::toFileString)
                    .collect(Collectors.joining(System.lineSeparator()));
            writer.write(content);
            if (!tasks.isEmpty()) {
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            throw new TankaException("Failed to save tasks: " + e.getMessage());
        }
    }

    private void ensureParentDirExists(File file) {
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
    }
}
