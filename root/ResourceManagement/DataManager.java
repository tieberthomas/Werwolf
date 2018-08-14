package root.ResourceManagement;

import root.Spieler;
import root.mechanics.Game;

import java.util.ArrayList;

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
        GameDto game = fileManager.readGame(ResourcePath.LAST_GAME_FILE);
        if (game != null) {
            evaluatePlayers(game.players);
            evaluateComposition(game.compositionDto, false);
        } else {
            System.out.println("no last game could be found");
        }
    }

    private void evaluateComposition(CompositionDto composition) {
        evaluateComposition(composition, true);
    }

    private void evaluateComposition(CompositionDto composition, boolean evaluatePlayers) {
        if (evaluatePlayers) {
            for (String spielerName : composition.spieler) {
                game.spieler.add(new Spieler(spielerName));
            }
        }

        for (String hauptRollenName : composition.hauptrollen) {
            game.mainRolesInGame.add(game.findHauptrolle(hauptRollenName));
        }

        for (String nebenRollenName : composition.nebenrollen) {
            game.secondaryRolesInGame.add(game.findNebenrolle(nebenRollenName));
        }
    }

    private void evaluatePlayers(ArrayList<PlayerDto> players) {
        for (PlayerDto player : players) {
            Spieler newSpieler = new Spieler(player.name, player.hauptrolle, player.nebenrolle);
            game.mainRolesInGame.add(newSpieler.hauptrolle);
            game.secondaryRolesInGame.add(newSpieler.nebenrolle);
        }
    }

    public void writeComposition() {
        fileManager.writeComposition(ResourcePath.LAST_GAME_COMPOSITION_FILE, game.getLivingPlayerStrings(),
                game.getMainRoleInGameNames(), game.getSecondaryRoleInGameNames());
    }

    public void writeGame() {
        fileManager.writeGame(ResourcePath.LAST_GAME_FILE, game.getLivingPlayer(),
            game.getMainRolesUnspecifiedStrings(), game.getSecondaryRolesUnspecifiedStrings());
    }
}
