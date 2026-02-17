package tanka;

import java.util.ArrayList;

/**
 * Command to find tasks whose description contains the given keyword (case-insensitive).
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Creates a FindCommand that will list tasks matching the keyword.
     *
     * @param keyword the search keyword (case-insensitive)
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> all = tasks.getList();
        ArrayList<Task> matching = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Task task : all) {
            if (task.getStatusDescription().toLowerCase().contains(lowerKeyword)) {
                matching.add(task);
            }
        }
        ui.showMatchingTasks(matching);
    }
}
