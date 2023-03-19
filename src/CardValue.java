public enum CardValue{
    ASSO(10, 11, 1, "Asso", "A"),
    TRE(9, 10, 3, "Tre","3"),
    RE(8, 4, 10, "Re", "R"),
    CAVALLO(7, 3, 9, "Cavallo", "C"),
    DONNA(6, 2, 8, "Donna","D"),
    SETTE(5, 0, 7),
    SEI(4, 0, 6),
    CINQUE(3, 0, 5),
    QUATTRO(2, 0, 4),
    DUE(1, 0, 2);

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
