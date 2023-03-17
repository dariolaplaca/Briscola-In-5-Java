import java.util.*;

public class Player{
    private String name;
    private int id;
    private static int idCounter;
    private boolean isRoundStarter;
    private boolean isMate;
    private TreeSet<Card> hand;
    private TreeSet<Card> cardsTaken;
    private int points;

    public Player(String name){
        this.name = name;
        this.points = 0;
        isRoundStarter = false;
        cardsTaken = new TreeSet<>();
        hand = new TreeSet<>();
        id = ++idCounter;
    }

    public int getId() {
        return this.id;
    }

    public boolean isRoundStarter() {
        return this.isRoundStarter;
    }

    public void setRoundStarter(boolean roundStarter) {
        isRoundStarter = roundStarter;
    }

    public int getPoints() {
        return this.points;
    }
    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<Card> getHand() {
        return this.hand;
    }

    public boolean isMate() {
        return this.isMate;
    }
    public void setMate(boolean mate) {
        isMate = mate;
    }


    public void retrieveCard(Card c){
        hand.add(c);
    }

    public void takeCards(TreeSet<Card> table){
        cardsTaken.addAll(table);
    }

    public void addPoint(int points){
        this.points += points;
    }

    public int countPoint(){
        int totalPoints = 0;
        for(Card c : cardsTaken){
            totalPoints += c.getValue().cardPoints;
        }
        return totalPoints;
    }


    public Card removeCard(int i){
        int index = 0;
        for(Card c : hand){
            if(index == i){
                Card cardToRemove = c;
                hand.remove(cardToRemove);
                return cardToRemove;
            }
            index++;
        }
        return null;
    }

    public void printHand(){
        for(Card c : hand){
            System.out.println(c);
            c.drawCard();
        }
    }
}
