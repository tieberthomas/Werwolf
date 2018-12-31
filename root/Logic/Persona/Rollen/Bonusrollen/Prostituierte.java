package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Prostituierte extends Bonusrolle {
    public static final String ID = "ID_Prostituierte";
    public static final String NAME = "Prostituierte";
    public static final String IMAGE_PATH = ImagePath.PROSTITUIERTE_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Bett legen";
    public static final String STATEMENT_BESCHREIBUNG = "Prostituierte legt sich zu einem Mitspieler ins Bett";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static Spieler host;

    public Prostituierte() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.selfuseable = false; //TODO true?
        this.spammable = false;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null && !chosenSpieler.equals(Game.game.findSpielerPerRolle(this.id))) {
            besucht = chosenSpieler;

            host = chosenSpieler;
        } else {
            host = Game.game.findSpielerPerRolle(this.id);
        }
    }
}
