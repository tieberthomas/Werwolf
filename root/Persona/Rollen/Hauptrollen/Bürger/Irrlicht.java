package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;

public class Irrlicht extends Hauptrolle {
    public static final String STATEMENT_IDENTIFIER = "Irrlicht";
    public static final String STATEMENT_TITLE = "";
    public static final String STATEMENT_BESCHREIBUNG = "";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Irrlicht";
    public static final String IMAGE_PATH = ""; //TODO ImagePath.IRRLICHT_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public Irrlicht() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.numberOfPossibleInstances = 5;
    }
}
