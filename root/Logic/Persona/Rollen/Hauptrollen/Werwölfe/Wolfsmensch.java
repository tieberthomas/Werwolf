package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Controller.FrontendObject.DropdownFrontendObject;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Wolfsmensch extends Hauptrolle {
    private static final String JA = "Ja";
    private static final String NEIN = "Nein";

    public static final String ID = "ID_Wolfsmensch";
    public static final String NAME = "Wolfsmensch";
    public static final String IMAGE_PATH = ImagePath.WOLFSMENSCH_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public static final String SETUP_NIGHT_STATEMENT_ID = ID;
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Nicht im Spiel";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Wolfsmensch erwacht und erfährt eine Bürgerrolle die nicht im Spiel ist";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.PERSONA_SPECAL;

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Zum Werwolf werden";
    public static final String STATEMENT_BESCHREIBUNG = "Wolfsmensch erwacht und entscheidet ob er zum Werwolf werden möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE;

    public static Hauptrolle fakeRolle = new Dorfbewohner();

    public Wolfsmensch() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.setupNightStatementID = SETUP_NIGHT_STATEMENT_ID;
        this.setupNightStatementTitle = SETUP_NIGHT_STATEMENT_TITLE;
        this.setupNightStatementBeschreibung = SETUP_NIGHT_STATEMENT_BESCHREIBUNG;
        this.setupNightStatementType = SETUP_NIGHT_STATEMENT_TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    public FrontendObject getFrontendObject() {
        return new DropdownFrontendObject(getJaNeinDropdownOptions());
    }

    private static DropdownOptions getJaNeinDropdownOptions() {
        List<String> dropdownStrings = new ArrayList<>();
        dropdownStrings.add(JA);
        dropdownStrings.add(NEIN);

        return new DropdownOptions(dropdownStrings);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        if (JA.equals(chosenOption)) {
            changeToWerwolf();
        }
    }

    private void changeToWerwolf() {
        Spieler wolfsMenschspieler = Game.game.findSpielerPerRolle(ID);
        if (wolfsMenschspieler != null) {
            wolfsMenschspieler.hauptrolle = new Werwolf();
        }
    }

    @Override
    public Zeigekarte isTötendInfo(Spieler requester) {
        return fakeRolle.isTötendInfo(requester);
    }

    @Override
    public Zeigekarte getFraktionInfo() {
        return fakeRolle.getFraktionInfo();
    }
}
