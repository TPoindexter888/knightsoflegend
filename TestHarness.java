public class TestHarness {

    public static void main(String[] args) {

        // === SETUP ===
        GameData data = new CSVGameData("gamedata.csv", "knights.csv");
        GameView view = new ConsoleView();
        CombatEngine engine = new CombatEngine(data, view);
        GameController controller = new GameController(data, view, engine);

        System.out.println("=== BEGIN FULL PROGRAM TEST HARNESS ===\n");

        // === TEST 1: LIST ALL KNIGHTS ===
        System.out.println("\n--- TEST: list all ---");
        controller.processCommand("list all");

        // === TEST 2: SHOW KNIGHT BY NAME ===
        System.out.println("\n--- TEST: show arthur ---");
        controller.processCommand("show arthur");

        // === TEST 3: SET ACTIVE ===
        System.out.println("\n--- TEST: set active arthur ---");
        controller.processCommand("set active arthur");

        // === TEST 4: LIST ACTIVE ===
        System.out.println("\n--- TEST: list active ---");
        controller.processCommand("list active");

        // === TEST 5: REMOVE ACTIVE ===
        System.out.println("\n--- TEST: remove active arthur ---");
        controller.processCommand("remove active arthur");

        // === TEST 6: SAVE GAME ===
        System.out.println("\n--- TEST: save testSave.csv ---");
        controller.processCommand("save testSave.csv");

        // === TEST 7: EXPLORE / COMBAT ===
        System.out.println("\n--- TEST: explore ---");
        controller.processCommand("explore");

        // === TEST 8: UNKNOWN COMMAND ===
        System.out.println("\n--- TEST: unknown command ---");
        controller.processCommand("foobar");

        // === TEST 9: EXIT ===
        System.out.println("\n--- TEST: exit ---");
        boolean running = controller.processCommand("exit");
        System.out.println("Exit returned: " + running);

        System.out.println("\n=== END FULL PROGRAM TEST HARNESS ===");
    }
}
