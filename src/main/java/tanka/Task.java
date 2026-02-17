package tanka;

/**
 * Base class for a single task with a description and done/not-done state.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description, initially not done.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /** Returns "X" if done, " " if not done. */
    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    /** Returns the task description. */
    public String getStatusDecription() {
        return description;
    }

    /** Marks this task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks this task as not done. */
    public void markAsUndone() {
        this.isDone = false;
    }

    /** Returns a short string representation: [icon] description. */
    @Override
    public String toString() {
        return " [" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns the line format used when saving this task to file.
     *
     * @return a string suitable for storage (e.g. "T | 0 | desc")
     */
    public abstract String toFileString();
}
