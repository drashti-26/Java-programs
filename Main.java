import java.util.Scanner;
import service.TaskService;
import model.Task;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        TaskService service = new TaskService();
        int choice;

        do {
            System.out.println("\nTASK MENU");
            System.out.println("1. Add task");
            System.out.println("2. View tasks");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.print("Enter task name: ");
                String name = input.nextLine();
                try {
                    service.addTask(name);
                    System.out.println("Task added.");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else if (choice == 2) {
                for (Task task : service.getTasks()) {
                    System.out.println(task);
                }
                System.out.println("Total tasks: " + service.getTaskCount());
            } else if (choice == 3) {
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid choice.");
            }
        } while (choice != 3);

        input.close();
    }
}
