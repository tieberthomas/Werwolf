package root.Persona.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class GrafVladimir extends Hauptrolle {
    public static final String STATEMENT_ID = "GrafVladimir";
    public static final String STATEMENT_TITLE = "Spieler unerkennbar machen";
    public static final String STATEMENT_BESCHREIBUNG = "Graf Vladimir erwacht und veerschleiert die Identität eines Spielers";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Graf Vladimir";
    public static final String IMAGE_PATH = ImagePath.GRAF_VLADIMIR_KARTE;
    public static final Fraktion FRAKTION = new Vampire();
    public static Spieler unerkennbarerSpieler;

    public GrafVladimir() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = false;
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

            unerkennbarerSpieler = chosenSpieler;
        }
    }
}