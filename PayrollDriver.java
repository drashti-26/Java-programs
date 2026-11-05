public class PayrollDriver {
    public static void main(String[] args) {
        Employee[] employees = {
            new FullTime("Asha", "E01", 30000),
            new PartTime("Ravi", "E02", 40, 300),
            new Intern("Mira", "E03", 8000)
        };

        double total = 0;
        for (Employee e : employees) {
            double salary = e.monthlySalary();
            System.out.println(e.name + " (" + e.id + "): " + salary);
            if (e instanceof Intern) {
                System.out.println("Intern employee");
            }
            total += salary;
        }
        System.out.println("Total payroll: " + total);
    }
}
