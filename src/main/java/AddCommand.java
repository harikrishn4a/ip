public class AddCommand extends Command {
    private final String userInput;

    public AddCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TankaException {
        Task newTask = Parser.parseTask(userInput);
        tasks.add(newTask);
        ui.showAdded(newTask, tasks.size());

        try {
            storage.saveTasks(tasks.getList());
        } catch (TankaException e) {
            ui.showError("  Failed to save: " + e.getMessage());
        }
    }
}
