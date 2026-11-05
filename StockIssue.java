import java.util.*;

class OutOfStockException extends Exception {
    private int shortfall;

    public OutOfStockException(int shortfall) {
        super("Not enough stock. Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    public int getShortfall() {
        return shortfall;
    }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}

class Warehouse {
    private int stock;

    public Warehouse(int stock) {
        this.stock = stock;
    }

    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero");
        }
        if (qty > stock) {
            throw new OutOfStockException(qty - stock);
        }
        stock -= qty;
        System.out.println("Issued " + qty + " units of " + item);
        System.out.println("Remaining stock: " + stock);
    }
}

public class StockIssue {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse(10);
        int[] requests = {4, 8, 0, 2};

        for (int qty : requests) {
            try {
                warehouse.issue("Notebook", qty);
            } catch (OutOfStockException e) {
                System.out.println(e.getMessage());
            } catch (InvalidQuantityException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
