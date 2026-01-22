public class Deadline extends Task{
    private String dueBy;
    public Deadline(String description, String dueBy) {
        super(description);
        this.dueBy = dueBy;
    }
    
    public String getDueBy() {
        return dueBy;
    }
    
    @Override
    public String toString() {
        return " [D]" + super.toString() + " (by: " + dueBy + ")";
    }
    
    
}
