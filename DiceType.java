package knightsoflegend;

public enum DiceType {
    D4(4), D6(6), D8(8), D10(10), D12(12), D20(20);
    private final int SIZE;
    
    // a private constructor for DiceType 
    private DiceType(int SIZE) {
        this.SIZE = SIZE;
    }

    // getter for SIZE
    public int getSIZE() {
        return SIZE;
    }

    // method to roll the dice and return a random number between 1 and SIZE
    public int roll() {
        return (int)(Math.random() * SIZE) + 1;
    }
    

}
