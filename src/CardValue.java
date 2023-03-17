public enum CardValue {
    ASSO(10, 11, 1, "Asso", "A"),
    DUE(1, 0, 2),
    TRE(9, 10, 3, "Tre","3"),
    QUATTRO(2, 0, 4),
    CINQUE(3, 0, 5),
    SEI(4, 0, 6),
    SETTE(5, 0, 7),
    DONNA(6, 2, 8, "Donna","D"),
    CAVALLO(7, 3, 9, "Cavallo", "C"),
    RE(8, 4, 10, "Re", "R");

    final String cardName;
    final int cardValue;
    final int cardPower;
    final int cardPoints;
    final String cardSymbol;
    CardValue( int cardPower, int cardPoints, int cardValue, String cardName, String cardSymbol){
        this.cardName = cardName;
        this.cardValue = cardValue;
        this.cardPower = cardPower;
        this.cardPoints = cardPoints;
        this.cardSymbol = cardSymbol;
    }
    CardValue(int cardPower, int cardPoints, int cardValue){
        this.cardValue = cardValue;
        this.cardPower = cardPower;
        this.cardName = String.valueOf(cardPower);
        this.cardPoints = cardPoints;
        this.cardSymbol = String.valueOf(cardPower);
    }
}
