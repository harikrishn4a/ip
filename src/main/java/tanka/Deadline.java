package tanka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A task with a due date.
 */
public class Deadline extends Task {
    private LocalDate dueBy;

    /**
     * Creates a deadline with description and due date.
     *
     * @param description the task description
     * @param dueBy       the due date
     */
    public Deadline(String description, LocalDate dueBy) {
        super(description);
        this.dueBy = dueBy;
    }

    /** Returns the due date. */
    public LocalDate getDueBy() {
        return dueBy;
    }

    @Override
    public String toString() {
        return " [D]" + super.toString() + " (by: " + dueBy.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? 1 : 0) + " | " + description + " | " + dueBy.toString();
    }
}
