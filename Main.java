import java.io.File;

public class Main {
    private static String gamedata = "gamedata.csv";
    private static String saveData = "knights.csv";

    public static void main(String[] args) {
        // If the player has saved before (default save file) load that,
        // otherwise start from the default knights list.
        if (new File("saveData.csv").exists()) {
            saveData = "saveData.csv";
        }
        processArgs(args);

        GameData data = new CSVGameData(gamedata, saveData);
        GameView view = new ConsoleView();
        CombatEngine engine = new CombatEngine(data, view);
        GameController controller = new GameController(data, view, engine);
        controller.start();
    }

    // Command line options:
    //   --data=file   use file for the monsters/fortunes game data
    //   file          load knights from file (e.g. a previous save)
    private static void processArgs(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--data=")) {
                gamedata = arg.substring("--data=".length());
            } else {
                saveData = arg;
            }
        }
    }
}
