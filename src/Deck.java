import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    ArrayList<Card> cards;
    public Deck(){
        cards = new ArrayList<>();
    }

    public void addCard(Card c){
        cards.add(c);
    }

    public void shuffle(){
        Collections.shuffle(cards);
    }

    public Card dealCard(){
        try{
            return cards.remove(0);
        }catch (Exception e){
            System.out.println("No more cards in the deck!");
        }
        return null;
    }

    public Card takeCard(int i){
        Card cardToKeep = cards.get(i);
        cards.remove(i);
        return cardToKeep;
    }

    public void printDeck(){
        for (Card c : cards){
            System.out.println(c);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Card c : cards){
            sb.append(c);
            sb.append(", ");
        }
        return sb.toString();
    }
}
