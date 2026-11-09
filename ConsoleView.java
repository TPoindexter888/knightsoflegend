import java.util.List;
import java.util.Scanner;

public class ConsoleView implements GameView {

    private final Scanner scan = new Scanner(System.in);

    @Override
    public boolean checkContinue() {
        System.out.println("Would you like to continue your quest (y/n)?");
        if (scan.nextLine().trim().equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }

    @Override
    public String displayMainMenu() {
        System.out.println("What would you like to do?");
        return scan.nextLine().trim();
    }

    @Override
    public void endGame() {
        System.out.println("Well done, brave knight! You successfully counted to three, the Holy Hand Grenade");
        System.out.println("was successful, and you've finished with only a flesh wound.");
    }

    @Override
    public void knightNotFound() {
        System.out.println("Knight not found!");
    }

    @Override
    public void listKnights(java.util.List<Knight> knights) {
        if (knights.isEmpty()) {
            System.out.println("No knights to list.");
            return;
        }
        for (Knight knight : knights) {
            System.out.println(knight.getId() + ": " + knight.getName());
        }
    }

   @Override
    public void printBattleText(List<MOB> monsters, List<Knight> activeKnights) {

        System.out.println("Our heroes come across the following monsters. Prepare for battle!");
        System.out.printf("%-27s %s%n", "Knights", "Foes");

        int knightCount = activeKnights.size();
        int monsterCount = monsters.size();

        int i = 0;

        while (i < knightCount || i < monsterCount) {

            String knightName = "";
            String monsterName = "";

            // If there is a knight at this index
            if (i < knightCount) {
                knightName = activeKnights.get(i).getName();
            }

            // If there is a monster at this index
            if (i < monsterCount) {
                monsterName = monsters.get(i).getName();
            }

            System.out.printf("%-27s %s%n", knightName, monsterName);

            i++;
        }

        System.out.println();
    }

    @Override
    public void printBattleText(MOB dead) {
        System.out.println(dead.getName() + " was defeated!");
    }

    @Override
    public void printDefeated() {
        System.out.println("All knights have been defeated!");
    }

    @Override
    public void printFortunes(List<Knight> activeKnights) {
        System.out.println("For this quest, our knights drew the following fortunes:");
        for (Knight knight : activeKnights) {
            Fortune f = knight.getActiveFortune();
            String damageAdj = (f.getDamageDie() == null) ? "-" : f.getDamageDie().toString();

            System.out.println(knight.getName() + " drew:");
            System.out.println(
                "+============================+\n" +
                String.format("| %-27s|%n", f.getName()) +
                fortuneRow("HP Bonus:", String.format("%+d", f.getMaxHP())) +
                fortuneRow("AC Bonus:", String.format("%+d", f.getArmor())) +
                fortuneRow("Hit Bonus:", String.format("%+d", f.getHitModifier())) +
                fortuneRow("Damage Adj:", damageAdj) +
                "+============================+"
            );
            System.out.println();
        }
    }

    // One line of a fortune card: label on the left, value right-aligned.
    private String fortuneRow(String label, String value) {
        return String.format("| %-12s%14s |%n", label, value);
    }

    @Override
    public void printHelp() {
        String message = """
Unsure what to do, here are some options:
    ls or list all  - listing the knights
    list active  - list the active knights only
    show name or id - show the knight details card
    set active name or id - set knight as active (note: only 4 knights can be active)
    remove active name or id - remove a knight from active status (heals knight)
    explore or adventure or quest - find random monsters to fight
    save filename - save the game to the file name (default: saveData.csv)
    exit or goodbye - to leave the game

Game rules: You can have four active knights. As long as they are active, they won't heal,
but they can gain XP by going on adventures.
When you make a knight inactive, they will heal. How many monsters can you defeat
before you have to heal?
""";

        System.out.println(message);
    }

    @Override
    public void setActiveFailed() {
        System.out.println("Unable to set active knight. Only four can be active at a time.");
    }

    @Override
    public void showKnight(Knight knight) {
        String knightDetails = knight.toString();
        System.out.println(knightDetails);
    }

    @Override
    public void splashScreen() {
        String message = """
Welcome to Knights of Legend!

Your quest begins here, brave knight.
Many adventures await you, but first, you must gather your companions.
The road will be perilous.
Let's see if you can change your stars and become a legend!
""";
        System.out.println(message);
    }
}
