package root.Persona.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Gefängniswärter extends Nebenrolle {
    public static String title = "Schutzhaft";
    public static final String beschreibung = "Gefängniswärter erwacht und stellt einen Spieler  unter Schutzhaft";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Gefängniswärter";
    public static final String IMAGE_PATH = ImagePath.GEFÄNGNISWÄRTER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;

    public Gefängniswärter() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
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
    public boolean isSpammable() {
        return spammable;
    }
}
