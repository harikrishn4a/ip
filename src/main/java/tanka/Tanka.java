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
     * Creates a Tanka instance with the default data file (for JavaFX GUI).
     */
    public Tanka() {
        this("data/tasks.txt");
    }

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
     * Returns the welcome message shown when the GUI starts (same text as the CLI welcome).
     *
     * @return the welcome string for the GUI
     */
    public String getWelcomeMessage() {
        GuiUi guiUi = new GuiUi();
        guiUi.showWelcome();
        return guiUi.getResponseAndClear();
    }

    /**
     * Generates a response for the given user input for use in the GUI.
     * Parses the input, executes the command, and returns the response string.
     *
     * @param input raw user command (e.g. "list", "todo read book")
     * @return the response string to show in the chat, or error message if parsing/execution fails
     */
    public String getResponse(String input) {
        GuiUi guiUi = new GuiUi();
        try {
            Command c = Parser.parse(input);
            c.execute(tasks, guiUi, storage);
            return guiUi.getResponseAndClear();
        } catch (TankaException e) {
            return e.getMessage();
        }
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
