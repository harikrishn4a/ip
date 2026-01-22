import java.util.Scanner;

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
                "but i would never order a full pizza 🍕 for myself";
               
            System.out.println("Hello! I'm \n" + logo);
            System.out.println("____________________________________________________________");
            System.out.println(" Hello! I'm Tanka Jihari");
            System.out.println(" What can I do for you?");
            System.out.println("____________________________________________________________");
        
            Scanner scanner = new Scanner(System.in);
            Task[] tasks = new Task[100];
            int taskCount = 0;
            String userInput = ""; // Initialize userInput with an empty string
        
            while (true) {
                userInput = scanner.nextLine(); // Read user input from the console

                if (userInput.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                        break;
                }

                else if (userInput.equals("list")) {
                    System.out.println("____________________________________________________________");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + ". " + tasks[i].toString());
                    }
                    System.out.println("____________________________________________________________");
                }

                else if (userInput.startsWith("mark")) {
                    String[] parts = userInput.split(" ");
                    int taskNumber = Integer.parseInt(parts[1]) - 1; 
                    tasks[taskNumber].markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("  " + tasks[taskNumber].toString());
                    System.out.println("____________________________________________________________");
                }

                else if (userInput.startsWith("unmark")) {
                    String[] parts = userInput.split(" ");
                    int taskNumber = Integer.parseInt(parts[1]) - 1; 
                    tasks[taskNumber].markAsUndone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("  " + tasks[taskNumber].toString());
                    System.out.println("____________________________________________________________");
                }

                else if (userInput.startsWith("todo ")) {
                    String desc = userInput.substring(5).trim();
                    tasks[taskCount] = new Todo(desc);
                    System.out.println("____________________________________________________________");
                    System.out.println("  Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount].toString());
                    taskCount++;
                    System.out.println("  Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                    
                }

                else if (userInput.startsWith("deadline ")) {
                    String actual = userInput.substring(9).trim();
                    String[] parts = actual.split("/by", 2);
                    String desc = parts[0].trim();
                    String due = parts[1].trim();
                    tasks[taskCount] = new Deadline(desc, due);
                    System.out.println("____________________________________________________________");
                    System.out.println("  Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount].toString());
                    taskCount++;
                    System.out.println("  Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }

                else if (userInput.startsWith("event ")) {
                    String actual = userInput.substring(6).trim();
                    String[] parts = actual.split("/from", 2);
                    String desc = parts[0].trim();
                    String[] subParts = parts[1].split("/to", 2);
                    String start = subParts[0].trim();
                    String end = subParts[1].trim();
                    tasks[taskCount] = new Event(desc, start, end);
                    System.out.println("____________________________________________________________");
                    System.out.println("  Got it. I've added this task:");
                    System.out.println("  " + tasks[taskCount].toString());
                    taskCount++;
                    System.out.println("  Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                }

                //For all other inputs, added it to the task list
                else {
                    Task newTask = new Task(userInput);
                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println("____________________________________________________________");
                    System.out.println(" added: " + userInput); 
                    System.out.println("____________________________________________________________");
                }
            }
            scanner.close();
    }
}
