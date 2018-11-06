package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.KillLogik.AbsoluteKill;

public class Riese extends Hauptrolle {
    public static final String STATEMENT_ID = "Riese";
    public static final String STATEMENT_TITLE = "Mitspieler töten";
    public static final String STATEMENT_BESCHREIBUNG = "Riese erwacht und entscheidet sich ob einen Mitspieler töten möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String ID = "Riese";
    public static final String NAME = "Riese";
    public static final String IMAGE_PATH = ImagePath.RIESE_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

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
        return game.getSpielerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            Spieler täter = game.findSpielerPerRolle(NAME);
            AbsoluteKill.execute(chosenSpieler, täter);

            abilityCharges--;
        }
    }
}