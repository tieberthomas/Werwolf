package root.Persona.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Gefängniswärter extends Nebenrolle {
    public static final String STATEMENT_TITLE = "Schutzhaft";
    public static final String STATEMENT_BESCHREIBUNG = "Gefängniswärter erwacht und stellt einen Spieler  unter Schutzhaft";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Gefängniswärter";
    public static final String IMAGE_PATH = ImagePath.GEFÄNGNISWÄRTER_KARTE;
    public static boolean unique = true;

    public Gefängniswärter() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            chosenPlayer.aktiv = false;
            chosenPlayer.geschützt = true;
        }
    }
}
