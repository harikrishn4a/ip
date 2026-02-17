package tanka;

/**
 * Command to add a todo, deadline, or event from raw user input.
 */
public class AddCommand extends Command {
    private final String userInput;

    /**
     * Creates an AddCommand that will parse and add a task from the given input.
     *
     * @param userInput raw input (e.g. "todo x", "deadline y /by 2025-01-01")
     */
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
