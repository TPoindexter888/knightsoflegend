import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GameData {
    protected final List<Knight> activeKnights;
    protected final List<Fortune> fortunes;
    protected final List<Knight> knights;
    private static final int MAX_ACTIVE = 4;
    protected final List<MOB> monsters;
    private static final Random random = new Random(); // random number generator for grabbing random items for the structures

    public GameData() {
        this.activeKnights = new ArrayList<>();
        this.fortunes = new ArrayList<>();
        this.knights = new ArrayList<>();
        this.monsters = new ArrayList<>();
    }

    // Required for the implementing class to be able to save the file
    public abstract void save(String filename);

    public List<Knight> getKnights() {
        return knights;
    }

    public List<Knight> getActiveKnights() {
        return activeKnights;
    }

    protected Knight findKnight (String nameOrId, List<Knight> knights) {
        for (Knight knight : knights) {
            if (knight.getName().toLowerCase().contains(nameOrId.toLowerCase())) {
                return knight;
            }
            if (knight.getId().toString().equals(nameOrId)) {
                return knight;
            }
        }
        return null; // not found
    }

    public Knight getActive(String nameOrId) {
        return findKnight(nameOrId, activeKnights);
    }

    public Knight getKnight(String nameOrId) {
        return findKnight(nameOrId, knights);
    }

    public boolean setActive (Knight kt) {
        if (activeKnights.size() != MAX_ACTIVE && !activeKnights.contains(kt)) {
            activeKnights.add(kt);
            return true;
        }
        return false;
    }

    public void removeActive (Knight kt) {
        if (activeKnights.remove(kt)) {
            kt.resetDamage();
        }
    }

    public Fortune getRandomFortune() {
        int index = random.nextInt(fortunes.size());
        return fortunes.get(index);
    }
    // Returns one random monster per active knight. Monsters are copies, so templates are never damaged.
    public List<MOB> getRandomMonsters() {
        List<MOB> result = new ArrayList<>();
        for (int i = 0; i < activeKnights.size(); i++) {
            int index = random.nextInt(monsters.size());
            result.add(monsters.get(index).copy());
        }
        return result;
    }

    public List<MOB> getRandomMonsters (int number) {
        MOB rndmMonster;
        List<MOB> selectedMonsters = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            int index = random.nextInt(monsters.size());
            rndmMonster = monsters.get(index).copy();
            selectedMonsters.add(rndmMonster);
            }
        return selectedMonsters;
    }

}
