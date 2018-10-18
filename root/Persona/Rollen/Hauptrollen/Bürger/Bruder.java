package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

public class Bruder extends Hauptrolle {
    public static final String FIRST_NIGHT_STATEMENT_IDENTIFIER = "Bruder";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Brüder";
    public static final String FIRST_NIGHT_STATEMENT_SECOND_TITLE = "Neue Hauptrolle";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Brüder erwachen und sehen einander";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Bruder";
    public static final String IMAGE_PATH = ImagePath.BRUDER_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public Bruder() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.firstNightStatementIdentifier = FIRST_NIGHT_STATEMENT_IDENTIFIER;
        this.firstNightStatementTitle = FIRST_NIGHT_STATEMENT_TITLE;
        this.firstNightStatementBeschreibung = FIRST_NIGHT_STATEMENT_BESCHREIBUNG;
        this.firstNightStatementType = FIRST_NIGHT_STATEMENT_TYPE;

        this.numberOfPossibleInstances = 2;
    }
}