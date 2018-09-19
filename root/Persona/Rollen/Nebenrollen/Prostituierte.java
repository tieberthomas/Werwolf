package root.Persona.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Prostituierte extends Nebenrolle {
    public static String title = "Bett legen";
    public static final String beschreibung = "Prostituierte legt sich zu einem Mitspieler ins Bett";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;
    public static final String NAME = "Prostituierte";
    public static final String IMAGE_PATH = ImagePath.PROSTITUIERTE_KARTE;
    public static boolean spammable = false;

    public static Spieler host;

    public Prostituierte() {
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
        if (chosenPlayer != null && !chosenPlayer.equals(game.findSpielerPerRolle(NAME))) {
            besucht = chosenPlayer;

            host = chosenPlayer;
        } else {
            host = game.findSpielerPerRolle(NAME);
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
