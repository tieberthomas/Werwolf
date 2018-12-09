package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Logic.Game;
import root.Logic.KillLogic.AbsoluteKill;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Riese extends Hauptrolle {
    public static final String ID = "ID_Riese";
    public static final String NAME = "Riese";
    public static final String IMAGE_PATH = ImagePath.RIESE_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Mitspieler töten";
    public static final String STATEMENT_BESCHREIBUNG = "Riese erwacht und entscheidet sich ob einen Mitspieler töten möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public Riese() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.killing = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return Game.game.getSpielerFrontendControl(this, false);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            Spieler täter = Game.game.findSpielerPerRolle(this.id);
            AbsoluteKill.execute(chosenSpieler, täter);

            abilityCharges--;
        }
    }
}