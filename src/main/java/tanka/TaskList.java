package tanka;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Wrapper around a list of {@link Task} items with indexed access and mutators.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing the given tasks (varargs).
     *
     * @param tasks initial tasks (zero or more)
     */
    public TaskList(Task... tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    /**
     * Creates a task list containing the given tasks from a list.
     *
     * @param tasks initial tasks (can be empty)
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /** Returns true if there are no tasks. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /** Returns the number of tasks. */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given 0-based index.
     *
     * @param index 0-based index
     * @return the task at that index
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Appends a task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given 0-based index.
     *
     * @param index 0-based index
     * @return the removed task
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the underlying list of tasks (for iteration or filtering).
     *
     * @return the list of tasks
     */
    public ArrayList<Task> getList() {
        return tasks;
    }
}
