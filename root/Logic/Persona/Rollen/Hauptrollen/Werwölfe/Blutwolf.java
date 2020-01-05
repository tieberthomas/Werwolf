package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Controller.FrontendObject.DropdownFrontendObject;
import root.Controller.FrontendObject.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Blutwolf extends Hauptrolle {
    public static final String ID = "ID_Blutwolf";
    public static final String NAME = "Blutwolf";
    public static final String IMAGE_PATH = ImagePath.BLUTWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();
    public static Spieler markedPlayer = null;
    public static boolean markedPlayerJustDied = false;
    public static boolean deadly = false;

    public static final String SETUP_NIGHT_STATEMENT_ID = ID;
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Ziel markieren";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Blutwolf erwacht und markiert sein Ziel";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE;

    public static final String SECOND_STATEMENT_ID = "Blutwolf_2";
    public static final String SECOND_STATEMENT_TITLE = "Du bist das Ziel";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der gewählte Spieler erwacht und sieht dass er das Zeil des Blutwolfs ist";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.SHOW_TITLE;

    public static final String DYNAMIC_STATEMENT_ID = "Blutwolf_Dynamic";
    public static final String DYNAMIC_STATEMENT_TITLE = "Das Ziel des Blutwolfs war unter denn Opfern";
    public static final String DYNAMIC_STATEMENT_BESCHREIBUNG = "Das Ziel des Blutwolfs war unter denn Opfern";
    public static final StatementType DYNAMIC_STATEMENT_TYPE = StatementType.SHOW_TITLE;

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

        this.dynamicStatementID = DYNAMIC_STATEMENT_ID;
        this.dynamicStatementTitle = DYNAMIC_STATEMENT_TITLE;
        this.dynamicStatementBeschreibung = DYNAMIC_STATEMENT_BESCHREIBUNG;
        this.dynamicStatementType = DYNAMIC_STATEMENT_TYPE;
    }

    public FrontendObject getFrontendObject() {
        return new DropdownFrontendObject(Game.game.getSpielerDropdownOptions(false));
    }

    public void processChosenOption(String chosenOption) {
        markedPlayer = Game.game.findSpieler(chosenOption);
    }

    @Override
    public StatementState getDynamicState() {
        if (markedPlayerJustDied) {
            return StatementState.NORMAL;
        } else {
            return StatementState.INVISIBLE_NOT_IN_GAME;
        }
    }

    @Override
    public void cleanUpAfterNight() {
        markedPlayerJustDied = false;
    }

}