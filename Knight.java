


public class Knight extends MOB{
    private Fortune activeFortune = null;
    protected final int id; // id for the knight, used to identify 
    protected int xp = 0; // experience points for the knight, used to level up
    

    // constructor for the Knight class
    public Knight(int id, String name, int maxHP, int armor, int hitModifier, DiceType damageDie, int xp) {
        super(name, armor, hitModifier, maxHP, damageDie);
        this.id = id;
        this.xp = xp;
    }

    public void addXP(int xp) {
        this.xp += xp;
    }

    public Fortune getActiveFortune() {
        return activeFortune;
    }

    // getters for the attributes of the Knight class
    @Override
    public int getArmor() {
        if (activeFortune != null) {
            return armor + activeFortune.getArmor();
        }
        return armor;
    }

    @Override
    public DiceType getDamageDie() {
        if (activeFortune != null && activeFortune.getDamageDie() != null) {
            return activeFortune.getDamageDie();
        }
        return damageDie;
    }


    @Override
    public int getHitModifier() {
        if (activeFortune != null) {
            return hitModifier + activeFortune.getHitModifier();
        }
        return hitModifier;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public Integer getId() {
        return id;
    }

    @Override
    public int getMaxHP() {
        if (activeFortune != null) {
            return maxHP + activeFortune.getMaxHP();
        }
        return maxHP;
    }

    public int getXP() {
        return xp;
    }

    public void setActiveFortune(Fortune fortune) {
        this.activeFortune = fortune;
    }

    public String toCSV() {
        return getName() + "," + getMaxHP() + "," + getArmor() + ","
           + getHitModifier() + "," + getDamageDie() + "," + xp;

    }

    @Override
    public String toString() {
        return "+============================+\n" +
          String.format("| %-27s|%n", getName()) +
          String.format("| id: %-23d|%n", getId()) +
          "|                            |\n" +
          String.format("| Health: %-6d  XP: %-7d|%n", getHP(), getXP())  +
          String.format("|  Power: %-6s  Armor: %-4d|%n", getDamageDie().toString(), getArmor()) +
          "|                            |\n" +
          "+============================+";
    }
    
}
