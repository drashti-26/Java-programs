public abstract class Employee {
    protected String name, id;

    public Employee(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public abstract double monthlySalary();
}
