package root.Persona.Rollen.Bonusrollen;

import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Prostituierte extends Bonusrolle {
    public static final String STATEMENT_TITLE = "Bett legen";
    public static final String STATEMENT_BESCHREIBUNG = "Prostituierte legt sich zu einem Mitspieler ins Bett";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Prostituierte";
    public static final String IMAGE_PATH = ImagePath.PROSTITUIERTE_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static Spieler host;

    public Prostituierte() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = false;
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
}
