import java.util.Comparator;

public class Card implements Comparable<Card>{
    private final CardValue value;
    private final CardSuit suit;
    private int cardPower;
    private int playerId;
    public Card(CardValue value, CardSuit suit){
        this.value = value;
        this.suit = suit;
        this.cardPower = this.getSuit().getPower() * (-this.getValue().cardPower) + this.getSuit().order;
    }

    public int getPlayerId() {
        return this.playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public CardValue getValue() {
        return this.value;
    }

    public CardSuit getSuit() {
        return this.suit;
    }

    public void refreshCardPower(){
        this.cardPower = this.getSuit().getPower() * this.getValue().cardPower + this.getSuit().order;
    }

    public int getCardPower(){
        return this.cardPower;
    }

    @Override
    public String toString() {
        return value + " di " + suit;
    }

    public String drawCard(){
        String card = ("""
                 ______
                |%s     |
                |      |
                |  %s   |
                |      |
                |_____%s|
                """);
        return String.format(card, this.suit.suitSymbol, this.value.cardSymbol, this.suit.suitSymbol);
    }

    @Override
    public int compareTo(Card o) {
        return o.cardPower - this.cardPower;
    }

    @Override
    public boolean equals(Object o) {
        if(o.getClass() != this.getClass()){
            return false;
        }
        Card c = (Card)o;
        return (getValue() + " " + getSuit()).equals(c.getValue() + " " + c.getSuit());
    }
}
