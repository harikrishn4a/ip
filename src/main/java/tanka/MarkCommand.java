package tanka;

/**
 * Command to mark a task as done at a given 0-based index.
 */
public class MarkCommand extends Command {
    private final int index;

    /**
     * Creates a MarkCommand for the task at the given index.
     *
     * @param index 0-based index of the task to mark done
     */
    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TankaException {
        tasks.get(index).markAsDone();
        try {
            storage.saveTasks(tasks.getList());
        } catch (TankaException e) {
            ui.showError("  Failed to save: " + e.getMessage());
        }
        ui.showMarkedDone(tasks.get(index));
    }
}
