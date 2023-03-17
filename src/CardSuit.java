public enum CardSuit {
    SPADE("Spade", -20),
    MAZZE("Mazze", -40),
    DENARI("Denari", -10),
    COPPE("Coppe", -30);
    final String name;
    final String suitSymbol;
    boolean isBriscola;
    int power;
    int order;

    CardSuit(String name, int order){
        this.name = name;
        this.order = order;
        this.suitSymbol = String.valueOf(name.charAt(0)).toUpperCase();
        this.power = -1;
        isBriscola = false;
    }

    public void setPower(){
        if(isBriscola){
            this.power = -5;
        } else {
            this.power = -1;
        }
    }

    public int getPower(){
        return this.power;
    }

    public void setBriscola() {
        isBriscola = true;
    }
    public boolean isBriscola() {
        return this.isBriscola;
    }
}
