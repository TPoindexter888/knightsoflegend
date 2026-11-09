
public class Main {
    public static void main(String[] args) {
        GameData data = new CSVGameData("gamedata.csv", "knights.csv");
        GameView view = new ConsoleView();
        CombatEngine engine = new CombatEngine(data, view);
        GameController controller = new GameController(data, view, engine);
        controller.start();
}

}
