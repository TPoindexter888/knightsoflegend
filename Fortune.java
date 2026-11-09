


// Fortune class imnplementing the attributes interface
public class Fortune implements Attributes {
    private final int armor;
    private final DiceType dtype; 
    private final int hitModifier;
    private final int hpBonus;
    private final String name; 

    // constructor for Fortune class with no damage die
    public Fortune (String name, int armor, int hitModifier, int hpBonus) {
        this.name = name;
        this.armor = armor;
        this.hitModifier = hitModifier;
        this.hpBonus = hpBonus;
        this.dtype = null;
    }
    
    // constructor for Fortune class with damage die
    public Fortune(String name, int armor, int hitModifier, int hpBonus, DiceType dtype) {
        this.name = name;
        this.armor = armor;
        this.dtype = dtype;
        this.hitModifier = hitModifier;
        this.hpBonus = hpBonus;
    }
    
    // getters for the attributes of the Fortune class
    @Override
    public int getArmor() {
        return armor;
    }

    @Override
    public DiceType getDamageDie() {
        return dtype;
    }

    @Override
    public int getHitModifier() {
        return hitModifier;
    }

    @Override
    public int getMaxHP() {
        return hpBonus;
    }

    public String getName() {
        return name;
    }

    // toString method for the Fortune class, describes attributes of the Fortune
    @Override
    public String toString() {
    return name + ": " + armor + " armor, " + hitModifier + " hit modifier, "
        + hpBonus + " HP bonus, damage die = " + dtype;
    }

}
