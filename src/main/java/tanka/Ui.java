package tanka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Handles all user interaction: reading commands and printing messages.
 */
public class Ui {
    private Scanner scanner;

    /** Creates a Ui that reads from standard input. */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        // Welcome message
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

        System.out.println("Hello! I'm \n" + logo);
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Tanka Jahari");
        System.out.println(" What can I do for you?");
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
        System.out.println("  " + message + " Starting with empty list.");
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
            System.out.println("  You have no tasks in your list.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString());
            }
        }
    }

    /**
     * Prints a numbered list of tasks from a TaskList, or a message if empty.
     *
     * @param tasks the TaskList to display
     */
    public void showTaskList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("  You have no tasks in your list.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString());
            }
        }
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
        System.out.println(" Here are the matching tasks in your list:");
        if (tasks.isEmpty()) {
            System.out.println("  No matching tasks.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString());
            }
        }
    }

    /** Prints the goodbye message. */
    public void showBye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    /**
     * Confirms that a task was marked as done and shows the task.
     *
     * @param task the task that was marked done
     */
    public void showMarkedDone(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("  " + task.toString());
    }

    /**
     * Confirms that a task was marked as not done and shows the task.
     *
     * @param task the task that was unmarked
     */
    public void showMarkedUndone(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("  " + task.toString());
    }

    /**
     * Confirms deletion of a task and shows the new total count.
     *
     * @param deletedTask    the task that was removed
     * @param remainingCount the number of tasks left in the list
     */
    public void showDeleted(Task deletedTask, int remainingCount) {
        System.out.println("  Noted. I've removed this task:");
        System.out.println("   " + deletedTask);
        System.out.println("  Now you have " + remainingCount + " tasks in the list.");
    }

    /**
     * Confirms that a task was added and shows the new total count.
     *
     * @param newTask   the task that was added
     * @param totalCount the total number of tasks after adding
     */
    public void showAdded(Task newTask, int totalCount) {
        System.out.println("  Got it. I've added this task:");
        System.out.println("  " + newTask);
        System.out.println("  Now you have " + totalCount + " tasks in the list.");
    }

    /** Closes the scanner and releases resources. */
    public void close() {
        scanner.close();
    }
}
