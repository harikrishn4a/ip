public class UnmarkCommand extends Command{
    private final int index;
    
    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TankaException {
        tasks.get(index).markAsUndone();
        try {
            storage.saveTasks(tasks.getList());
        } catch (TankaException e) {
            ui.showError("  Failed to save: " + e.getMessage());
        }
        ui.showMarkedUndone(tasks.get(index));
    }
    
}
