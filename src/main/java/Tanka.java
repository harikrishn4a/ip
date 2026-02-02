import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Tanka {
    public static void main(String[] args) {
        String logo = 
                "████████╗ █████╗ ███╗   ██╗██╗  ██╗ █████╗\n" +
                "╚══██╔══╝██╔══██╗████╗  ██║██║ ██╔╝██╔══██╗\n" +
                "   ██║   ███████║██╔██╗ ██║█████╔╝ ███████║\n" +
                "   ██║   ██╔══██║██║╚██╗██║██╔═██╗ ██╔══██║\n" +
                "   ██║   ██║  ██║██║ ╚████║██║  ██╗██║  ██║\n" +
                "   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═══╝╚═╝  ╚═╝╚═╝  ╚═╝\n" +
                "\n" +
                "     J A H A R I\n" +
                "\n" +
                "but i would never order a WHOLE pizza 🍕 for myself";
               
            System.out.println("Hello! I'm \n" + logo);
            System.out.println("____________________________________________________________");
            System.out.println(" Hello! I'm Tanka Jahari");
            System.out.println(" What can I do for you?");
            System.out.println("____________________________________________________________");
        
            Scanner scanner = new Scanner(System.in);

            // Loads tasks from file on startup
            Storage storage = new Storage("data" + File.separator + "tanka.txt");
            ArrayList<Task> tasks;
            try {
                tasks = storage.loadTasks();
            } catch (TankaException e) {
                System.out.println("  " + e.getMessage() + " Starting with empty list.");
                tasks = new ArrayList<>();
            }
            String userInput = ""; // Initialize userInput with an empty string
        
            while (scanner.hasNextLine()) {
                userInput = scanner.nextLine(); // Read user input from the console

                if (userInput.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                        break;
                }

                else if (userInput.equals("list")) {
                    System.out.println("____________________________________________________________");
                    if (tasks.isEmpty()) {
                        System.out.println("  You have no tasks in your list.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(" " + (i + 1) + ". " + tasks.get(i).toString());
                        }
                    }
                    System.out.println("____________________________________________________________");
                }

                else if (userInput.startsWith("mark")) {
                    String[] parts = userInput.split(" ");
                    int taskNumber = Integer.parseInt(parts[1]) - 1; 
                    tasks.get(taskNumber).markAsDone();
                    try {
                        storage.saveTasks(tasks);
                    } catch (TankaException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("  Failed to save: " + e.getMessage());
                        System.out.println("____________________________________________________________");
                    }
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("  " + tasks.get(taskNumber).toString());
                    System.out.println("____________________________________________________________");
                }

                else if (userInput.startsWith("unmark")) {
                    String[] parts = userInput.split(" ");
                    int taskNumber = Integer.parseInt(parts[1]) - 1; 
                    tasks.get(taskNumber).markAsUndone();
                    try {
                        storage.saveTasks(tasks);
                    } catch (TankaException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("  Failed to save: " + e.getMessage());
                        System.out.println("____________________________________________________________");
                    }
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks.get(taskNumber).toString());
                    System.out.println("____________________________________________________________");
                }

                else if (userInput.startsWith("delete")) {
                    try {
                        //Get the integer index
                        String[] components = userInput.split(" ");
                        int indexOfDeletion = Integer.parseInt(components[1]) - 1;

                        //Check bound validity
                        if (indexOfDeletion < 0 || indexOfDeletion >= tasks.size()) {
                            throw new TankaException("This task number does not exist!");
                        }

                        Task deletedTask = tasks.remove(indexOfDeletion);
                        System.out.println("____________________________________________________________");
                        System.out.println("  Noted. I've removed this task:");
                        System.out.println("   " + deletedTask);
                        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                        try {
                            storage.saveTasks(tasks);
                        } catch (TankaException e) {
                            System.out.println("____________________________________________________________");
                            System.out.println("  Failed to save: " + e.getMessage());
                            System.out.println("____________________________________________________________");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("  Please provide a valid task number.");
                        System.out.println("____________________________________________________________");
                    } catch (TankaException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("  " + e.getMessage());
                        System.out.println("____________________________________________________________");

                    }
                }

                //For all other inputs, added it to the task list
                else {
                    try {
                        Task newTask = Parser.parseTask(userInput);
                        tasks.add(newTask);
                        try {
                            storage.saveTasks(tasks);
                        } catch (TankaException e) {
                            System.out.println("____________________________________________________________");
                            System.out.println("  Failed to save: " + e.getMessage());
                            System.out.println("____________________________________________________________");
                        }
                        System.out.println("____________________________________________________________");
                        System.out.println("  Got it. I've added this task:");
                        System.out.println("  " + newTask);
                        System.out.println("  Now you have " + tasks.size() + " tasks in the list.");
                        System.out.println("____________________________________________________________");
                    } catch(TankaException e) {
                        System.out.println("____________________________________________________________");
                        System.out.println("  " + e.getMessage());
                        System.out.println("____________________________________________________________");
                    }
                }
            }
            scanner.close();
    }
}
