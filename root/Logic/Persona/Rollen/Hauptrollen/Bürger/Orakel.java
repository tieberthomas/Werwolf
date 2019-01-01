package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Controller.FrontendObject.FrontendObjectType;
import root.Controller.FrontendObject.FrontendObject;
import root.Controller.FrontendObject.ImageFrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rolle;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;
import root.Utils.Rand;

import java.util.ArrayList;
import java.util.List;

public class Orakel extends Hauptrolle {
    public static final String ID = "ID_Orakel";
    public static final String NAME = "Orakel";
    public static final String IMAGE_PATH = ImagePath.ORAKEL_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Bonusrolle";
    private static final String LIST_TITLE = "Bonusrollen";
    public static final String STATEMENT_BESCHREIBUNG = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    private static final String note = "Du hast die Bonusrollen von allen Bürgern gesehen, du erwachst jetzt nicht mehr.";

    private static List<String> geseheneBonusrollen = new ArrayList<>();

    public static boolean seenEverything = false;

    public Orakel() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendObject getInfo() {
        if (seenEverything) {
            return new FrontendObject();
        }

        Bonusrolle randomBonusrolle = getRandomUnseenBonusrolle();

        if (randomBonusrolle != null) {
            return new ImageFrontendObject(FrontendObjectType.CARD, randomBonusrolle.imagePath);
        } else {
            seenEverything = true;
            return allSeenBonusrollen();
        }
    }

    private Bonusrolle getRandomUnseenBonusrolle() {
        List<Spieler> unseenBürger = getAllUnseenBürger();

        if (unseenBürger.size() > 0) {
            Bonusrolle bonusrolle = Rand.getRandomElement(unseenBürger).bonusrolle;
            geseheneBonusrollen.add(bonusrolle.name);
            return bonusrolle;
        } else {
            return null;
        }
    }

    private static List<Spieler> getAllUnseenBürger() {
        List<Spieler> bürger = Fraktion.getFraktionsMembers(Bürger.ID);
        List<Spieler> bürgerToRemove = new ArrayList<>();

        if (Rolle.rolleLebend(ID)) {
            Bonusrolle orakelSpielerBonusrolle = Game.game.findSpielerPerRolle(ID).bonusrolle;
            if (!geseheneBonusrollen.contains(orakelSpielerBonusrolle.name)) {
                geseheneBonusrollen.add(orakelSpielerBonusrolle.name);
            }
        }

        for (Spieler currentBürger : bürger) {
            if (geseheneBonusrollen.contains(currentBürger.bonusrolle.name)) {
                bürgerToRemove.add(currentBürger);
            }
        }

        bürger.removeAll(bürgerToRemove);
        return bürger;
    }

    private FrontendObject allSeenBonusrollen() {
        Bonusrolle orakelSpielerBonusrolle = Game.game.findSpielerPerRolle(ID).bonusrolle;
        geseheneBonusrollen.remove(orakelSpielerBonusrolle.name);
        return new FrontendObject(FrontendObjectType.LIST_WITH_NOTE, LIST_TITLE, note, geseheneBonusrollen);
    }
}
