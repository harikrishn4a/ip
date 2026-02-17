package tanka;

import java.util.ArrayList;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static Command parse(String fullCommand) throws TankaException {
        String trimmed = fullCommand.trim();

        if (trimmed.equals("bye")) {
            return new ExitCommand();
        } else if (trimmed.equals("list")) {
            return new ListCommand();
        } else if (trimmed.startsWith("mark ")) {
            int index = parseIndex(trimmed.substring(5), "mark");
            return new MarkCommand(index);
        } else if (trimmed.startsWith("unmark ")) {
            int index = parseIndex(trimmed.substring(7), "unmark");
            return new UnmarkCommand(index);
        } else if (trimmed.startsWith("delete ")) {
            int index = parseIndex(trimmed.substring(7), "delete");
            return new DeleteCommand(index);
        } else if (trimmed.startsWith("todo") || trimmed.startsWith("deadline") || trimmed.startsWith("event")) {
            return new AddCommand(trimmed);
        } else {
            throw new TankaException("Sorry I don't understand what you mean!");
        }
    }

    private static int parseIndex(String str, String commandName) throws TankaException {
        if (str.isEmpty()) {
            throw new TankaException("Please provide a valid task number.");
        }
        try {
            int oneBased = Integer.parseInt(str);
            if (oneBased < 1) {
                throw new TankaException("Please provide a valid task number.");
            }
            return oneBased - 1;
        } catch (NumberFormatException e) {
            throw new TankaException("Please provide a valid task number.");
        }
    }
    public static Task parseTask(String userInput) throws TankaException {
        if (userInput.startsWith("todo")) {
            return parseTodo(userInput);
        } else if (userInput.startsWith("deadline")) {
            return parseDeadline(userInput);
        } else if (userInput.startsWith("event")) {
            return parseEvent(userInput);
        } 
        throw new TankaException("Sorry! I don't understand what you mean!");
    }
    
    private static Task parseTodo(String userInput) throws TankaException {
        if (userInput.length() <= 4) { 
        throw new TankaException("The description of a todo task cannot be empty!");
        }
        String desc = userInput.substring(5).trim();
        if (desc.isEmpty()) {
            throw new TankaException("The description of a todo task cannot be empty!");
        }
        return new Todo(desc);
    }

    private static Task parseDeadline(String userInput) throws TankaException {
        if (userInput.length() <= 9) { // "todo" or shorter
        throw new TankaException("The description of a deadline task cannot be empty!");
        }

        String rest = userInput.substring(9).trim();
        if (!rest.contains("/by")) {
            throw new TankaException("A deadline task must have aa d /by <time>!");
        }

        String[] parts = rest.split("/by",2);
        String desc = parts[0].trim();
        String dueBy = parts[1].trim();

        if (desc.isEmpty()) {
            throw new TankaException("The description of a deadline task cannot be empty!");
        }

        try {
            LocalDate dueDate = LocalDate.parse(dueBy.trim());
            return new Deadline(desc, dueDate);
        } catch (DateTimeParseException e) {
            throw new TankaException("Invalid date. Use yyyy-mm-dd");
        }
    }

    private static Task parseEvent(String userInput) throws TankaException {
        if (userInput.length() <= 6) { // "todo" or shorter
        throw new TankaException("The description of an event task cannot be empty!");
        }

        String rest = userInput.substring(6).trim();
    
        if (!rest.contains("/from") || !rest.contains("/to")) {
            throw new TankaException("An event task must have /from <start> and /to <end>!");
        }
    
        String[] parts = rest.split("/from", 2);
        if (parts.length < 2) {
            throw new TankaException("An event task must specify a start time.");
        }
    
        String desc = parts[0].trim();
    
        String[] subParts = parts[1].split("/to", 2);
        if (subParts.length < 2) {
            throw new TankaException("An event task must specify an end time.");
        }
    
        String start = subParts[0].trim();
        String end = subParts[1].trim();
    
        if (desc.isEmpty()) {
            throw new TankaException("The description of an event task cannot be empty!");
        }
        if (start.isEmpty()) {
            throw new TankaException("The start period of an event task cannot be empty!");
        }
        if (end.isEmpty()) {
            throw new TankaException("The end period of an event task cannot be empty!");
        }
    
        return new Event(desc, start, end);
    }

    /**
     * Parses a line from the storage file into a Task.
     * Format: T|0|desc  or  D|0|desc|dueBy  or  E|0|desc|start|end
     * @param line
     * @return
     * @throws TankaException
     */
    public static Task parseFromFile(String line) throws TankaException {
        // Split exactly on " | " and preserve empty fields
        String[] parts = line.split(" \\|", -1);
        if (parts.length < 3) {
            throw new TankaException("Invalid format in data file.");
        }
        // Store as variables
        String type = parts[0].trim();
        int isDone = Integer.parseInt(parts[1].trim());
        String description = parts[2].trim();
        
        Task task;
        if (type.equals("T")) {
            task = new Todo(description);
        } else if (type.equals("D")) {
            if (parts.length < 4) {
                throw new TankaException("Invalid Deadline format in data file.");
            }
            try {
                String dueBy = parts[3].trim();
                LocalDate dueDate = LocalDate.parse(dueBy);
                task = new Deadline(description, dueDate);
            } catch (DateTimeParseException e) {
                throw new TankaException("Invalid date format!");
            }
            
        } else if (type.equals("E")) {
            if (parts.length < 5) {
                throw new TankaException("Invalid Event format in data file.");
            }
            String start = parts[3].trim();
            String end = parts[4].trim();
            task = new Event(description, start, end);
        } else {
            throw new TankaException("Undefined Task type declared in file.");
        }
        if (isDone == 1) {
            task.markAsDone();
        }
        return task;
    }
}
