package root.Persona.Fraktionen;

import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;

public class Vampire extends Fraktion {
    public static String title = "Opfer wählen";
    public static final String beschreibung = "Die Vampire erwachen und wählen ein Opfer aus";
    public static StatementType statementType = StatementType.FRAKTION_CHOOSE_ONE;
    public static final String name = "Vampire";
    public static final Color farbe = Color.red;
    public static final String imagePath = ImagePath.VAMPIERE_ICON;

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            Spieler täter = Fraktion.getFraktionsMembers(name).get(0);
            Opfer.addVictim(chosenPlayer, täter, true);
        }
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerFrontendControl();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public StatementType getStatementType() {
        return statementType;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }
}
