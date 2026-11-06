public class GenericContainers {
    static class Box<T> {
        private T value;

        void set(T value) {
            this.value = value;
        }

        T get() {
            return value;
        }
    }

    static class Pair<A, B> {
        private A first;
        private B second;

        Pair(A first, B second) {
            this.first = first;
            this.second = second;
        }

        void display() {
            System.out.println("First: " + first);
            System.out.println("Second: " + second);
        }
    }

    static <T> T firstElement(T[] values) {
        return values[0];
    }

    public static void main(String[] args) {
        Box<String> nameBox = new Box<>();
        nameBox.set("Drashti");
        System.out.println("Box value: " + nameBox.get());

        Pair<Integer, String> student = new Pair<>(1, "Riya");
        student.display();

        Integer[] numbers = {10, 20, 30};
        String[] names = {"Aman", "Neha"};

        System.out.println("First number: " + firstElement(numbers));
        System.out.println("First name: " + firstElement(names));
    }
}
