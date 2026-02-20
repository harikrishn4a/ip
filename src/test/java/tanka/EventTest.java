package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void toString_notDone_showsSpaceIconAndFromTo() {
        Event e = new Event("meeting", "Mon 2pm", "Mon 3pm");
        assertEquals(" [E] [ ] meeting (from: Mon 2pm to: Mon 3pm)", e.toString());
        assertEquals(" ", e.getStatusIcon());
    }

    @Test
    public void toString_done_showsXIcon() {
        Event e = new Event("meeting", "Mon 2pm", "Mon 3pm");
        e.markAsDone();
        assertEquals(" [E] [X] meeting (from: Mon 2pm to: Mon 3pm)", e.toString());
        assertEquals("X", e.getStatusIcon());
    }

    @Test
    public void toFileString_notDone_hasZero() {
        Event e = new Event("meeting", "Mon 2pm", "Mon 3pm");
        assertEquals("E | 0 | meeting | Mon 2pm | Mon 3pm", e.toFileString());
    }

    @Test
    public void toFileString_done_hasOne() {
        Event e = new Event("meeting", "Mon 2pm", "Mon 3pm");
        e.markAsDone();
        assertEquals("E | 1 | meeting | Mon 2pm | Mon 3pm", e.toFileString());
    }

    @Test
    public void markAsUndone_afterDone_restoresNotDone() {
        Event e = new Event("task", "start", "end");
        e.markAsDone();
        assertEquals("X", e.getStatusIcon());
        e.markAsUndone();
        assertEquals(" ", e.getStatusIcon());
        assertEquals(" [E] [ ] task (from: start to: end)", e.toString());
        assertEquals("E | 0 | task | start | end", e.toFileString());
    }

    @Test
    public void descriptionAndTimes_preservedInToStringAndToFileString() {
        Event e = new Event("  workshop  ", "2025-01-10", "2025-01-11");
        assertEquals(" [E] [ ]   workshop   (from: 2025-01-10 to: 2025-01-11)", e.toString());
        assertEquals("E | 0 |   workshop   | 2025-01-10 | 2025-01-11", e.toFileString());
    }
}
