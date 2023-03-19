import java.util.*;

public class AI extends Player{

    private Random random = new Random();
    private int briscolaChosen;
    private CardSuit briscolaSuit;
    private int maxBid = 0;
    private final int MIN_POWER_TO_BID = 22;
    private final int MAX_CARDS_TO_HAVE = 7;
    private final int confidence = (int)(Math.random()*5 + 80);

    public AI(String name) {
        super(name);
        isAi = true;
    }

    public int getBriscolaChosen() {
        return this.briscolaChosen;
    }

    public int playACard(ArrayList<Card> table){
        return 0;
    }


    public int chooseMate(CardSuit briscola){
        TreeSet<Card> myBriscolaCards = new TreeSet<>();
        for(Card c : getHand()){
            if(c.getSuit().isBriscola){
                myBriscolaCards.add(c);
            }
        }
        for(CardValue cv : CardValue.values()){
            boolean found = false;
            for (Card c : myBriscolaCards) {
                if (c.getValue() == cv) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return cv.cardValue;
            }
        }
        return 0;
    }

    public void clearHand(){
        getHand().clear();
    }

    public void choseMonteCards(Deck deck){

        deck.addAllCard(getHand());
        clearHand();
        while(getHand().size() < MAX_CARDS_TO_HAVE){
            for(int i = 0; i < deck.cards.size(); i++){
                if(deck.cards.get(i).getSuit().isBriscola){
                    retrieveCard(deck.takeCard(i));
                    i--;
                }if (getHand().size() == MAX_CARDS_TO_HAVE){
                    break;
                }
            }
            for(int i = 0; i < deck.cards.size(); i++){
                if (getHand().size() == MAX_CARDS_TO_HAVE){
                    break;
                }
                 if(deck.cards.get(i).getValue().cardPoints == 0){
                    retrieveCard(deck.takeCard(i));
                } else if ( deck.cards.get(i).getValue().cardPoints == 2){
                    retrieveCard(deck.takeCard(i));
                } else if ( deck.cards.get(i).getValue().cardPoints == 3){
                    retrieveCard(deck.takeCard(i));
                } else if ( deck.cards.get(i).getValue().cardPoints == 4){
                    retrieveCard(deck.takeCard(i));
                }
            }
        }

    }

    public int makeABid(int currentBid){
        if(currentBid > maxBid){
            return 0;
        } else if (maxBid == 120) {
            return 120;
        } else {
            return currentBid + random.nextInt(1, 3);
        }
    }


    /**
     *
     * @return auction entry's first integer is the briscola chosen (1 for Denari, 2 For Spade, 3 for Coppe, 4 for Mazze), second one is the bid
     *
     *     */

    public void calculateAuctionChoice() {
        Map<Integer, Integer> auctionChoice = new HashMap<>();

        for (Card c : getHand()) {
            int cardSuitIndex = 0;
            switch (c.getSuit()) {
                case DENARI -> {
                    cardSuitIndex = 1;
                }
                case SPADE -> {
                    cardSuitIndex = 2;
                }
                case COPPE -> {
                    cardSuitIndex = 3;
                }
                case MAZZE -> {
                    cardSuitIndex = 4;
                }
            }
            if (auctionChoice.containsKey(cardSuitIndex)) {
                int currentPower = auctionChoice.get(cardSuitIndex) + c.getValue().cardPower;
                auctionChoice.put(cardSuitIndex, currentPower);
            } else {
                auctionChoice.put(cardSuitIndex, c.getValue().cardPower);
            }
        }

        int currentPoints = 0;
        for (Map.Entry<Integer, Integer> entry : auctionChoice.entrySet()) {
            if (entry.getValue() > MIN_POWER_TO_BID && entry.getValue() > currentPoints) {
                currentPoints = entry.getValue();
                briscolaChosen = entry.getKey();
            }
        }

        if(currentPoints < MIN_POWER_TO_BID){
            maxBid = 0;
        } else if(currentPoints < 25){
            maxBid = 85 + random.nextInt(11);
        } else if (currentPoints < 33) {
            maxBid = 100 + random.nextInt(11);
        } else if (currentPoints < 43) {
            maxBid = 105 + random.nextInt(11);
        } else {
            maxBid = 120;
        }
    }
}
