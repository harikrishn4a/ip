package tanka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDate dueBy;
    public Deadline(String description, LocalDate dueBy) {
        super(description);
        this.dueBy = dueBy;
    }
    
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
