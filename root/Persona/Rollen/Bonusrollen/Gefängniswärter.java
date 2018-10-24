package root.Persona.Rollen.Bonusrollen;

import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Gefängniswärter extends Bonusrolle {
    public static final String STATEMENT_IDENTIFIER = "Gefängniswärter";
    public static final String STATEMENT_TITLE = "Schutzhaft";
    public static final String STATEMENT_BESCHREIBUNG = "Gefängniswärter erwacht und stellt einen Spieler  unter Schutzhaft";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Gefängniswärter";
    public static final String IMAGE_PATH = ImagePath.GEFÄNGNISWÄRTER_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public Gefängniswärter() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = false;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return game.getSpielerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            chosenSpieler.aktiv = false;
            chosenSpieler.geschützt = true;
        }
    }
}
