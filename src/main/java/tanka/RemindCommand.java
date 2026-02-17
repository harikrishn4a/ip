package tanka;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Command to show reminders for incomplete deadlines due within the next N days.
 */
public class RemindCommand extends Command {
    private final int days;

    /**
     * Creates a RemindCommand that shows deadlines due within the given number of days.
     *
     * @param days number of days to look ahead (must be positive)
     */
    public RemindCommand(int days) {
        assert days > 0 : "days must be positive";
        this.days = days;
    }

    /**
     * Returns the number of days used for the reminder window (for testing).
     *
     * @return the days parameter
     */
    public int getDays() {
        return days;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(days);
        ArrayList<Task> upcoming = new ArrayList<>();
        for (Task task : tasks.getList()) {
            if (!(task instanceof Deadline)) {
                continue;
            }
            if (task.isDone()) {
                continue;
            }
            LocalDate dueBy = ((Deadline) task).getDueBy();
            // today <= dueBy <= limit (inclusive)
            if (!dueBy.isBefore(today) && !dueBy.isAfter(limit)) {
                upcoming.add(task);
            }
        }
        ui.showReminders(upcoming);
    }
}
