public class Todo extends Task{
    public Todo(String decription) {
        super(decription);
    }

    @Override
    public String toString() {
        return " [T]" + super.toString();
    }  
}
