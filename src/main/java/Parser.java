public class Parser {
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
            throw new TankaException("A deadline task must have a /by <time>!");
        }

        String[] parts = rest.split("/by",2);
        String desc = parts[0].trim();
        String dueBy = parts[1].trim();
        if (desc.isEmpty()) {
            throw new TankaException("The description of a deadline task cannot be empty!");
        }
        if (dueBy.isEmpty()) {
            throw new TankaException("The duedate of a deadline task cannot be empty!");
        }
        return new Deadline(desc, dueBy);
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
    
}
