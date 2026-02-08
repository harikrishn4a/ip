public class MarkCommand extends Command {
    private final int index;

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
