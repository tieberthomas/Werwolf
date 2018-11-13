package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.Utils.Rand;
import root.mechanics.KillLogik.Selbstmord;

import java.util.ArrayList;
import java.util.List;

public class Irrlicht extends Hauptrolle {
    public static final String ID = "ID_Irrlicht";
    public static final String NAME = "Irrlicht";
    public static final String IMAGE_PATH = ImagePath.IRRLICHT_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Flackern";
    public static final String STATEMENT_BESCHREIBUNG = "Irrlichter entscheiden sich, ob sie flackern möchten";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String INFO = "Irrlicht_Info";
    public static final String SECOND_STATEMENT_TITLE = "anderes Irrlicht";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Irrlicht erwacht und erfährt ein anderes Irrlicht";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    private static final String LAST_IRRLICHT_MESSAGE = "Du bist das letzte Irrlicht";

    private static List<String> geseheneIrrlichter = new ArrayList<>();

    public Irrlicht() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementID = INFO;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.numberOfPossibleInstances = 5;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        List<String> allSpieler = game.getIrrlichterStrings();

        return new FrontendControl(FrontendControlType.IRRLICHT_DROPDOWN, allSpieler);
    }

    public static FrontendControl processFlackerndeIrrlichter(List<String> flackerndeIrrlichter) {
        int numberIrrlichterInGame = game.getIrrlichterStrings().size();
        int numberFlackerndeIrrlichter = flackerndeIrrlichter.size();

        if (numberFlackerndeIrrlichter == 1) {
            Spieler einzigesFlackerndesIrrlicht = game.findSpieler(flackerndeIrrlichter.get(0));
            Irrlicht irrlicht = ((Irrlicht) einzigesFlackerndesIrrlicht.hauptrolle);
            String randomIrrlicht = irrlicht.getRandomUnseenIrrlichtSpieler(einzigesFlackerndesIrrlicht.name);

            if (randomIrrlicht != null) {
                return new FrontendControl(SECOND_STATEMENT_TITLE, randomIrrlicht);
            } else {
                return new FrontendControl(SECOND_STATEMENT_TITLE, LAST_IRRLICHT_MESSAGE);
            }
        } else if (numberFlackerndeIrrlichter == numberIrrlichterInGame && numberIrrlichterInGame != 0) {
            Spieler spieler = getRandomIrrlichtToDie();
            Selbstmord.execute(spieler);
        }

        return new FrontendControl(SECOND_STATEMENT_TITLE);
    }

    private String getRandomUnseenIrrlichtSpieler(String einzigesFlackerndesIrrlichtName) {
        List<String> unseenIrrlichter = getAllUnseenIrrlichter(einzigesFlackerndesIrrlichtName);

        if (unseenIrrlichter == null) {
            return null;
        }

        String irrlichtName = null;

        if (unseenIrrlichter.size() > 0) {
            irrlichtName = Rand.getRandomElement(unseenIrrlichter);
            geseheneIrrlichter.add(irrlichtName);
        }

        return irrlichtName;
    }

    private List<String> getAllUnseenIrrlichter(String einzigesFlackerndesIrrlichtName) {
        List<String> irrlichter = game.getIrrlichterStrings();

        if (irrlichter.size() == 1) {
            return null;
        }

        irrlichter.removeAll(geseheneIrrlichter);
        irrlichter.remove(einzigesFlackerndesIrrlichtName);

        if (irrlichter.size() == 0) {
            geseheneIrrlichter.clear();
            return getAllUnseenIrrlichter(einzigesFlackerndesIrrlichtName);
        }

        return irrlichter;
    }

    private static Spieler getRandomIrrlichtToDie() {
        List<Spieler> irrlichter = game.getIrrlichter();

        if (irrlichter != null) {
            return Rand.getRandomElement(irrlichter);
        }

        return null;
    }
}
