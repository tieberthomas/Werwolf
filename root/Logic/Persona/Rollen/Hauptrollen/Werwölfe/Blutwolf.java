package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Controller.FrontendObject.DropdownFrontendObject;
import root.Controller.FrontendObject.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Blutwolf extends Hauptrolle {
    public static final String ID = "ID_Blutwolf";
    public static final String NAME = "Blutwolf";
    public static final String IMAGE_PATH = ImagePath.BLUTWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();
    public static Spieler markedPlayer = null;
    public static boolean deadly = false;

    public static final String SETUP_NIGHT_STATEMENT_ID = ID;
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Ziel markieren";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Blutwolf erwacht und markiert sein Ziel";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE;

    public static final String SECOND_STATEMENT_ID = "Blutwolf2";
    public static final String SECOND_STATEMENT_TITLE = "Du bist das Ziel";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der gewählte Spieler erwacht und sieht dass er das Zeil des Blutwolfs ist";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.SHOW_TITLE;

    public Blutwolf() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;
        this.killing = true;

        this.setupNightStatementID = SETUP_NIGHT_STATEMENT_ID;
        this.setupNightStatementTitle = SETUP_NIGHT_STATEMENT_TITLE;
        this.setupNightStatementBeschreibung = SETUP_NIGHT_STATEMENT_BESCHREIBUNG;
        this.setupNightStatementType = SETUP_NIGHT_STATEMENT_TYPE;

        this.secondStatementID = SECOND_STATEMENT_ID;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;
    }

    public FrontendObject getFrontendObject() {
        return new DropdownFrontendObject(Game.game.getSpielerDropdownOptions(false));
    }

    public void processChosenOption(String chosenOption) {
        markedPlayer = Game.game.findSpieler(chosenOption);
    }
}