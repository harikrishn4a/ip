package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void loadTasks_fileMissing_returnsEmptyList() throws TankaException {
        Path nonExistent = tempDir.resolve("nonexistent.txt");
        Storage storage = new Storage(nonExistent.toString());
        ArrayList<Task> tasks = storage.loadTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    public void loadTasks_fileWithValidLines_returnsParsedTasks() throws Exception {
        Path file = tempDir.resolve("tasks.txt");
        try (FileWriter w = new FileWriter(file.toFile())) {
            w.write("T | 0 | read book\n");
            w.write("D | 1 | submit report | 2025-03-15\n");
            w.write("E | 0 | meeting | Mon 2pm | Mon 3pm\n");
        }
        Storage storage = new Storage(file.toString());
        ArrayList<Task> tasks = storage.loadTasks();
        assertEquals(3, tasks.size());
        assertEquals(" [T] [ ] read book", tasks.get(0).toString());
        assertEquals(" [D] [X] submit report (by: Mar 15 2025)", tasks.get(1).toString());
        assertEquals(" [E] [ ] meeting (from: Mon 2pm to: Mon 3pm)", tasks.get(2).toString());
    }

    @Test
    public void loadTasks_fileWithInvalidLine_throwsTankaException() throws IOException {
        Path file = tempDir.resolve("bad.txt");
        try (FileWriter w = new FileWriter(file.toFile())) {
            w.write("T | 0 | read book\n");
            w.write("X | 0 | invalid type\n");
        }
        Storage storage = new Storage(file.toString());
        assertThrows(TankaException.class, storage::loadTasks);
    }

    @Test
    public void saveTasks_thenLoad_roundTripsCorrectly() throws TankaException {
        Path file = tempDir.resolve("roundtrip.txt");
        Storage storage = new Storage(file.toString());
        ArrayList<Task> original = new ArrayList<>();
        original.add(new Todo("todo task"));
        original.add(new Deadline("deadline task", LocalDate.of(2025, 6, 1)));
        original.add(new Event("event task", "start", "end"));
        storage.saveTasks(original);
        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(original.size(), loaded.size());
        for (int i = 0; i < original.size(); i++) {
            assertEquals(original.get(i).toFileString(), loaded.get(i).toFileString());
        }
    }

    @Test
    public void loadTasks_emptyFile_returnsEmptyList() throws Exception {
        Path file = tempDir.resolve("empty.txt");
        Files.write(file, new byte[0]);
        Storage storage = new Storage(file.toString());
        ArrayList<Task> tasks = storage.loadTasks();
        assertEquals(0, tasks.size());
    }
}
