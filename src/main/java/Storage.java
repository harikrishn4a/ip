import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> loadTasks() throws TankaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // Ensure access to parent directory, or create.
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // First Run Validity: If file does not exist, return empty list
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
        } catch (FileNotFoundException e) {
            // File disappeared between exists() and open, return tasks as it is
            return tasks;
        }
        return tasks;
    }

    public void saveTasks(ArrayList<Task> tasks) throws TankaException {
        // Get File and parent dir, use FileWriter to write it
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileString() + System.lineSeparator());
            }
            writer.close();  
        } catch (IOException e) {
            throw new TankaException("Failed to save tasks" + e.getMessage());
        }
    }
}