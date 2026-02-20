package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CommandTest {

    @TempDir
    Path tempDir;

    private StubUi stubUi;
    private Storage storage;
    private TaskList tasks;

    @BeforeEach
    public void setUp() throws Exception {
        stubUi = new StubUi();
        String path = tempDir.resolve("tasks.txt").toString();
        storage = new Storage(path);
        tasks = new TaskList();
    }

    // --- AddCommand ---
    @Test
    public void addCommand_execute_addsTaskAndShowsAdded() throws TankaException {
        AddCommand cmd = new AddCommand("todo buy milk");
        cmd.execute(tasks, stubUi, storage);
        assertEquals(1, tasks.size());
        assertEquals("buy milk", tasks.get(0).getStatusDescription());
        List<String> msgs = stubUi.getMessages();
        assertTrue(msgs.stream().anyMatch(m -> m.startsWith("showAdded:") && m.endsWith(":1")));
    }

    @Test
    public void addCommand_duplicateDescription_throwsTankaException() throws TankaException {
        tasks.add(new Todo("same task"));
        AddCommand cmd = new AddCommand("todo same task");
        assertThrows(TankaException.class, () -> cmd.execute(tasks, stubUi, storage));
        assertEquals(1, tasks.size());
    }

    @Test
    public void addCommand_execute_persistsToStorage() throws TankaException {
        AddCommand cmd = new AddCommand("todo persist me");
        cmd.execute(tasks, stubUi, storage);
        TaskList loaded = new TaskList(storage.loadTasks());
        assertEquals(1, loaded.size());
        assertEquals("persist me", loaded.get(0).getStatusDescription());
    }

    // --- MarkCommand ---
    @Test
    public void markCommand_execute_marksTaskAndShowsMarkedDone() throws TankaException {
        Todo t = new Todo("task");
        tasks.add(t);
        MarkCommand cmd = new MarkCommand(0);
        cmd.execute(tasks, stubUi, storage);
        assertTrue(t.isDone());
        assertTrue(stubUi.getMessages().stream().anyMatch(m -> m.startsWith("showMarkedDone:")));
    }

    @Test
    public void markCommand_invalidIndex_throwsTankaException() {
        tasks.add(new Todo("only one"));
        MarkCommand cmd = new MarkCommand(1);
        assertThrows(TankaException.class, () -> cmd.execute(tasks, stubUi, storage));
        assertFalse(tasks.get(0).isDone());
    }

    // --- UnmarkCommand ---
    @Test
    public void unmarkCommand_execute_unmarksTaskAndShowsMarkedUndone() throws TankaException {
        Todo t = new Todo("task");
        t.markAsDone();
        tasks.add(t);
        UnmarkCommand cmd = new UnmarkCommand(0);
        cmd.execute(tasks, stubUi, storage);
        assertFalse(t.isDone());
        assertTrue(stubUi.getMessages().stream().anyMatch(m -> m.startsWith("showMarkedUndone:")));
    }

    @Test
    public void unmarkCommand_invalidIndex_throwsTankaException() {
        tasks.add(new Todo("only one"));
        UnmarkCommand cmd = new UnmarkCommand(1);
        assertThrows(TankaException.class, () -> cmd.execute(tasks, stubUi, storage));
    }

    // --- DeleteCommand ---
    @Test
    public void deleteCommand_execute_removesTaskAndShowsDeleted() throws TankaException {
        Todo t = new Todo("to delete");
        tasks.add(t);
        DeleteCommand cmd = new DeleteCommand(0);
        cmd.execute(tasks, stubUi, storage);
        assertEquals(0, tasks.size());
        assertTrue(stubUi.getMessages().stream().anyMatch(m -> m.contains("showDeleted:") && m.endsWith(":0")));
    }

    @Test
    public void deleteCommand_invalidIndex_throwsTankaException() {
        tasks.add(new Todo("only one"));
        DeleteCommand cmd = new DeleteCommand(1);
        assertThrows(TankaException.class, () -> cmd.execute(tasks, stubUi, storage));
        assertEquals(1, tasks.size());
    }

    // --- ListCommand ---
    @Test
    public void listCommand_execute_callsShowTaskListWithCurrentList() throws TankaException {
        tasks.add(new Todo("a"));
        tasks.add(new Todo("b"));
        ListCommand cmd = new ListCommand();
        cmd.execute(tasks, stubUi, storage);
        List<String> msgs = stubUi.getMessages();
        assertTrue(msgs.stream().anyMatch(m -> m.equals("showTaskList:2")));
    }

    @Test
    public void listCommand_emptyList_showsEmptyListSize() throws TankaException {
        ListCommand cmd = new ListCommand();
        cmd.execute(tasks, stubUi, storage);
        assertTrue(stubUi.getMessages().stream().anyMatch(m -> m.equals("showTaskList:0")));
    }

    // --- FindCommand ---
    @Test
    public void findCommand_execute_showsMatchingTasks() throws TankaException {
        tasks.add(new Todo("buy milk"));
        tasks.add(new Todo("buy bread"));
        tasks.add(new Todo("read book"));
        FindCommand cmd = new FindCommand("buy");
        cmd.execute(tasks, stubUi, storage);
        List<String> msgs = stubUi.getMessages();
        assertTrue(msgs.stream().anyMatch(m -> m.equals("showMatchingTasks:2")));
    }

    @Test
    public void findCommand_noMatch_showsEmptyMatching() throws TankaException {
        tasks.add(new Todo("buy milk"));
        FindCommand cmd = new FindCommand("xyz");
        cmd.execute(tasks, stubUi, storage);
        assertTrue(stubUi.getMessages().stream().anyMatch(m -> m.equals("showMatchingTasks:0")));
    }

    // --- RemindCommand ---
    @Test
    public void remindCommand_execute_showsDeadlinesInWindow() throws TankaException {
        LocalDate today = LocalDate.now();
        tasks.add(new Deadline("due today", today));
        tasks.add(new Deadline("due tomorrow", today.plusDays(1)));
        tasks.add(new Todo("not a deadline"));
        RemindCommand cmd = new RemindCommand(7);
        cmd.execute(tasks, stubUi, storage);
        List<String> msgs = stubUi.getMessages();
        assertTrue(msgs.stream().anyMatch(m -> m.startsWith("showReminders:")));
        // Should have 2 deadlines in window (today and tomorrow)
        assertTrue(msgs.stream().anyMatch(m -> m.equals("showReminders:2")));
    }

    @Test
    public void remindCommand_doneDeadline_excludedFromReminders() throws TankaException {
        LocalDate today = LocalDate.now();
        Deadline d = new Deadline("done task", today);
        d.markAsDone();
        tasks.add(d);
        RemindCommand cmd = new RemindCommand(7);
        cmd.execute(tasks, stubUi, storage);
        assertTrue(stubUi.getMessages().stream().anyMatch(m -> m.equals("showReminders:0")));
    }

    // --- ExitCommand ---
    @Test
    public void exitCommand_isExit_returnsTrue() {
        ExitCommand cmd = new ExitCommand();
        assertTrue(cmd.isExit());
    }

    @Test
    public void exitCommand_execute_callsShowBye() throws TankaException {
        ExitCommand cmd = new ExitCommand();
        cmd.execute(tasks, stubUi, storage);
        assertTrue(stubUi.getMessages().stream().anyMatch(m -> m.equals("showBye")));
    }
}
