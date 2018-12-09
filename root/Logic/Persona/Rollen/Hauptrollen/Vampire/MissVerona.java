package root.Logic.Persona.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Vampire;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Phases.NormalNight;
import root.ResourceManagement.ImagePath;
import root.Logic.Spieler;
import root.Logic.Game;

public class MissVerona extends Hauptrolle {
    public static final String ID = "ID_Miss_Verona";
    public static final String NAME = "Miss Verona";
    public static final String IMAGE_PATH = ImagePath.MISS_VERONA_KARTE;
    public static final Fraktion FRAKTION = new Vampire();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler tarnen";
    public static final String STATEMENT_BESCHREIBUNG = "Miss Verona erwacht und tarnt die Identität eines Spielers und verwirrt ihn";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public MissVerona() {
        this.id = ID;
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
        return Game.game.getSpielerFrontendControl(this, false);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;
            NormalNight.getarnterSpieler = chosenSpieler;
        }
    }
}