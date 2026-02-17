package tanka;

/**
 * Main application class for Tanka, a task list manager.
 * Coordinates storage, task list, and UI to run the command loop.
 */
public class Tanka {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a Tanka instance and loads tasks from the given file path.
     * If loading fails, starts with an empty task list and shows a loading error.
     *
     * @param filePath path to the data file (e.g. "data/tasks.txt")
     */
    public Tanka(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        // Loads tasks from file on startup
        try {
            // Wrap tasks in TaskList
            tasks = new TaskList(storage.loadTasks());
        } catch (TankaException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main command loop: shows welcome, reads commands, parses and executes them until bye.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (TankaException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    /**
     * Entry point. Starts Tanka with the default data file and runs the app.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        new Tanka("data/tasks.txt").run();
    }
}
