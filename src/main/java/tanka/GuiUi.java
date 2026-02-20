package tanka;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * UI implementation that captures all output to a StringBuilder for use in the GUI.
 * Used by {@link Tanka#getResponse(String)} so the response string can be displayed in the chat.
 */
public class GuiUi extends Ui {
    private final StringBuilder output = new StringBuilder();

    @Override
    public void showWelcome() {
        output.append("Hello! I'm \n").append(Ui.WELCOME_LOGO).append("\n");
        output.append("____________________________________________________________\n");
        output.append(" ").append(Ui.WELCOME_MESSAGE).append("\n");
        output.append("____________________________________________________________\n");
    }

    @Override
    public void showLine() {
        output.append("____________________________________________________________\n");
    }

    @Override
    public void showError(String errorMessage) {
        output.append(errorMessage).append("\n");
    }

    @Override
    public void showLoadingError(String message) {
        output.append("  ").append(message).append(Ui.MESSAGE_LOADING_ERROR_SUFFIX).append("\n");
    }

    @Override
    public void showTask(int number, Task task) {
        output.append(" ").append(number).append(". ").append(task.toString()).append("\n");
    }

    @Override
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            output.append(Ui.MESSAGE_LIST_EMPTY).append("\n");
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> output.append(" ").append(i + 1).append(". ")
                            .append(tasks.get(i).toString()).append("\n"));
        }
    }

    @Override
    public void showTaskList(TaskList tasks) {
        showTaskList(tasks.getList());
    }

    @Override
    public void showMatchingTasks(ArrayList<Task> tasks) {
        output.append(Ui.MESSAGE_FIND_HEADER).append("\n");
        if (tasks.isEmpty()) {
            output.append(Ui.MESSAGE_FIND_EMPTY).append("\n");
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> output.append(" ").append(i + 1).append(". ")
                            .append(tasks.get(i).toString()).append("\n"));
        }
    }

    @Override
    public void showReminders(ArrayList<Task> tasks) {
        output.append(Ui.MESSAGE_REMINDERS_HEADER).append("\n");
        if (tasks.isEmpty()) {
            output.append(Ui.MESSAGE_REMINDERS_EMPTY).append("\n");
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> output.append(" ").append(i + 1).append(". ")
                            .append(tasks.get(i).toString()).append("\n"));
        }
    }

    @Override
    public void showBye() {
        output.append(" ").append(Ui.MESSAGE_BYE).append("\n");
    }

    @Override
    public void showMarkedDone(Task task) {
        output.append(" ").append(Ui.MESSAGE_MARKED_DONE).append("\n");
        output.append("  ").append(task.toString()).append("\n");
    }

    @Override
    public void showMarkedUndone(Task task) {
        output.append(Ui.MESSAGE_MARKED_UNDONE).append("\n");
        output.append("  ").append(task.toString()).append("\n");
    }

    @Override
    public void showDeleted(Task deletedTask, int remainingCount) {
        output.append(Ui.MESSAGE_DELETED_HEADER).append("\n");
        output.append("   ").append(deletedTask).append("\n");
        output.append(String.format(Ui.MESSAGE_REMAINING_COUNT, remainingCount)).append("\n");
    }

    @Override
    public void showAdded(Task newTask, int totalCount) {
        output.append(" ").append(Ui.MESSAGE_ADDED_HEADER).append("\n");
        output.append("  ").append(newTask).append("\n");
        output.append(String.format(Ui.MESSAGE_REMAINING_COUNT, totalCount)).append("\n");
    }

    /**
     * Returns the captured output and clears the buffer for the next command.
     *
     * @return the accumulated output string (trimmed)
     */
    public String getResponseAndClear() {
        String result = output.toString().trim();
        output.setLength(0);
        return result;
    }
}
