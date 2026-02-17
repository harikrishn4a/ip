package tanka;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input and file lines into {@link Command} or {@link Task} objects.
 */
public class Parser {

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String COMMAND_REMIND = "remind";
    private static final String PREFIX_MARK = "mark ";
    private static final String PREFIX_UNMARK = "unmark ";
    private static final String PREFIX_DELETE = "delete ";
    private static final String PREFIX_FIND = "find ";
    private static final String PREFIX_TODO = "todo";
    private static final String PREFIX_DEADLINE = "deadline";
    private static final String PREFIX_EVENT = "event";
    private static final String FILE_DELIMITER = " \\|";
    private static final String TYPE_TODO = "T";
    private static final String TYPE_DEADLINE = "D";
    private static final String TYPE_EVENT = "E";
    private static final int DONE_VALUE = 1;

    /**
     * Parses a full command string into the corresponding Command.
     *
     * @param fullCommand raw user input (e.g. "list", "mark 1", "todo read book")
     * @return the parsed Command
     * @throws TankaException if the command is unknown or invalid
     */
    public static Command parse(String fullCommand) throws TankaException {
        String trimmed = fullCommand.trim();

        if (trimmed.equals(COMMAND_BYE)) {
            return new ExitCommand();
        } else if (trimmed.equals(COMMAND_LIST)) {
            return new ListCommand();
        } else if (trimmed.startsWith(PREFIX_MARK)) {
            int index = parseIndex(trimmed.substring(PREFIX_MARK.length()), "mark");
            return new MarkCommand(index);
        } else if (trimmed.startsWith(PREFIX_UNMARK)) {
            int index = parseIndex(trimmed.substring(PREFIX_UNMARK.length()), "unmark");
            return new UnmarkCommand(index);
        } else if (trimmed.startsWith(PREFIX_DELETE)) {
            int index = parseIndex(trimmed.substring(PREFIX_DELETE.length()), "delete");
            return new DeleteCommand(index);
        } else if (trimmed.startsWith(PREFIX_FIND)) {
            String keyword = trimmed.substring(PREFIX_FIND.length()).trim();
            return new FindCommand(keyword);
        } else if (trimmed.equals(COMMAND_REMIND)) {
            return new RemindCommand(7);
        } else if (trimmed.startsWith(COMMAND_REMIND + " ")) {
            String rest = trimmed.substring(COMMAND_REMIND.length()).trim();
            int days = parseRemindDays(rest);
            return new RemindCommand(days);
        } else if (trimmed.startsWith(PREFIX_TODO) || trimmed.startsWith(PREFIX_DEADLINE)
                || trimmed.startsWith(PREFIX_EVENT)) {
            return new AddCommand(trimmed);
        } else {
            throw new TankaException("Sorry I don't understand what you mean!");
        }
    }

    private static int parseRemindDays(String str) throws TankaException {
        if (str.isEmpty()) {
            throw new TankaException("Please provide a positive number of days for remind.");
        }
        try {
            int days = Integer.parseInt(str.trim());
            if (days < 1) {
                throw new TankaException("Please provide a positive number of days for remind.");
            }
            return days;
        } catch (NumberFormatException e) {
            throw new TankaException("Please provide a positive number of days for remind.");
        }
    }

    private static int parseIndex(String str, String commandName) throws TankaException {
        if (str.isEmpty()) {
            throw new TankaException("Please provide a valid task number for " + commandName + ".");
        }
        try {
            int oneBased = Integer.parseInt(str);
            if (oneBased < 1) {
                throw new TankaException("Please provide a valid task number for " + commandName + ".");
            }
            int index = oneBased - 1;
            assert index >= 0 : "0-based index must be non-negative";
            return index;
        } catch (NumberFormatException e) {
            throw new TankaException("Please provide a valid task number for " + commandName + ".");
        }
    }

    /**
     * Parses a task-creation input (todo/deadline/event) into a Task.
     *
     * @param userInput raw input (e.g. "todo read", "deadline x /by 2025-01-01", "event y /from a /to b")
     * @return the parsed Task
     * @throws TankaException if the input is invalid or missing required parts
     */
    public static Task parseTask(String userInput) throws TankaException {
        if (userInput.startsWith(PREFIX_TODO)) {
            return parseTodo(userInput);
        } else if (userInput.startsWith(PREFIX_DEADLINE)) {
            return parseDeadline(userInput);
        } else if (userInput.startsWith(PREFIX_EVENT)) {
            return parseEvent(userInput);
        }
        throw new TankaException("Sorry! I don't understand what you mean!");
    }

    private static Task parseTodo(String userInput) throws TankaException {
        int prefixLen = PREFIX_TODO.length();
        if (userInput.length() <= prefixLen) {
            throw new TankaException("The description of a todo task cannot be empty!");
        }
        String desc = userInput.substring(prefixLen).trim();
        if (desc.isEmpty()) {
            throw new TankaException("The description of a todo task cannot be empty!");
        }
        return new Todo(desc);
    }

    private static Task parseDeadline(String userInput) throws TankaException {
        int prefixLen = PREFIX_DEADLINE.length();
        if (userInput.length() <= prefixLen) {
            throw new TankaException("The description of a deadline task cannot be empty!");
        }
        String rest = userInput.substring(prefixLen).trim();
        if (!rest.contains("/by")) {
            throw new TankaException("A deadline task must have a /by <time>!");
        }

        String[] parts = rest.split("/by", 2);
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
        int prefixLen = PREFIX_EVENT.length();
        if (userInput.length() <= prefixLen) {
            throw new TankaException("The description of an event task cannot be empty!");
        }
        String rest = userInput.substring(prefixLen).trim();

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
        String[] parts = line.split(FILE_DELIMITER, -1);
        if (parts.length < 3) {
            throw new TankaException("Invalid format in data file.");
        }
        String type = parts[0].trim();
        int isDone = Integer.parseInt(parts[1].trim());
        String description = parts[2].trim();

        Task task = buildTaskFromFileParts(type, parts, description);
        if (isDone == DONE_VALUE) {
            task.markAsDone();
        }
        assert task != null : "parsed task must not be null";
        return task;
    }

    private static Task buildTaskFromFileParts(String type, String[] parts, String description)
            throws TankaException {
        switch (type) {
        case TYPE_TODO:
            return new Todo(description);
        case TYPE_DEADLINE:
            return parseDeadlineFromFile(parts, description);
        case TYPE_EVENT:
            return parseEventFromFile(parts, description);
        default:
            throw new TankaException("Undefined Task type declared in file.");
        }
    }

    private static Task parseDeadlineFromFile(String[] parts, String description) throws TankaException {
        if (parts.length < 4) {
            throw new TankaException("Invalid Deadline format in data file.");
        }
        try {
            String dueBy = parts[3].trim();
            LocalDate dueDate = LocalDate.parse(dueBy);
            return new Deadline(description, dueDate);
        } catch (DateTimeParseException e) {
            throw new TankaException("Invalid date format!");
        }
    }

    private static Task parseEventFromFile(String[] parts, String description) throws TankaException {
        if (parts.length < 5) {
            throw new TankaException("Invalid Event format in data file.");
        }
        String start = parts[3].trim();
        String end = parts[4].trim();
        return new Event(description, start, end);
    }
}
