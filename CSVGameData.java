

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

                int maxHP = line.nextInt();
                int armor = line.nextInt();
                int hitMod = line.nextInt();

                // Die token may be "-" or empty
                String dieToken = line.next().trim();
                DiceType die;
                if (dieToken.equals("-") || dieToken.isEmpty()) {
                    die = DiceType.D6;   // safe default
                } else {
                    die = DiceType.valueOf(dieToken);
                }

                int xp = line.nextInt();

                // Auto-generate ID
                int id = ++idCounter;

                Knight kt = new Knight(id, name, maxHP, armor, hitMod, die, xp);
                knights.add(kt);

            } catch (Exception e) {
                System.out.println("Skipping malformed knight line.");
            }
        }
    }


    // loads game data and creates objects based on first letter of each line, F for fortune and M for monster.
     public void loadGameData(String filename) {
        Scanner file = readFile(filename);
        if (file == null) 
            return;

        while (file.hasNextLine()) {
            Scanner line = new Scanner(file.nextLine());
            line.useDelimiter(",");
            parseGameDataLine(line);
        }
    }

    private void parseGameDataLine(Scanner line) {
        if (!line.hasNext()) return;

        String type = line.next().trim();

        switch (type) {

            case "FORTUNE": {
                String name = line.next().trim();
                int armor = line.nextInt();
                int hitMod = line.nextInt();
                int hpBonus = line.nextInt();
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
                int armor = line.nextInt();
                int hitMod = line.nextInt();
                int maxHP = line.nextInt();
                String dieToken = line.next().trim();
                DiceType die;
                if (dieToken.equals("-") || dieToken.isEmpty()) {
                    die = DiceType.D6; 
                }                   
                else {
                    die = DiceType.valueOf(dieToken);
                }

                monsters.add(new MOB(name, armor, hitMod, maxHP, die));
            }
        }
    }
   
    // optional method to isolate erros when reading files
    private Scanner readFile(String filename) {
        try {
            return new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file: " + filename);
            return null;
        }
    }
    
    // Implementation for saving game data to a CSV file
   @Override
    public void save(String filename) {
        try (PrintWriter out = new PrintWriter(filename)) {

            // Save all knights
            for (Knight kt : knights) {
                out.println("KNIGHT," + kt.toCSV());
            }

            // Save active knights
            for (Knight kt : activeKnights) {
                out.println("ACTIVE," + kt.getId());
            }

            // Save assigned fortunes
            for (Knight kt : knights) {
                if (kt.getActiveFortune() != null) {
                    out.println("FORTUNE," + kt.getId() + "," + kt.getActiveFortune().getName());
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Unable to save file: " + filename);
        }
    }
    
        // find fortune by name, used to assign fortunes used by nights after loading saved characters.
    private Fortune getFortuneByName(String name) {
        for (Fortune f : fortunes) {
            if (f.getName().equalsIgnoreCase(name)) {
                return f;
            }
        }
        return null;
    }
}

