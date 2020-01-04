package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Controller.FrontendObject.FrontendObject;
import root.Controller.FrontendObject.ImageFrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Schattenmensch extends Hauptrolle {
    public static final String ID = "ID_Schattenmensch";
    public static final String NAME = "Schattenmensch";
    public static final String IMAGE_PATH = ImagePath.SCHATTENMENSCH_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static boolean shallBeTransformed = false;
    public static boolean reineSeelWasPresentLastDay = false;

    public static final String STATEMENT_ID = ID;
    private static final String TRANSFORMATION_TITLE = "Verwandlung";
    private static final String NO_TRANSFORMATION_TITLE = "Keine Verwandlung";
    public static final String STATEMENT_BESCHREIBUNG = "Der Schattenmensch erwacht, und wird gegebenenfalls zum Schattenpriester.";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_SPECAL;

    public Schattenmensch() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = NO_TRANSFORMATION_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.numberOfPossibleInstances = 1;
    }

    public static boolean addStatement() {
        return reineSeelWasPresentLastDay;
    }

    @Override
    public FrontendObject getFrontendObject() {
        String title = NO_TRANSFORMATION_TITLE;

        if (shallBeTransformed) {
            title = TRANSFORMATION_TITLE;
        }

        return new ImageFrontendObject(title, imagePath);
    }

    public static void transformIfShallBeTransformed() {
        if (shallBeTransformed) {
            Spieler schattenmenschSpieler = Game.game.findSpielerPerRolle(ID);
            if (schattenmenschSpieler != null) {
                Schattenpriester schattenpriester = new Schattenpriester();
                schattenpriester.neuster = true;
                schattenmenschSpieler.hauptrolle = schattenpriester;

                SchattenpriesterFraktion.spielerToChangeCards = schattenmenschSpieler;
            }

            shallBeTransformed = false;
        }
    }

    public static void resetReineSeeleFlag() {
        reineSeelWasPresentLastDay = false;
    }
}
