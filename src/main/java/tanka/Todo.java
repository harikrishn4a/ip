package tanka;

/**
 * A task with no date or time; just a description.
 */
public class Todo extends Task {

    /**
     * Creates a todo with the given description.
     *
     * @param decription the task description
     */
    public Todo(String decription) {
        super(decription);
    }

    @Override
    public String toString() {
        return " [T]" + super.toString();
    }

    @Override
    public String toFileString() {
        return "T | " + (isDone ? 1 : 0) + " | " + description;
    }
}
