// MOB class implementing the attributes interface, represents a monster or enemy in the game
public class MOB implements Attributes  {
    protected int armor;
    protected int damage;
    protected DiceType damageDie;
    protected int hitModifier;
    protected int maxHP;
    private String name;

    // constructor for the MOB class
    public MOB(String name, int armor, int hitModifier, int maxHP, DiceType damageDie) {
        this.name = name;
        this.armor = armor;
        this.hitModifier = hitModifier;
        this.maxHP = maxHP;
        this.damageDie = damageDie;
        this.damage = 0;
    }

    // method adding damage to the MOB, used when the MOB takes damage in combat
    public void addDamage(int damage) {
        this.damage += damage;
    }

    // method to create a copy of the MOB, used to create instance of MOB for combat
    public MOB copy() {
        return new MOB(this.name, this.armor, this.hitModifier, this.maxHP, this.damageDie);
    }

    // getters for the attributes of the MOB class
    @Override
    public int getArmor() {
        return armor;
    }

    // method to get the current damage given to the MOB
    public int getDamage() {
        return damage;
    }

    @Override
    public DiceType getDamageDie() {
        return damageDie;
    }

    @Override
    public int getHitModifier() {
        return hitModifier;
    }

    public int getHP() {
        // getMaxHP() so a Knight's fortune HP bonus is included
        return getMaxHP() - damage;
    }

    @Override
    public int getMaxHP() {
        return maxHP;
    }

    public String getName() {
        return name;
    }

    // method to reset damage of MOB after being defeated
    public void resetDamage() {
        this.damage = 0;
    }

    // toString method for the MOB class, describes attributes of the MOB
    @Override
    public String toString() {
        return "+============================+\n" +
                String.format("| %-27s|%n", getName()) +
                "|                            |\n" +
                String.format("|         Health: %-10d |%n", getHP())  +
                String.format("|  Power: %-6s  Armor: %-4d|%n", getDamageDie().toString(), getArmor()) +
                "|                            |\n" +
                "+============================+";
    }

}
