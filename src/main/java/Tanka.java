import java.util.Scanner;
import java.util.ArrayList;

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
            String userInput = ""; // Initialize userInput with an empty string
            ArrayList<String> tasks = new ArrayList<>();
        
            while (true) {
                userInput = scanner.nextLine(); // Read user input from the console

                if (userInput.equals("bye")) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Bye. Hope to see you again soon!");
                    System.out.println("____________________________________________________________");
                        break;
                }

                if (userInput.equals("list")) {
                    System.out.println("____________________________________________________________");
                    int count = 1;
                    for (String task : tasks) {
                        System.out.println(" " + count + ". " + task);
                        count++;
                    }
                    System.out.println("____________________________________________________________");

                //For all other inputs, added it to the task list
                } else {
                    tasks.add(userInput);
                    System.out.println("____________________________________________________________");
                    System.out.println(" added: " + userInput); // Echo the user's input with proper spacing
                    System.out.println("____________________________________________________________");
                }
            }
            scanner.close();
    }
}
