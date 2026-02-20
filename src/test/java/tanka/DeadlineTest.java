package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toString_notDone_showsSpaceIconAndDueBy() {
        Deadline d = new Deadline("submit report", LocalDate.of(2025, 3, 15));
        assertEquals(" [D] [ ] submit report (by: Mar 15 2025)", d.toString());
        assertEquals(" ", d.getStatusIcon());
        assertEquals(LocalDate.of(2025, 3, 15), d.getDueBy());
    }

    @Test
    public void toString_done_showsXIcon() {
        Deadline d = new Deadline("submit report", LocalDate.of(2025, 3, 15));
        d.markAsDone();
        assertEquals(" [D] [X] submit report (by: Mar 15 2025)", d.toString());
        assertEquals("X", d.getStatusIcon());
    }

    @Test
    public void toFileString_notDone_hasZero() {
        Deadline d = new Deadline("submit report", LocalDate.of(2025, 3, 15));
        assertEquals("D | 0 | submit report | 2025-03-15", d.toFileString());
    }

    @Test
    public void toFileString_done_hasOne() {
        Deadline d = new Deadline("submit report", LocalDate.of(2025, 3, 15));
        d.markAsDone();
        assertEquals("D | 1 | submit report | 2025-03-15", d.toFileString());
    }

    @Test
    public void markAsUndone_afterDone_restoresNotDone() {
        Deadline d = new Deadline("task", LocalDate.of(2025, 1, 1));
        d.markAsDone();
        assertEquals("X", d.getStatusIcon());
        d.markAsUndone();
        assertEquals(" ", d.getStatusIcon());
        assertEquals(" [D] [ ] task (by: Jan 1 2025)", d.toString());
        assertEquals("D | 0 | task | 2025-01-01", d.toFileString());
    }

    @Test
    public void getDueBy_returnsConstructorValue() {
        LocalDate date = LocalDate.of(2026, 12, 31);
        Deadline d = new Deadline("end of year", date);
        assertEquals(date, d.getDueBy());
    }
}
