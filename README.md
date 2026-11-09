# Knights of Legend

A text-based fantasy adventure game for the console, written in Java.

Build a party of up to four knights from a roster, send them on quests, and fight randomly generated monsters. Before each quest, every knight draws a random fortune that changes their health, armor, accuracy or damage, for better or worse. Battles use dice rolls: a D20 attack roll against the target's armor, then a damage die. Knights gain experience for each monster they defeat. Defeated knights aren't lost; they go back to the roster and heal. You can save your progress to a file and load it the next time you play.

## Requirements

- Java 15 or later

## Getting started

```bash
javac *.java
java Main
```

Run the game from the project folder so it can find the data files.

### Command line options

```bash
java Main [saveFile] [--data=gameDataFile]
```

| Option | Default | Description |
| --- | --- | --- |
| `saveFile` | `saveData.csv` if it exists, otherwise `knights.csv` | Knight roster to load |
| `--data=file` | `gamedata.csv` | Monsters and fortunes |

## How to play

| Command | Action |
| --- | --- |
| `ls` or `list all` | List all knights |
| `list active` | List the knights in your party |
| `show <name or id>` | Show a knight's stats |
| `set active <name or id>` | Add a knight to your party (max 4) |
| `remove active <name or id>` | Remove a knight from your party and heal them |
| `explore`, `adventure` or `quest` | Go on a quest |
| `save [filename]` | Save your progress (default: `saveData.csv`) |
| `help` or `?` | Show the help text |
| `exit` or `goodbye` | Quit the game |

Active knights don't heal between quests. Take a knight out of your party to restore their health.

## Data files

| File | Format |
| --- | --- |
| `knights.csv` | `name,maxHP,armor,hitModifier,damageDie,xp` |
| `gamedata.csv` | `MOB,name,maxHP,armor,hitModifier,damageDie`<br>`FORTUNE,name,hpBonus,armor,hitModifier,damageDie` (use `-` for no damage die) |

Save files use the same format as `knights.csv`.

## Design

The game follows a model-view-controller layout:

| Layer | Classes | Role |
| --- | --- | --- |
| Model | `Knight`, `MOB`, `Fortune`, `DiceType`, `Attributes` | Characters, bonuses and dice |
| Data | `GameData` (abstract), `CSVGameData` | Loading and saving game state |
| View | `GameView` (interface), `ConsoleView` | All input and output |
| Controller | `GameController`, `CombatEngine` | Command handling and battle logic |

- **Swappable layers.** The controller and combat engine only use the `GameData` abstract class and the `GameView` interface. Adding a JSON data source or a graphical interface means writing one new class, with no changes to the game logic.
- **Inheritance and polymorphism.** `Knight` extends `MOB`, so knights and monsters share one combat routine. `Knight` overrides the stat getters to add its fortune's bonuses, so combat code never needs to know whether a fortune is active.
- **Shared contract.** `MOB`, `Knight` and `Fortune` all implement the `Attributes` interface, which defines the stats every combatant and bonus card provides.
- **Safe copies.** Monsters are copied from templates for each encounter, so damage never carries over between battles.

## Testing

`TestHarness` runs the main commands (listing, showing, activating and removing knights, saving, and questing) against the real data files:

```bash
javac *.java
java TestHarness
```
