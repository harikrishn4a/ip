package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ParserTest {

    // ---- parse(String) ----

    @Test
    public void parse_bye_returnsExitCommand() throws TankaException {
        Command c = Parser.parse("bye");
        assertInstanceOf(ExitCommand.class, c);
        assert (c.isExit());
    }

    @Test
    public void parse_list_returnsListCommand() throws TankaException {
        Command c = Parser.parse("list");
        assertInstanceOf(ListCommand.class, c);
    }

    @Test
    public void parse_markOne_returnsMarkCommand() throws TankaException {
        Command c = Parser.parse("mark 1");
        assertInstanceOf(MarkCommand.class, c);
    }

    @Test
    public void parse_unmarkOne_returnsUnmarkCommand() throws TankaException {
        Command c = Parser.parse("unmark 1");
        assertInstanceOf(UnmarkCommand.class, c);
    }

    @Test
    public void parse_deleteOne_returnsDeleteCommand() throws TankaException {
        Command c = Parser.parse("delete 1");
        assertInstanceOf(DeleteCommand.class, c);
    }

    @Test
    public void parse_todoWithDesc_returnsAddCommand() throws TankaException {
        Command c = Parser.parse("todo read book");
        assertInstanceOf(AddCommand.class, c);
    }

    @Test
    public void parse_findKeyword_returnsFindCommand() throws TankaException {
        Command c = Parser.parse("find book");
        assertInstanceOf(FindCommand.class, c);
    }

    @Test
    public void parse_remind_returnsRemindCommandWithDefaultDays() throws TankaException {
        Command c = Parser.parse("remind");
        assertInstanceOf(RemindCommand.class, c);
        assertEquals(7, ((RemindCommand) c).getDays());
    }

    @Test
    public void parse_remindWithNumber_returnsRemindCommandWithThatManyDays() throws TankaException {
        Command c = Parser.parse("remind 3");
        assertInstanceOf(RemindCommand.class, c);
        assertEquals(3, ((RemindCommand) c).getDays());
    }

    @Test
    public void parse_remindWithNegative_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parse("remind -1"));
        assertEquals("Please provide a positive number of days for remind.", e.getMessage());
    }

    @Test
    public void parse_remindWithZero_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parse("remind 0"));
    }

    @Test
    public void parse_remindWithNonNumeric_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parse("remind abc"));
        assertEquals("Please provide a positive number of days for remind.", e.getMessage());
    }

    @Test
    public void parse_trimmedInput_acceptsWhitespace() throws TankaException {
        Command c = Parser.parse("  list  ");
        assertInstanceOf(ListCommand.class, c);
    }

    @Test
    public void parse_unknownCommand_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parse("unknown"));
        assertEquals(Ui.MESSAGE_PARSE_ERROR, e.getMessage());
    }

    @Test
    public void parse_markWithNoNumber_throwsTankaException() {
        // "mark x" triggers mark branch but index is non-numeric
        TankaException e = assertThrows(TankaException.class, () -> Parser.parse("mark x"));
        assertEquals("Please provide a valid task number for mark.", e.getMessage());
    }

    @Test
    public void parse_markWithNonNumeric_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parse("mark abc"));
    }

    @Test
    public void parse_markZero_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parse("mark 0"));
    }

    @Test
    public void parse_markNegative_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parse("mark -1"));
    }

    @Test
    public void parse_unmarkWithNoNumber_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parse("unmark "));
    }

    @Test
    public void parse_deleteWithNoNumber_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parse("delete "));
    }

    // ---- parseTask(String) ----

    @Test
    public void parseTask_validTodo_returnsTodo() throws TankaException {
        Task t = Parser.parseTask("todo read book");
        assertInstanceOf(Todo.class, t);
        assertEquals("read book", t.getStatusDescription());
    }

    @Test
    public void parseTask_emptyTodo_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseTask("todo"));
        assertEquals("The description of a todo task cannot be empty!", e.getMessage());
    }

    @Test
    public void parseTask_todoOnlySpaces_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parseTask("todo   "));
    }

    @Test
    public void parseTask_validDeadline_returnsDeadline() throws TankaException {
        Task t = Parser.parseTask("deadline submit /by 2025-02-10");
        assertInstanceOf(Deadline.class, t);
        assertEquals("submit", t.getStatusDescription());
    }

    @Test
    public void parseTask_deadlineWithoutBy_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseTask("deadline submit no slash by"));
        assertEquals("A deadline task must have a /by <time>!", e.getMessage());
    }

    @Test
    public void parseTask_deadlineInvalidDate_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseTask("deadline submit /by not-a-date"));
        assertEquals("Invalid date. Use yyyy-mm-dd", e.getMessage());
    }

    @Test
    public void parseTask_validEvent_returnsEvent() throws TankaException {
        Task t = Parser.parseTask("event meeting /from 2pm /to 3pm");
        assertInstanceOf(Event.class, t);
        assertEquals("meeting", t.getStatusDescription());
    }

    @Test
    public void parseTask_eventWithoutFrom_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parseTask("event meeting /to 3pm"));
    }

    @Test
    public void parseTask_eventWithoutTo_throwsTankaException() {
        assertThrows(TankaException.class, () -> Parser.parseTask("event meeting /from 2pm"));
    }

    @Test
    public void parseTask_unknownType_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseTask("other x"));
        assertEquals(Ui.MESSAGE_PARSE_ERROR, e.getMessage());
    }

    // ---- parseFromFile(String) ----

    @Test
    public void parseFromFile_todoNotDone_returnsTodo() throws TankaException {
        Task t = Parser.parseFromFile("T | 0 | buy milk");
        assertInstanceOf(Todo.class, t);
        assertEquals("buy milk", t.getStatusDescription());
        assertEquals(" ", t.getStatusIcon());
    }

    @Test
    public void parseFromFile_todoDone_returnsMarkedTodo() throws TankaException {
        Task t = Parser.parseFromFile("T | 1 | buy milk");
        assertInstanceOf(Todo.class, t);
        assertEquals("X", t.getStatusIcon());
    }

    @Test
    public void parseFromFile_deadline_returnsDeadline() throws TankaException {
        Task t = Parser.parseFromFile("D | 0 | submit | 2025-03-01");
        assertInstanceOf(Deadline.class, t);
        assertEquals("submit", t.getStatusDescription());
    }

    @Test
    public void parseFromFile_event_returnsEvent() throws TankaException {
        Task t = Parser.parseFromFile("E | 0 | meeting | 2pm | 3pm");
        assertInstanceOf(Event.class, t);
        assertEquals("meeting", t.getStatusDescription());
    }

    @Test
    public void parseFromFile_tooFewParts_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseFromFile("T | 0"));
        assertEquals("Invalid format in data file.", e.getMessage());
    }

    @Test
    public void parseFromFile_undefinedType_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseFromFile("X | 0 | desc"));
        assertEquals("Undefined Task type declared in file.", e.getMessage());
    }

    @Test
    public void parseFromFile_deadlineTooFewParts_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseFromFile("D | 0 | desc"));
        assertEquals("Invalid Deadline format in data file.", e.getMessage());
    }

    @Test
    public void parseFromFile_eventTooFewParts_throwsTankaException() {
        TankaException e = assertThrows(TankaException.class, () -> Parser.parseFromFile("E | 0 | desc | start"));
        assertEquals("Invalid Event format in data file.", e.getMessage());
    }
}
