package tanka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test double for Ui that records show* calls instead of printing.
 * Do not call readCommand(); the scanner is still attached to System.in.
 */
public class StubUi extends Ui {

    private final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void clear() {
        messages.clear();
    }

    @Override
    public void showWelcome() {
        messages.add("showWelcome");
    }

    @Override
    public void showLine() {
        messages.add("showLine");
    }

    @Override
    public void showError(String errorMessage) {
        messages.add("showError:" + errorMessage);
    }

    @Override
    public void showLoadingError(String message) {
        messages.add("showLoadingError:" + message);
    }

    @Override
    public void showTask(int number, Task task) {
        messages.add("showTask:" + number + ":" + task.toString());
    }

    @Override
    public void showTaskList(Task... tasks) {
        showTaskList(new ArrayList<>(Arrays.asList(tasks)));
    }

    @Override
    public void showTaskList(ArrayList<Task> tasks) {
        messages.add("showTaskList:" + tasks.size());
    }

    @Override
    public void showTaskList(TaskList taskList) {
        messages.add("showTaskList:" + taskList.size());
    }

    @Override
    public void showMatchingTasks(Task... tasks) {
        showMatchingTasks(new ArrayList<>(Arrays.asList(tasks)));
    }

    @Override
    public void showMatchingTasks(ArrayList<Task> tasks) {
        messages.add("showMatchingTasks:" + tasks.size());
    }

    @Override
    public void showReminders(ArrayList<Task> tasks) {
        messages.add("showReminders:" + tasks.size());
    }

    @Override
    public void showBye() {
        messages.add("showBye");
    }

    @Override
    public void showMarkedDone(Task task) {
        messages.add("showMarkedDone:" + task.toString());
    }

    @Override
    public void showMarkedUndone(Task task) {
        messages.add("showMarkedUndone:" + task.toString());
    }

    @Override
    public void showDeleted(Task deletedTask, int remainingCount) {
        messages.add("showDeleted:" + deletedTask.toString() + ":" + remainingCount);
    }

    @Override
    public void showAdded(Task newTask, int totalCount) {
        messages.add("showAdded:" + newTask.toString() + ":" + totalCount);
    }
}
