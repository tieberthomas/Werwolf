package root.Persona.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NormalNight;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class MissVerona extends Hauptrolle {
    public static final String STATEMENT_ID = "MissVerona";
    public static final String STATEMENT_TITLE = "Spieler tarnen";
    public static final String STATEMENT_BESCHREIBUNG = "Miss Verona erwacht und verschleiert die Identität eines Spielers";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public static final String NAME = "Miss Verona";
    public static final String IMAGE_PATH = ImagePath.MISS_VERONA_KARTE;
    public static final Fraktion FRAKTION = new Vampire();

    public MissVerona() {
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
            NormalNight.getarnterSpieler = chosenSpieler;
        }
    }
}