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
        String logo =
                "████████╗ █████╗ ███╗   ██╗██╗  ██╗ █████╗\n"
                + "╚══██╔══╝██╔══██╗████╗  ██║██║ ██╔╝██╔══██╗\n"
                + "   ██║   ███████║██╔██╗ ██║█████╔╝ ███████║\n"
                + "   ██║   ██╔══██║██║╚██╗██║██╔═██╗ ██╔══██║\n"
                + "   ██║   ██║  ██║██║ ╚████║██║  ██╗██║  ██║\n"
                + "   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═╝\n"
                + "\n"
                + "     J A H A R I\n"
                + "\n"
                + "but i would never order a WHOLE pizza 🍕 for myself";
        output.append("Hello! I'm \n").append(logo).append("\n");
        output.append("____________________________________________________________\n");
        output.append(" Hello! I'm Tanka Jahari\n");
        output.append(" What can I do for you?\n");
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
        output.append("  ").append(message).append(" Starting with empty list.\n");
    }

    @Override
    public void showTask(int number, Task task) {
        output.append(" ").append(number).append(". ").append(task.toString()).append("\n");
    }

    @Override
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            output.append("  You have no tasks in your list.\n");
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> output.append(" ").append(i + 1).append(". ")
                            .append(tasks.get(i).toString()).append("\n"));
        }
    }

    @Override
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            output.append("  You have no tasks in your list.\n");
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> output.append(" ").append(i + 1).append(". ")
                            .append(tasks.get(i).toString()).append("\n"));
        }
    }

    @Override
    public void showMatchingTasks(ArrayList<Task> tasks) {
        output.append(" Here are the matching tasks in your list:\n");
        if (tasks.isEmpty()) {
            output.append("  No matching tasks.\n");
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> output.append(" ").append(i + 1).append(". ")
                            .append(tasks.get(i).toString()).append("\n"));
        }
    }

    @Override
    public void showBye() {
        output.append(" Bye. Hope to see you again soon!\n");
    }

    @Override
    public void showMarkedDone(Task task) {
        output.append(" Nice! I've marked this task as done:\n");
        output.append("  ").append(task.toString()).append("\n");
    }

    @Override
    public void showMarkedUndone(Task task) {
        output.append(" OK, I've marked this task as not done yet:\n");
        output.append("  ").append(task.toString()).append("\n");
    }

    @Override
    public void showDeleted(Task deletedTask, int remainingCount) {
        output.append("  Noted. I've removed this task:\n");
        output.append("   ").append(deletedTask).append("\n");
        output.append("  Now you have ").append(remainingCount).append(" tasks in the list.\n");
    }

    @Override
    public void showAdded(Task newTask, int totalCount) {
        output.append("  Got it. I've added this task:\n");
        output.append("  ").append(newTask).append("\n");
        output.append("  Now you have ").append(totalCount).append(" tasks in the list.\n");
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
