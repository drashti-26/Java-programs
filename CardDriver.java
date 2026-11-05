public class CardDriver {
    public static void main(String[] args) {
        Card[] cards = {
            new Card("Ace", "Spades"),
            new Card("King", "Hearts"),
            new Card("Ace", "Spades"),
            new Card("Queen", "Clubs")
        };

        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < i; j++) {
                if (cards[i].equals(cards[j])) {
                    System.out.println("Duplicate found: " + cards[i]);
                    return;
                }
            }
        }
        System.out.println("No duplicates found");
    }
}
