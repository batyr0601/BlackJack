import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    // SETTING UP THE STATIC VARIABLES (THE ONES THAT DO NOT CHANGE)
    static Random random = new Random();
    static Scanner input = new Scanner(System.in);
    static int[] values = {1,2,3,4,5,6,7,8,9,10,11,12,13};
    static String[] suits = {"Hearts", "Spades", "Clubs", "Diamonds"};
    static Card[] deck = new Card[52];

    static List<Card> usersDeck = new ArrayList<Card>();
    static List<Card> dealersDeck = new ArrayList<Card>();

    static int userTotal = 0;
    static int dealerTotal = 0;

    public static void main(String[] args) {
        // Thread for keep checking if the total is over 21
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (userTotal>21){
                    end();
                    exec.shutdown();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        decksSetUp();
        game();
        choice();
    }

    // SETTING UP THE DECKS
    public static void decksSetUp(){
        int i = 0;
        for(String suit : suits){
            for (int value : values) {
                Card newCard = new Card(suit, value);
                deck[i] = newCard;
                i++;
            }
        }
        int counter1 = 0;
        int counter2 = 0;
        int index;
        while (counter1<2){
            boolean cardFound = false;
            index = random.nextInt(52);
            do {
                if (deck[index].getAvailability() == true){
                    usersDeck.add(deck[index]);
                    cardFound = true;
                }
            } while (!cardFound);
            deck[index].setAvailability(false);
            counter1 += 1;
        }
        while (counter2<2){
            boolean cardFound = false;
            index = random.nextInt(52);
            do {
                if (deck[index].getAvailability() == true){
                    dealersDeck.add(deck[index]);
                    cardFound = true;
                }
            } while (!cardFound);
            deck[index].setAvailability(false);
            counter2 += 1;
        }
    }

    // THE GAME ITSELF
    public static void game(){
        System.out.println("Welcome to Blackjack");
        System.out.println("-------------------------------");
        System.out.println("You will be playing the dealer");
        System.out.println("-------------------------------");
        System.out.println("Stick = stay with what you have");
        System.out.println("Twist = get a new card");
        System.out.println("-------------------------------");
        System.out.println("YOUR DECK IS:");
        System.out.println("Card #1");
        usersDeck.get(0).display();
        System.out.println("Card #2");
        usersDeck.get(1).display();
        // CODE FOR EASIER DEVELOPMENT
//        System.out.println("DEALER'S DECK IS:");
//        System.out.println("Card #1");
//        dealersDeck.get(0).display();
//        System.out.println("Card #2");
//        dealersDeck.get(1).display();
//        System.out.println("---------------------------");
        // OUTPUT THE TOTAL
        total();
    }

    // CHOICE OF "STICK OR TWIST"
    public static void choice(){
        boolean choiceMade = false;
        while (!choiceMade) {
            System.out.println("Stick or Twist?");
            String choice = input.next();
            if (choice.equalsIgnoreCase("Stick")) {
                stick();
            } else if (choice.equalsIgnoreCase("Twist")) {
                twist();
                if (userTotal > 21){
                    break;
                }
            } else {
                System.out.println("An error has occurred");
            }
            System.out.println("Are you finished?(Yes/No)");
            String choice2 = input.next();
            if (choice2.equalsIgnoreCase("Yes")){
                choiceMade = true;
                total();
                end();
            } else if (choice2.equalsIgnoreCase("No")){
                ;
            } else {
                System.out.println("An error has occurred");
            }
        }
    }

    // CHOICE TO "STICK"
    public static void stick(){
        System.out.println("You have chosen to stick with what you have");
        total();
    }

    // CHOICE TO "TWIST"
    public static void twist(){
        boolean cardFound = false;
        int index = random.nextInt(52);
        do {
            if (deck[index].getAvailability() == true){
                usersDeck.add(deck[index]);
                cardFound = true;
            }
        } while (!cardFound);
        deck[index].setAvailability(false);
        System.out.println("Your new card: ");
        deck[index].display();
        total();
    }

    // CALCULATING THE TOTAL
    public static void total(){
        userTotal = 0;
        dealerTotal = 0;
        for (Card card: usersDeck){
            userTotal += card.getValue();
        }
        for (Card card: dealersDeck){
            dealerTotal += card.getValue();
        }
        System.out.println("---------------------------");
        System.out.println("");
        System.out.println("YOUR TOTAL IS " + userTotal);
//        System.out.println("DEALER TOTAL IS " + dealerTotal);
        System.out.println("");
        System.out.println("---------------------------");
    }

    // FINAL METHOD TO SUM UP THE GAME
    public static void end() {
        System.out.println("---------------------------");
        System.out.println("");
        System.out.println("YOUR TOTAL IS " + userTotal);
        System.out.println("DEALER TOTAL IS " + dealerTotal);
        System.out.println("");
        System.out.println("---------------------------");
        if (userTotal <= 21 && dealerTotal <= 21) {
            if (userTotal > dealerTotal) {
                System.out.println("YOU WON");
            } else if (userTotal < dealerTotal) {
                System.out.println("YOU LOST");
            } else if (userTotal == dealerTotal) {
                System.out.println("DRAW");
            } else {
                System.out.println("ERROR HAS OCCURRED");
            }
        } else if (dealerTotal > 21) {
            if (userTotal > 21) {
                System.out.println("You both went over 21 so it is a DRAW");
            } else if (userTotal <= 21) {
                System.out.println("The dealer went over 21, YOU WON");
            }
        }
        else if (userTotal > 21){
            System.out.println("YOU LOST SINCE YOU WENT OVER 21");
        }
    }
}
