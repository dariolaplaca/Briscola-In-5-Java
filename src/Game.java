import java.util.*;

public class Game {
    private final Deck deck;
    ArrayList<Card> table;
    private final ArrayList<Player> players;
    private static final int NUMBER_OF_PLAYERS = 5;
    private static final int NUMBER_OF_CARDS_IN_FIRST_HAND = 7;
    private final Scanner input = new Scanner(System.in);
    private CardSuit briscola;
    int scoreToGet = 60;

    Player player_one;
    Player player_two;
    Player player_three;
    Player player_four;
    Player player_five;

    public Game(List<String> playersName) {
        players = new ArrayList<>();
        deck = new Deck();
        table = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            try {
                players.add(new Player(playersName.get(i)));
            } catch (IndexOutOfBoundsException e) {
                players.add(new Player("Player " + (i + 1)));
            }
        }
    }

    public Game() {
        players = new ArrayList<>();
        deck = new Deck();
        table = new ArrayList<>();
        player_one = new Player("Player 1");
        player_two = new Player("Player 2");
        player_three = new Player("Player 3");
        player_four = new Player("Player 4");
        player_five = new Player("Player 5");
        players.add(player_one);
        players.add(player_two);
        players.add(player_three);
        players.add(player_four);
        players.add(player_five);
    }

    public Deck getDeck() {
        return deck;
    }


    public void runGame() {
        printMain();
        setupDeck();
        setupPlayers();
        scoreToGet = auction();
        chooseBriscola();
        changeCardsWithMonte();
        chooseMate();
        printTeams();
        for(int i = 0; i < NUMBER_OF_CARDS_IN_FIRST_HAND; i++){
            setTurnOrder();
            nextTurn();
        }
        getMonteCards();
        endGame(countPoints());
    }

    public void setupPlayers() {
        for (Player p : players) {
            for (int i = 0; i < NUMBER_OF_CARDS_IN_FIRST_HAND; i++) {
                Card c = deck.dealCard();
                c.setPlayerId(p.getId());
                p.retrieveCard(c);
            }
        }
        for (Player p : players) {
            System.out.println(p.getName());
            p.printHand();
            System.out.println();
        }
    }

    private void setupDeck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                deck.addCard(new Card(value, suit));
            }
        }
        deck.shuffle();
    }


    //TODO Fix Auction bug where it asks for one more input after all the players passes except one
    private int auction() {
        HashMap<Player, Integer> playersAuction = new HashMap<>();
        int playersOut = 0;
        for(Player p : players){
            playersAuction.put(p, -1);
        }
        boolean isPlayerChosen = false;
        int finalBid = 60;
        Player chosenPlayer = players.get(0);
        while (!isPlayerChosen) {
            for (Map.Entry<Player, Integer> entry : playersAuction.entrySet()) {
                if (entry.getValue() != 0) {
                    System.out.println("Current bid is " + finalBid + "\n" + entry.getKey().getName() + " make a bid!\n(Set less to pass)");
                    entry.setValue(input.nextInt());
                    if (entry.getValue() > finalBid) {
                        chosenPlayer = entry.getKey();
                        if (entry.getValue() >= 120) {
                            entry.setValue(120);
                            isPlayerChosen = true;
                        }
                        finalBid = entry.getValue();
                    } else {
                        entry.setValue(0);
                        playersOut++;
                    }
                }
                if ((playersOut > 4 || isPlayerChosen ) && finalBid > 60) {
                    isPlayerChosen = true;
                    break;
                }
            }
            if (finalBid == 60) {
                System.out.println("No player make a bid, restart the game");
                System.exit(0);
            }
        }
        chosenPlayer.setMate(true);
        chosenPlayer.setRoundStarter(true);
        System.out.println("The first player is " + chosenPlayer.getName());
        return finalBid;
    }

    public void chooseBriscola() {
        System.out.println("Your cards are:");
        for (Card c : players.get(0).getHand()) {
            c.drawCard();
        }
        System.out.println("Scegli la briscola:\n1. Denari\n2. Spade\n3. Coppe\n4. Mazze");
        switch (input.nextInt()) {
            case 1 -> {
                briscola = CardSuit.DENARI;
                CardSuit.DENARI.setBriscola();
            }
            case 2 -> {
                briscola = CardSuit.SPADE;
                CardSuit.SPADE.setBriscola();
            }
            case 3 -> {
                briscola = CardSuit.COPPE;
                CardSuit.COPPE.setBriscola();
            }
            case 4 -> {
                briscola = CardSuit.MAZZE;
                CardSuit.MAZZE.setBriscola();
            }
        }
        for(CardSuit c : CardSuit.values()){
            c.setPower();
        }
        for(Player p : players){
            for(Card c : p.getHand()){
                c.getSuit().setPower();
                c.refreshCardPower();
            }
        }
    }

    //TODO make more readable all the prints and make try catch for input
    private void changeCardsWithMonte(){
        Player firstPlayer = null;
        boolean hasFinished = false;
        for(Player p : players){
            if(p.isRoundStarter()){
                firstPlayer = p;
            }
        }
        while(!hasFinished){
            System.out.println("1. Add Card to hand\n" +
                    "2. Remove Card from hand\n" +
                    "9. Finish\n" +
                    "Cards in Hand: " + firstPlayer.getHand() +
                    "\nCards in Monte: " + getDeck());

            switch (input.nextInt()) {
                case 1 -> {
                    System.out.println("Select a card to keep");
                    int cardToKeep = input.nextInt();
                    firstPlayer.retrieveCard(deck.takeCard(cardToKeep));
                }
                case 2 -> {
                    System.out.println("Select a card to remove");
                    deck.addCard(firstPlayer.removeCard(input.nextInt()));

                }
                case 9 -> {
                    if (firstPlayer.getHand().size() == 7) {
                        hasFinished = true;
                    } else {
                        System.out.println("You must have 7 cards in your hand");
                    }
                }
            }
        }
    }

    private void chooseMate() {
        System.out.println("Scegli il tuo compagno:\n");
        int chosenCard;
        for(CardValue value : CardValue.values()){
            System.out.println(value + " di " + briscola);
        }
        System.out.println("Selezionare solo il valore della carta:\n");
        chosenCard = input.nextInt();
        for(Player p : players){
            for(Card c : p.getHand()){
                if (c.getValue().cardValue == chosenCard && c.getSuit() == briscola){
                    p.setMate(true);
                }
            }
        }
    }

    //TODO make more readable all the prints and make try catch for input
    private void nextTurn(){
        for(Player p : players){
            System.out.println(p.getName() + " choose a card to play\t\t\tThe Briscola is: " + briscola);
            p.printHand();
            if(!table.isEmpty()){
                System.out.println("The cards on the table are: " + table);
            }
            table.add(p.removeCard(input.nextInt()));
        }
        turnWinner();
    }

    private void turnWinner(){
        Card winningCard = table.get(0);
        for(Card c : table){
            if((c.getSuit().equals(winningCard.getSuit()) && c.getValue().cardPower > winningCard.getValue().cardPower) || (c.getSuit().isBriscola && !winningCard.getSuit().isBriscola)){
                winningCard = c;
            }
        }
        for(Player p : players){
            if(winningCard.getPlayerId() == p.getId()){
                System.out.println("The played cards are:");
                System.out.println(table);
                System.out.println("The winner of the round is " + p.getName() + " with the " + winningCard);
                p.takeCards(table);
                p.setRoundStarter(true);
                table.clear();
            }
        }
    }

    public void setTurnOrder() {
        int index = 0;
        for (Player p : players) {
            if (p.isRoundStarter()) {
                index = players.indexOf(p);
                p.setRoundStarter(false);
                break;
            }
        }
        List<Player> startingList = new ArrayList<>(players.subList(index, players.size()));
        List<Player> finishingList = new ArrayList<>(players.subList(0, index));
        players.clear();
        players.addAll(startingList);
        players.addAll(finishingList);
    }

    private void printTeams(){
        for(Player p : players){
            System.out.println(p.getName() + " " + p.getHand() + " " + p.isMate());
        }
    }

    private void getMonteCards(){
        for(Player p : players){
            if(p.isRoundStarter()){
                p.addMonte(deck.cards);
            }
        }
    }

    private int countPoints(){
        int points = 0;
        for(Player p : players){
            if(p.isMate()){
                points += p.countPoint();
            }
        }
        return points;
    }

    private void endGame(int points){
        System.out.println("Game Ended!");
        input.nextLine();
        System.out.println("The mates were...");
        if(points > scoreToGet){
            for(Player p : players){
                if(p.isMate()){
                    System.out.println(p);
                }
            }
        }
        if(points > scoreToGet){
            System.out.println("They won with " + points + " points!");
        } else {
            System.out.println("They lose, they miss " + (scoreToGet - points) + " points");
        }
    }

    private static void printMain() {
        System.out.println("""

                 ____  ____  ___ ____   ____ ___  _        _   \s
                | __ )|  _ \\|_ _/ ___| / ___/ _ \\| |      / \\  \s
                |  _ \\| |_) || |\\___ \\| |  | | | | |     / _ \\ \s
                | |_) |  _ < | | ___) | |__| |_| | |___ / ___ \\\s
                |____/|_| \\_\\___|____/ \\____\\___/|_____/_/   \\_\\
                           ___ _   _                           \s
                          |_ _| \\ | |                          \s
                           | ||  \\| |                          \s
                           | || |\\  |                          \s
                          |___|_| \\_|                          \s
                     ____ ___ _   _  ___  _   _ _____          \s
                    / ___|_ _| \\ | |/ _ \\| | | | ____|         \s
                   | |    | ||  \\| | | | | | | |  _|           \s
                   | |___ | || |\\  | |_| | |_| | |___          \s
                    \\____|___|_| \\_|\\__\\_\\\\___/|_____|         \s
                                                               \s
                GOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOFY""");
    }
}
