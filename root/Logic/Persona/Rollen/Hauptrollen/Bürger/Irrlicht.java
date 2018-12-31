package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Controller.FrontendObjectType;
import root.Controller.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.KillLogic.Selbstmord;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Bonusrollen.DunklesLicht;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;
import root.Utils.Rand;

import java.util.ArrayList;
import java.util.List;

public class Irrlicht extends Hauptrolle {
    public static final String ID = "ID_Irrlicht";
    public static final String NAME = "Irrlicht";
    public static final String IMAGE_PATH = ImagePath.IRRLICHT_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Flackern";
    public static final String STATEMENT_BESCHREIBUNG = "Lichter entscheiden sich, ob sie flackern möchten";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String INFO = "Irrlicht_Info";
    public static final String SECOND_STATEMENT_TITLE = "anderes Irrlicht";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Das angetippte Licht erwacht und erfährt ein anderes Irrlicht";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    private static final String LAST_IRRLICHT_MESSAGE = "Du bist das letzte Irrlicht";

    public List<String> geseheneIrrlichter = new ArrayList<>();

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
    public FrontendObject getFrontendObject() {
        return new FrontendObject(FrontendObjectType.IRRLICHT_DROPDOWN, new DropdownOptions(Game.game.getIrrlichterStrings()));
    }

    public static FrontendObject processFlackerndeIrrlichter(List<String> flackerndeIrrlichter) {
        int numberIrrlichterInGame = Game.game.getIrrlichterStrings().size();
        int numberFlackerndeIrrlichter = flackerndeIrrlichter.size();

        if (numberFlackerndeIrrlichter == 1) {
            Spieler einzigesFlackerndesIrrlicht = Game.game.findSpieler(flackerndeIrrlichter.get(0));
            String randomIrrlicht = getRandomUnseenIrrlichtSpieler(einzigesFlackerndesIrrlicht);

            if (randomIrrlicht != null) {
                return new FrontendObject(SECOND_STATEMENT_TITLE, randomIrrlicht);
            } else {
                return new FrontendObject(SECOND_STATEMENT_TITLE, LAST_IRRLICHT_MESSAGE);
            }
        } else if (numberFlackerndeIrrlichter == numberIrrlichterInGame && numberIrrlichterInGame != 0) {
            Spieler spieler = getRandomIrrlichtToDie();
            Selbstmord.execute(spieler);
        }

        return new FrontendObject(SECOND_STATEMENT_TITLE);
    }

    private static String getRandomUnseenIrrlichtSpieler(Spieler einzigesFlackerndesIrrlicht) {
        List<String> unseenIrrlichter = getAllUnseenIrrlichter(einzigesFlackerndesIrrlicht);

        if (unseenIrrlichter == null) {
            return null;
        }

        String irrlichtName = null;

        if (unseenIrrlichter.size() > 0) {
            irrlichtName = Rand.getRandomElement(unseenIrrlichter);
            addGesehenesIrrlicht(einzigesFlackerndesIrrlicht, irrlichtName);
        }

        return irrlichtName;
    }

    private static void addGesehenesIrrlicht(Spieler irrlichtSpieler, String name) {
        if (irrlichtSpieler.hauptrolle.equals(Irrlicht.ID)) {
            ((Irrlicht) irrlichtSpieler.hauptrolle).geseheneIrrlichter.add(name);
        } else if (irrlichtSpieler.bonusrolle.equals(DunklesLicht.ID)) {
            ((DunklesLicht) irrlichtSpieler.bonusrolle).geseheneIrrlichter.add(name);
        }
    }

    private static List<String> getAllUnseenIrrlichter(Spieler irrlichtSpieler) {
        List<String> irrlichter = Game.game.getIrrlichterStrings();

        if (irrlichter.size() == 1) {
            return null;
        }

        List<String> geseheneIrrlichter = getGeseheneIrrlichter(irrlichtSpieler);

        irrlichter.removeAll(geseheneIrrlichter);
        irrlichter.remove(irrlichtSpieler.name);

        if (irrlichter.size() == 0) {
            geseheneIrrlichter.clear();
            return getAllUnseenIrrlichter(irrlichtSpieler);
        }

        return irrlichter;
    }

    private static List<String> getGeseheneIrrlichter(Spieler irrlichtSpieler) {
        if (irrlichtSpieler.hauptrolle.equals(Irrlicht.ID)) {
            return ((Irrlicht) irrlichtSpieler.hauptrolle).geseheneIrrlichter;
        } else if (irrlichtSpieler.bonusrolle.equals(DunklesLicht.ID)) {
            return ((DunklesLicht) irrlichtSpieler.bonusrolle).geseheneIrrlichter;
        }
        return new ArrayList<>();
    }

    private static Spieler getRandomIrrlichtToDie() {
        List<Spieler> irrlichter = Game.game.getIrrlichter();

        if (irrlichter != null) {
            return Rand.getRandomElement(irrlichter);
        }

        return null;
    }
}
