package tanka;

/**
 * Command to mark a task as not done at a given 0-based index.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Creates an UnmarkCommand for the task at the given index.
     *
     * @param index 0-based index of the task to unmark
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TankaException {
        if (index < 0 || index >= tasks.size()) {
            throw new TankaException("This task number does not exist!");
        }
        tasks.get(index).markAsUndone();
        try {
            storage.saveTasks(tasks.getList());
        } catch (TankaException e) {
            ui.showError("  Failed to save: " + e.getMessage());
        }
        ui.showMarkedUndone(tasks.get(index));
    }
}
