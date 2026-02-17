package tanka;

public class Tanka {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Tanka("data/tasks.txt").run();
    }
}
