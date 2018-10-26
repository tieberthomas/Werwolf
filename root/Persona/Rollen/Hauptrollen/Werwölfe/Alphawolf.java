package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

public class Alphawolf extends Hauptrolle {
    public static final String FIRST_NIGHT_STATEMENT_ID = "Alphawolf";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Werwölfe";
    public static final String FIRST_NIGHT_STATEMENT_FERTIG_TITLE = "Fertig";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Alphawolf erwacht und erfährt die Rollen der Wolfsfraktion";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Alphawolf";
    public static final String IMAGE_PATH = ImagePath.ALPHAWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Alphawolf() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.firstNightStatementID = FIRST_NIGHT_STATEMENT_ID;
        this.firstNightStatementTitle = FIRST_NIGHT_STATEMENT_TITLE;
        this.firstNightStatementBeschreibung = FIRST_NIGHT_STATEMENT_BESCHREIBUNG;
        this.firstNightStatementType = FIRST_NIGHT_STATEMENT_TYPE;

        this.killing = true;
    }
}