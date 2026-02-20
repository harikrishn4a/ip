package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void defaultConstructor_isEmpty() {
        TaskList list = new TaskList();
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void varargsConstructor_zeroTasks() {
        TaskList list = new TaskList(new Task[0]);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void varargsConstructor_withTasks() {
        Todo a = new Todo("a");
        Todo b = new Todo("b");
        TaskList list = new TaskList(a, b);
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
        assertEquals(a, list.get(0));
        assertEquals(b, list.get(1));
    }

    @Test
    public void arrayListConstructor_empty() {
        TaskList list = new TaskList(new ArrayList<>());
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void arrayListConstructor_withTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("x"));
        tasks.add(new Todo("y"));
        TaskList list = new TaskList(tasks);
        assertFalse(list.isEmpty());
        assertEquals(2, list.size());
        assertEquals("x", list.get(0).getStatusDescription());
        assertEquals("y", list.get(1).getStatusDescription());
    }

    @Test
    public void add_increasesSizeAndAppends() {
        TaskList list = new TaskList();
        Todo t = new Todo("task");
        list.add(t);
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(t, list.get(0));
        list.add(new Todo("second"));
        assertEquals(2, list.size());
        assertEquals("second", list.get(1).getStatusDescription());
    }

    @Test
    public void remove_returnsTaskAndDecreasesSize() {
        Todo a = new Todo("a");
        Todo b = new Todo("b");
        TaskList list = new TaskList(a, b);
        Task removed = list.remove(1);
        assertEquals(b, removed);
        assertEquals(1, list.size());
        assertEquals(a, list.get(0));
        Task removedFirst = list.remove(0);
        assertEquals(a, removedFirst);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void getList_returnsUnderlyingList() {
        Todo a = new Todo("a");
        TaskList list = new TaskList(a);
        ArrayList<Task> got = list.getList();
        assertEquals(1, got.size());
        assertEquals(a, got.get(0));
    }
}
