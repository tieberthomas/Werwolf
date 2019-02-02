package root.ResourceManagement;

import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Spieler;
import root.Utils.ListHelper;

import java.util.List;

public class DataManager {
    private FileManager fileManager;

    public DataManager() {
        this.fileManager = new FileManager();
        fileManager.createFolderInAppdata();
    }

    public void loadLastComposition() {
        CompositionDto composition = fileManager.readComposition(ResourcePath.LAST_GAME_COMPOSITION_FILE);
        if (composition != null) {
            processComposition(composition);
            System.out.println("Composition was loaded successfully.");
        } else {
            System.out.println("No composition from last game could be found.");
        }
    }

    public void loadLastGame() {
        GameDto lastGame = fileManager.readGame(ResourcePath.LAST_GAME_FILE);
        if (lastGame != null) {
            processSpieler(lastGame.spieler);
            processComposition(lastGame.compositionDto, false);
            System.out.println("Game was loaded successfully.");
        } else {
            System.out.println("No last game could be found.");
        }
    }

    private void processComposition(CompositionDto composition) {
        processComposition(composition, true);
    }

    private void processComposition(CompositionDto composition, boolean processSpielers) {
        if (processSpielers) {
            processSpielers(composition.spieler);
        }
        processHauptrollen(composition.hauptrollen);
        processBonusrollen(composition.bonusrollen);

        Game.game.spielerSpecified = ListHelper.cloneList(Game.game.spieler);
    }

    private void processSpielers(List<String> spieler) {
        for (String spielerName : spieler) {
            Game.game.spieler.add(new Spieler(spielerName));
        }
    }

    private void processHauptrollen(List<String> hauptrollen) {
        for (String hauptRollenName : hauptrollen) {
            Hauptrolle hauptrolle = Game.game.findHauptrollePerName(hauptRollenName);
            if (hauptrolle == null) {
                hauptrolle = Hauptrolle.getDefaultHauptrolle();
            }
            Game.game.hauptrollenInGame.add(hauptrolle);
        }
    }

    private void processBonusrollen(List<String> bonusrollen) {
        for (String bonusRollenName : bonusrollen) {
            Bonusrolle bonusrolle = Game.game.findBonusrollePerName(bonusRollenName);
            if (bonusrolle == null) {
                bonusrolle = Bonusrolle.DEFAULT_BONUSROLLE;
            }
            Game.game.bonusrollenInGame.add(bonusrolle);
        }
    }

    private void processSpieler(List<SpielerDto> spieler) {
        for (SpielerDto currentSpieler : spieler) {
            Spieler newSpieler = new Spieler(currentSpieler.name, currentSpieler.hauptrolle, currentSpieler.bonusrolle);
            Game.game.hauptrollenInGame.add(newSpieler.hauptrolle);
            Game.game.bonusrollenInGame.add(newSpieler.bonusrolle);
        }
    }

    public void writeComposition() {
        boolean successfull = fileManager.writeComposition(ResourcePath.LAST_GAME_COMPOSITION_FILE, Game.game.getLivingSpielerStrings(),
                Game.game.getHauptrolleInGameNames(), Game.game.getBonusrolleInGameNames());

        if (successfull) {
            System.out.println("Composition was saved successfully.");
        } else {
            System.out.println("Composition could not be saved.");
        }
    }

    public void writeGame() {
        boolean successfull = fileManager.writeGame(ResourcePath.LAST_GAME_FILE, Game.game.getLivingSpieler(),
                Game.game.getHauptrollenUnspecifiedStrings(), Game.game.getBonusrollenUnspecifiedStrings());

        if (successfull) {
            System.out.println("Game was saved successfully.");
        } else {
            System.out.println("Game could not be saved.");
        }
    }
}
