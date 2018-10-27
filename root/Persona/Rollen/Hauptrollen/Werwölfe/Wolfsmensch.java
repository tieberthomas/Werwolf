package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

public class Wolfsmensch extends Hauptrolle {
    public static final String FIRST_NIGHT_STATEMENT_ID = "Wolfsmensch";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Nicht im Spiel";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Wolfsmensch erwacht und erfährt eine Bürgerrolle die nicht im Spiel ist";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Wolfsmensch";
    public static final String IMAGE_PATH = ImagePath.WOLFSMENSCH_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Wolfsmensch() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.firstNightStatementID = FIRST_NIGHT_STATEMENT_ID;
        this.firstNightStatementTitle = FIRST_NIGHT_STATEMENT_TITLE;
        this.firstNightStatementBeschreibung = FIRST_NIGHT_STATEMENT_BESCHREIBUNG;
        this.firstNightStatementType = FIRST_NIGHT_STATEMENT_TYPE;
    }

    public Zeigekarte getFraktionInfo() {
        return new Bürger().getZeigeKarte();
    }
}
