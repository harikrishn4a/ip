package tanka;

public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TankaException;
    public boolean isExit() {
        return false;
    }
}
