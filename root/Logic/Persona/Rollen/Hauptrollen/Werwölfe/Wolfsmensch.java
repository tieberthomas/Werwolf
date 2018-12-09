package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.ResourceManagement.ImagePath;

public class Wolfsmensch extends Hauptrolle {
    public static final String ID = "ID_Wolfsmensch";
    public static final String NAME = "Wolfsmensch";
    public static final String IMAGE_PATH = ImagePath.WOLFSMENSCH_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public static final String SETUP_NIGHT_STATEMENT_ID = ID;
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Nicht im Spiel";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Wolfsmensch erwacht und erfährt eine Bürgerrolle die nicht im Spiel ist";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public Wolfsmensch() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.setupNightStatementID = SETUP_NIGHT_STATEMENT_ID;
        this.setupNightStatementTitle = SETUP_NIGHT_STATEMENT_TITLE;
        this.setupNightStatementBeschreibung = SETUP_NIGHT_STATEMENT_BESCHREIBUNG;
        this.setupNightStatementType = SETUP_NIGHT_STATEMENT_TYPE;
    }

    public Zeigekarte getFraktionInfo() {
        return new BürgerZeigekarte();
    }
}
