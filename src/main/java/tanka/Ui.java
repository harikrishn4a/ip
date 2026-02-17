package tanka;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;

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

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void showLoadingError(String message) {
        System.out.println("  " + message + " Starting with empty list.");
    }

    public void showTask(int number, Task task) {
        System.out.println(" " + number + ". " + task.toString());
    }

    public void showTaskList(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("  You have no tasks in your list.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString());
            }
        }
    }

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

    public void showBye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }

    public void showMarkedDone(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("  " + task.toString());
    }

    public void showMarkedUndone(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("  " + task.toString());
    }

    public void showDeleted(Task deletedTask, int remainingCount) {
        System.out.println("  Noted. I've removed this task:");
        System.out.println("   " + deletedTask);
        System.out.println("  Now you have " + remainingCount + " tasks in the list.");
    }

    public void showAdded(Task newTask, int totalCount) {
        System.out.println("  Got it. I've added this task:");
        System.out.println("  " + newTask);
        System.out.println("  Now you have " + totalCount + " tasks in the list.");
    }

    public void close() {
        scanner.close();
    }
}
