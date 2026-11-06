import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveLoadObjects {
    static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        int rollNumber;
        String name;
        transient String password;

        Student(int rollNumber, String name, String password) {
            this.rollNumber = rollNumber;
            this.name = name;
            this.password = password;
        }

        public String toString() {
            return "Roll No: " + rollNumber + ", Name: " + name +
                    ", Password: " + password;
        }
    }

    public static void main(String[] args) {
        Student[] students = {
                new Student(1, "Riya", "riya123"),
                new Student(2, "Aman", "aman456")
        };

        try {
            ObjectOutputStream output = new ObjectOutputStream(
                    new FileOutputStream("students.dat"));
            output.writeObject(students);
            output.close();

            ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream("students.dat"));
            Student[] loadedStudents = (Student[]) input.readObject();
            input.close();

            System.out.println("Students loaded from file:");
            for (Student student : loadedStudents) {
                System.out.println(student);
            }
            System.out.println("The transient password is not saved.");
        } catch (Exception e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
