

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
    public void clear() {
        int i;
        List<Knight> knights = data.getKnights();
        for (i=0; i < knights.size(); ++i) {
            if (knights.get(i).getActiveFortune() != null) {
                knights.get(i).setActiveFortune(null);
            }
        }
    }

    public void initialize() {
        GameData data = new CSVGameData("gamedata.csv", "saveData.csv");
        List<Knight> activeKnights = data.getActiveKnights();
        for (Knight kt : activeKnights) {
            if (kt.getActiveFortune() == null) {
                kt.setActiveFortune(data.getRandomFortune());

            }
        }
    }
    // helper method: optional 
    private int doBattle(List<MOB> attackers, List<MOB> defenders) {
         //FixMe 
                return 0;
    }
    // runCombat: optional for a challenge
    public void runCombat() {
        System.out.println ("Combat not implemented yet.");
        System.out.println ("non violence is healthy anyway");
    }
}
