package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TodoTest {

    @Test
    public void toString_notDone_showsSpaceIcon() {
        Todo t = new Todo("read book");
        assertEquals(" [T] [ ] read book", t.toString());
        assertEquals(" ", t.getStatusIcon());
    }

    @Test
    public void toString_done_showsXIcon() {
        Todo t = new Todo("read book");
        t.markAsDone();
        assertEquals(" [T] [X] read book", t.toString());
        assertEquals("X", t.getStatusIcon());
    }

    @Test
    public void toFileString_notDone_hasZero() {
        Todo t = new Todo("read book");
        assertEquals("T | 0 | read book", t.toFileString());
    }

    @Test
    public void toFileString_done_hasOne() {
        Todo t = new Todo("read book");
        t.markAsDone();
        assertEquals("T | 1 | read book", t.toFileString());
    }

    @Test
    public void markAsUndone_afterDone_restoresNotDone() {
        Todo t = new Todo("task");
        t.markAsDone();
        assertEquals("X", t.getStatusIcon());
        t.markAsUndone();
        assertEquals(" ", t.getStatusIcon());
        assertEquals(" [T] [ ] task", t.toString());
        assertEquals("T | 0 | task", t.toFileString());
    }

    @Test
    public void description_preservedInToStringAndToFileString() {
        Todo t = new Todo("  buy milk  ");
        assertEquals(" [T] [ ]   buy milk  ", t.toString());
        assertEquals("T | 0 |   buy milk  ", t.toFileString());
    }
}
