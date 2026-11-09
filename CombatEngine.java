import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombatEngine {
    private GameData data;
    private Random random;
    private GameView view;

    public CombatEngine(GameData data, GameView view) {
        this.data = data;
        this.view = view;
        this.random = new Random();
    }

    // Removes the fortunes from all knights once the quest is over.
    public void clear() {
        for (Knight kt : data.getKnights()) {
            kt.setActiveFortune(null);
        }
    }

    // Gives every active knight a random fortune for this quest and shows them.
    // Uses the engine's own GameData so the fortunes go to the knights that are
    // actually active in this game.
    public void initialize() {
        List<Knight> activeKnights = data.getActiveKnights();
        if (activeKnights.isEmpty()) {
            return;
        }
        for (Knight kt : activeKnights) {
            kt.setActiveFortune(data.getRandomFortune());
        }
        view.printFortunes(activeKnights);
    }

    // One round of fighting: every living attacker swings at a random living defender.
    // Attack hits if D20 + hit modifier beats the defender's armor, then deals
    // a roll of the attacker's damage die. Defeated defenders are removed from the list.
    // Returns the number of defenders still standing.
    private int doBattle(List<MOB> attackers, List<MOB> defenders) {
        for (MOB attacker : attackers) {
            if (defenders.isEmpty()) {
                break;
            }
            MOB target = defenders.get(random.nextInt(defenders.size()));

            int attackRoll = DiceType.D20.roll() + attacker.getHitModifier();
            if (attackRoll > target.getArmor()) {
                target.addDamage(attacker.getDamageDie().roll());

                if (target.getHP() <= 0) {
                    defenders.remove(target);
                    view.printBattleText(target);

                    if (attacker instanceof Knight) {
                        ((Knight) attacker).addXP(1);
                    }
                    if (target instanceof Knight) {
                        // knights never die, they go back to the pool (and heal)
                        data.removeActive((Knight) target);
                    }
                }
            }
        }
        return defenders.size();
    }

    // Runs the quest: random encounters until the player stops or all knights are defeated.
    public void runCombat() {
        if (data.getActiveKnights().isEmpty()) {
            view.listKnights(data.getActiveKnights()); // "No knights to list."
            return;
        }

        boolean questing = true;
        while (questing) {
            // the knights fighting this battle (a copy, so we can remove the defeated)
            List<MOB> knights = new ArrayList<>();
            for (Knight kt : new ArrayList<>(data.getActiveKnights())) {
                if (kt.getHP() > 0) {
                    knights.add(kt);
                } else {
                    data.removeActive(kt);
                }
            }
            if (knights.isEmpty()) {
                view.printDefeated();
                return;
            }

            // 1 to (number of active knights) monsters
            int count = random.nextInt(knights.size()) + 1;
            List<MOB> monsters = data.getRandomMonsters(count);
            view.printBattleText(monsters, data.getActiveKnights());

            // knights strike first, then the monsters strike back, until one side falls
            while (!knights.isEmpty() && !monsters.isEmpty()) {
                if (doBattle(knights, monsters) == 0) {
                    break;
                }
                doBattle(monsters, knights);
            }

            if (knights.isEmpty()) {
                view.printDefeated();
                questing = false;
            } else {
                questing = view.checkContinue();
            }
        }
    }
}
