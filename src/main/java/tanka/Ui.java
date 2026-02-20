package tanka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Handles all user interaction: reading commands and printing messages.
 * Personality strings are centralized here so CLI and GUI stay in sync (Chill and Reliable Task Buddy).
 */
public class Ui {

    /** Shown when user input is not understood (used by Parser). */
    public static final String MESSAGE_PARSE_ERROR =
            "Hmm, that command didn't quite make sense. Like pineapple on pizza—some things don't fit. "
            + "Could you rephrase?";

    /** Logo and tagline shown in the welcome message. */
    protected static final String WELCOME_LOGO =
            "████████╗ █████╗ ███╗   ██╗██╗  ██╗ █████╗\n"
            + "╚══██╔══╝██╔══██╗████╗  ██║██║ ██╔╝██╔══██╗\n"
            + "   ██║   ███████║██╔██╗ ██║█████╔╝ ███████║\n"
            + "   ██║   ██╔══██║██║╚██╗██║██╔═██╗ ██╔══██║\n"
            + "   ██║   ██║  ██║██║ ╚████║██║  ██╗██║  ██║\n"
            + "   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═╝\n"
            + "\n"
            + "     J A H A R I\n"
            + "\n"
            + "but i would never order a WHOLE pizza \uD83C\uDF55 for myself";

    // ---- Personality phrases (Chill and Reliable Task Buddy) ----
    protected static final String WELCOME_MESSAGE =
            "Hey there! I'm Tanka. Let's get these tasks squared away. "
            + "You focus on what matters—maybe I'll grab a slice later.";
    protected static final String MESSAGE_BYE = "Catch you later. Hope to see you again soon!";
    protected static final String MESSAGE_LOADING_ERROR_SUFFIX = " Starting with empty list.";
    protected static final String MESSAGE_LIST_EMPTY = "  You have no tasks in your list.";
    protected static final String MESSAGE_FIND_HEADER = " Here are the matching tasks in your list:";
    protected static final String MESSAGE_FIND_EMPTY = "  No matching tasks.";
    protected static final String MESSAGE_REMINDERS_HEADER = " Here are your upcoming deadlines:";
    protected static final String MESSAGE_REMINDERS_EMPTY = "  No upcoming deadlines.";
    protected static final String MESSAGE_MARKED_DONE = "Boom! Task finished. You nailed it!";
    protected static final String MESSAGE_MARKED_UNDONE = " OK, I've marked this task as not done yet:";
    protected static final String MESSAGE_DELETED_HEADER = "  Noted. I've removed this task:";
    protected static final String MESSAGE_ADDED_HEADER =
            "Gotcha. Another one for the list. No biggie, we'll get through it.";
    protected static final String MESSAGE_REMAINING_COUNT = "  Now you have %d tasks in the list.";

    private Scanner scanner;

    /** Creates a Ui that reads from standard input. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("Hello! I'm \n" + WELCOME_LOGO);
        System.out.println("____________________________________________________________");
        System.out.println(" " + WELCOME_MESSAGE);
        System.out.println("____________________________________________________________");
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return the trimmed line entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /** Prints a horizontal separator line. */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Shows an error message to the user.
     *
     * @param errorMessage the message to display
     */
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Shows a loading error and indicates the app will start with an empty list.
     *
     * @param message the error message (e.g. from Storage)
     */
    public void showLoadingError(String message) {
        System.out.println("  " + message + MESSAGE_LOADING_ERROR_SUFFIX);
    }

    /**
     * Prints a single task with its 1-based index.
     *
     * @param number the display number (1-based)
     * @param task   the task to show
     */
    public void showTask(int number, Task task) {
        System.out.println(" " + number + ". " + task.toString());
    }

    /**
     * Prints a numbered list of tasks (varargs), or a message if none given.
     *
     * @param tasks zero or more tasks to display
     */
    public void showTaskList(Task... tasks) {
        showTaskList(new ArrayList<>(Arrays.asList(tasks)));
    }

    /**
     * Prints a numbered list of tasks, or a message if the list is empty.
     *
     * @param tasks the list of tasks to display
     */
    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(MESSAGE_LIST_EMPTY);
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString()));
        }
    }

    /**
     * Prints a numbered list of tasks from a TaskList, or a message if empty.
     *
     * @param tasks the TaskList to display
     */
    public void showTaskList(TaskList tasks) {
        showTaskList(tasks.getList());
    }

    /**
     * Shows the "find" results: header then numbered list of matching tasks (varargs).
     *
     * @param tasks zero or more tasks that matched the search keyword
     */
    public void showMatchingTasks(Task... tasks) {
        showMatchingTasks(new ArrayList<>(Arrays.asList(tasks)));
    }

    /**
     * Shows the "find" results: header then numbered list of matching tasks, or a no-match message.
     *
     * @param tasks list of tasks that matched the search keyword
     */
    public void showMatchingTasks(ArrayList<Task> tasks) {
        System.out.println(MESSAGE_FIND_HEADER);
        if (tasks.isEmpty()) {
            System.out.println(MESSAGE_FIND_EMPTY);
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString()));
        }
    }

    /**
     * Shows upcoming deadline reminders: header then numbered list, or a no-upcoming message.
     *
     * @param tasks list of incomplete deadlines due within the reminder window
     */
    public void showReminders(ArrayList<Task> tasks) {
        System.out.println(MESSAGE_REMINDERS_HEADER);
        if (tasks.isEmpty()) {
            System.out.println(MESSAGE_REMINDERS_EMPTY);
        } else {
            IntStream.range(0, tasks.size())
                    .forEach(i -> System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString()));
        }
    }

    /** Prints the goodbye message. */
    public void showBye() {
        System.out.println(" " + MESSAGE_BYE);
    }

    /**
     * Confirms that a task was marked as done and shows the task.
     *
     * @param task the task that was marked done
     */
    public void showMarkedDone(Task task) {
        System.out.println(" " + MESSAGE_MARKED_DONE);
        System.out.println("  " + task.toString());
    }

    /**
     * Confirms that a task was marked as not done and shows the task.
     *
     * @param task the task that was unmarked
     */
    public void showMarkedUndone(Task task) {
        System.out.println(MESSAGE_MARKED_UNDONE);
        System.out.println("  " + task.toString());
    }

    /**
     * Confirms deletion of a task and shows the new total count.
     *
     * @param deletedTask    the task that was removed
     * @param remainingCount the number of tasks left in the list
     */
    public void showDeleted(Task deletedTask, int remainingCount) {
        System.out.println(MESSAGE_DELETED_HEADER);
        System.out.println("   " + deletedTask);
        System.out.println(String.format(MESSAGE_REMAINING_COUNT, remainingCount));
    }

    /**
     * Confirms that a task was added and shows the new total count.
     *
     * @param newTask   the task that was added
     * @param totalCount the total number of tasks after adding
     */
    public void showAdded(Task newTask, int totalCount) {
        System.out.println(" " + MESSAGE_ADDED_HEADER);
        System.out.println("  " + newTask);
        System.out.println(String.format(MESSAGE_REMAINING_COUNT, totalCount));
    }

    /** Closes the scanner and releases resources. */
    public void close() {
        scanner.close();
    }
}
