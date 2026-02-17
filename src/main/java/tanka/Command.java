package tanka;

/**
 * Represents a user command that can be executed against the task list, UI, and storage.
 */
public abstract class Command {

    /**
     * Executes this command.
     *
     * @param tasks   the task list
     * @param ui      the UI for output
     * @param storage the storage to persist tasks
     * @throws TankaException if execution fails (e.g. invalid index)
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TankaException;

    /** Returns true if this command is the exit (bye) command. */
    public boolean isExit() {
        return false;
    }
}
