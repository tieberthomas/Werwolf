package root.ResourceManagement;

import root.Spieler;
import root.mechanics.Game;

import java.util.List;

public class DataManager {
    public Game game;
    FileManager fileManager;

    public DataManager(Game game) {
        this.game = game;
        this.fileManager = new FileManager();
        fileManager.createFolderInAppdata();
    }

    public void loadLastComposition() {
        CompositionDto composition = fileManager.readComposition(ResourcePath.LAST_GAME_COMPOSITION_FILE);
        if (composition != null) {
            evaluateComposition(composition);
        } else {
            System.out.println("no composition from last game could be found");
        }
    }

    public void loadLastGame() {
        GameDto lastGame = fileManager.readGame(ResourcePath.LAST_GAME_FILE);
        if (lastGame != null) {
            evaluateSpieler(lastGame.spieler);
            evaluateComposition(lastGame.compositionDto, false);
        } else {
            System.out.println("no last game could be found");
        }
    }

    private void evaluateComposition(CompositionDto composition) {
        evaluateComposition(composition, true);
    }

    private void evaluateComposition(CompositionDto composition, boolean evaluateSpieler) {
        if (evaluateSpieler) {
            for (String spielerName : composition.spieler) {
                game.spieler.add(new Spieler(spielerName));
            }
        }

        for (String hauptRollenName : composition.hauptrollen) {
            game.hauptrollenInGame.add(game.findHauptrolle(hauptRollenName));
        }

        for (String bonusRollenName : composition.bonusrollen) {
            game.bonusrollenInGame.add(game.findBonusrolle(bonusRollenName));
        }
    }

    private void evaluateSpieler(List<SpielerDto> spieler) {
        for (SpielerDto currentSpieler : spieler) {
            Spieler newSpieler = new Spieler(currentSpieler.name, currentSpieler.hauptrolle, currentSpieler.bonusrolle);
            game.hauptrollenInGame.add(newSpieler.hauptrolle);
            game.bonusrollenInGame.add(newSpieler.bonusrolle);
        }
    }

    public void writeComposition() {
        fileManager.writeComposition(ResourcePath.LAST_GAME_COMPOSITION_FILE, game.getLivingSpielerStrings(),
                game.getHauptrolleInGameNames(), game.getBonusrolleInGameNames());
    }

    public void writeGame() {
        fileManager.writeGame(ResourcePath.LAST_GAME_FILE, game.getLivingSpieler(),
                game.getHauptrollenUnspecifiedStrings(), game.getBonusrollenUnspecifiedStrings());
    }
}
