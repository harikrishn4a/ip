package tanka;

/**
 * A task with a start and end time (e.g. an event).
 */
public class Event extends Task {
    private String start;
    private String end;

    /**
     * Creates an event with description, start, and end.
     *
     * @param description the task description
     * @param start       start time or date string
     * @param end         end time or date string
     */
    public Event(String description, String start, String end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return " [E]" + super.toString() + " (from: " + start + " to: " + end + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? 1 : 0) + " | " + description + " | " + start + " | " + end;
    }
}
