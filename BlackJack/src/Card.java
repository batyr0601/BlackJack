public class Card { // CLASS FOR A CARD
    private String suit;    // INSTANCE VARIABLE
    private int value;
    private boolean availability = true;
                // PARAMETERS
    public Card(String suit, int value) { // CONSTRUCTOR
        this.suit = suit;
        this.value = value;
    }
    // THE SIGNATURE OF A METHOD
    public void display() {
        String strValue = switch (this.value) {
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            case 1 -> "Ace";
            default -> Integer.toString(this.value);
        };

        System.out.println(strValue + " of " + suit);
    }
    public String getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
