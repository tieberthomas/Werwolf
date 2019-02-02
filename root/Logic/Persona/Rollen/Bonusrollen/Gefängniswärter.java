package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Gefängniswärter extends Bonusrolle {
    public static final String ID = "ID_Gefängniswärter";
    public static final String NAME = "Gefängniswärter";
    public static final String IMAGE_PATH = ImagePath.GEFÄNGNISWÄRTER_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Schutzhaft";
    public static final String STATEMENT_BESCHREIBUNG = "Gefängniswärter erwacht und stellt einen Spieler  unter Schutzhaft";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE;

    public Gefängniswärter() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.selfuseable = true;
        this.spammable = false;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            chosenSpieler.aktiv = false;
            chosenSpieler.geschützt = true;
        }
    }
}
