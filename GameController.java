

public class GameController {
    GameData data;
    CombatEngine engine;
    GameView view;

    public GameController(GameData data, GameView view, CombatEngine engine) {
        this.data = data;
        this.view = view;
        this.engine = engine;
    }

    // Starts the game, causing it to run until a client exits. Starts with splashScreen,
    // loops as long as processCommand returns true, prints endgame when loop is done.
    public void start() {
        
        view.splashScreen();
        boolean running = true;
        while (running) {
            String command = view.displayMainMenu().trim().toLowerCase();
            running = processCommand(command);
        }
        view.endGame();
    }
    // Setup as a helper method for start(), processes commands the client passes in through
    protected boolean processCommand(String command) {
        if (command.equals("exit") || command.equals("bye")) {
            return false;
        }
        else if(command.equals("ls") || command.equals("list all")) {
            view.listKnights(data.getKnights());
        }
        else if(command.equals("list active")) {
            view.listKnights(data.getActiveKnights());
        }
        else if(command.startsWith("show ")) {
            String arg = command.substring("show ".length()).trim();
            processShowKnight(arg);
        }
        else if(command.startsWith("show")) {
            view.knightNotFound();
        }
        else if(command.startsWith("set active ")) {
            String arg = command.substring("set active ".length()).trim();
            processSetActive(arg);
        }
        else if(command.startsWith("remove active ")) {
            String arg = command.substring("remove active ".length()).trim();
            processRemoveKnight(arg);
        }
        else if (command.startsWith("save ")) {
            String filename = command.substring("save ".length()).trim();
            if (filename.isEmpty()) {
                data.save("saveData.csv");
            }
            else {
                data.save(filename);
            }
        }
        else if (command.equals("explore") || command.equals("adventure") || command.equals("quest")) {
            engine.initialize();
            engine.runCombat();
            engine.clear();
        }
        else if(command.equals("help") || command.equals("?")) {
            view.printHelp();
        }
    
        return true;
    }
    
    private void processRemoveKnight(String nameOrId) {
        Knight kt = data.getActive(nameOrId);
        if (kt == null) {
            view.knightNotFound();
            return;
    }
        data.removeActive(kt);
}

    private void processSetActive(String active) {
        Knight kt = data.getKnight(active);
        if (kt == null) {
            view.knightNotFound();
            return;
        }
        if (!data.setActive(kt)) {
            view.setActiveFailed();
        }
    }

    private void processShowKnight(String nameOrId) {
        Knight kt = data.getKnight(nameOrId);
        if (kt == null) {
            view.knightNotFound();
            return;
        }
        view.showKnight(kt);
    }
}
