package tanka;

/**
 * Command to delete a task at a given 0-based index.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a DeleteCommand for the task at the given index.
     *
     * @param index 0-based index of the task to delete
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TankaException {
        if (index < 0 || index >= tasks.size()) {
            throw new TankaException("This task number does not exist!");
        }
        Task deletedTask = tasks.remove(index);
        ui.showDeleted(deletedTask, tasks.size());

        try {
            storage.saveTasks(tasks.getList());
        } catch (TankaException e) {
            ui.showError("  Failed to save: " + e.getMessage());
        }
    }
}
