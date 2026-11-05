public class ShapeDriver {
    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle(3),
            new Rectangle(4, 5),
            new Triangle(6, 4)
        };

        double total = 0;
        double largest = 0;

        for (Shape s : shapes) {
            double a = s.area();
            System.out.println("Area: " + a);
            total += a;
            if (a > largest) largest = a;
        }

        System.out.println("Total area: " + total);
        System.out.println("Largest area: " + largest);
    }
}
