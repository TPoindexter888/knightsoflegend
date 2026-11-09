import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CSVGameData extends GameData {

    public CSVGameData(String gamedata, String saveData) {
        super();
        loadGameData(gamedata);
        loadSaveData(saveData);
    }

    // Loads knights from a save file. Each line has the format:
    // name,maxHP,armor,hitModifier,damageDie,xp
    // (the same format as knights.csv, so a saved game can be loaded back in)
    public void loadSaveData(String filename) {
        Scanner file = readFile(filename);
        if (file == null) return;

        int idCounter = 0;

        while (file.hasNextLine()) {
            Scanner line = new Scanner(file.nextLine());
            line.useDelimiter(",");

            if (!line.hasNext()) continue;

            try {
                // First token is the knight's name
                String name = line.next().trim();

                int maxHP = Integer.parseInt(line.next().trim());
                int armor = Integer.parseInt(line.next().trim());
                int hitMod = Integer.parseInt(line.next().trim());

                // Die token may be "-" or empty
                String dieToken = line.next().trim();
                DiceType die;
                if (dieToken.equals("-") || dieToken.isEmpty()) {
                    die = DiceType.D6;   // safe default
                } else {
                    die = DiceType.valueOf(dieToken);
                }

                int xp = Integer.parseInt(line.next().trim());

                // Auto-generate ID
                int id = ++idCounter;

                Knight kt = new Knight(id, name, maxHP, armor, hitMod, die, xp);
                knights.add(kt);

            } catch (Exception e) {
                System.out.println("Skipping malformed knight line.");
            }
        }
        file.close();
    }

    // loads game data and creates objects based on first token of each line, FORTUNE or MOB.
     public void loadGameData(String filename) {
        Scanner file = readFile(filename);
        if (file == null)
            return;

        while (file.hasNextLine()) {
            Scanner line = new Scanner(file.nextLine());
            line.useDelimiter(",");
            try {
                parseGameDataLine(line);
            } catch (Exception e) {
                System.out.println("Skipping malformed game data line.");
            }
        }
        file.close();
    }

    // Line formats in gamedata.csv:
    //   FORTUNE,name,hpBonus,armor,hitModifier,damageDie
    //   MOB,name,maxHP,armor,hitModifier,damageDie
    private void parseGameDataLine(Scanner line) {
        if (!line.hasNext()) return;

        String type = line.next().trim();

        switch (type) {

            case "FORTUNE": {
                String name = line.next().trim();
                int hpBonus = Integer.parseInt(line.next().trim());
                int armor = Integer.parseInt(line.next().trim());
                int hitMod = Integer.parseInt(line.next().trim());
                String dieToken = line.next().trim();
                if (dieToken.equals("-") || dieToken.isEmpty()) {
                    fortunes.add(new Fortune(name, armor, hitMod, hpBonus));
                } else {
                    DiceType die = DiceType.valueOf(dieToken);
                    fortunes.add(new Fortune(name, armor, hitMod, hpBonus, die));
                }
                break;
            }

            case "MOB": {
                String name = line.next().trim();
                int maxHP = Integer.parseInt(line.next().trim());
                int armor = Integer.parseInt(line.next().trim());
                int hitMod = Integer.parseInt(line.next().trim());
                String dieToken = line.next().trim();
                DiceType die;
                if (dieToken.equals("-") || dieToken.isEmpty()) {
                    die = DiceType.D6;
                }
                else {
                    die = DiceType.valueOf(dieToken);
                }

                monsters.add(new MOB(name, armor, hitMod, maxHP, die));
                break;
            }
        }
    }

    // Opens a file for reading, or returns null (with a message) if it can't be found.
    private Scanner readFile(String filename) {
        try {
            return new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file: " + filename);
            return null;
        }
    }

    // Saves every knight in the same format loadSaveData reads, one knight per line.
    @Override
    public void save(String filename) {
        try (PrintWriter out = new PrintWriter(filename)) {
            for (Knight kt : knights) {
                out.println(kt.toCSV());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to save file: " + filename);
        }
    }
}
