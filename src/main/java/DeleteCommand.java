public class DeleteCommand extends Command {
    private final int index;

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