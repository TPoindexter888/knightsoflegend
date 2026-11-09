public enum DiceType {
    D4(4), D6(6), D8(8), D10(10), D12(12), D20(20);
    private final int size;

    // a private constructor for DiceType
    private DiceType(int size) {
        this.size = size;
    }

    // getter for size
    public int getSize() {
        return size;
    }

    // method to roll the dice and return a random number between 1 and size
    public int roll() {
        return (int)(Math.random() * size) + 1;
    }

}
