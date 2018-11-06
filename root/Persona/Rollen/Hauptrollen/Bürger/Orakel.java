package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Rolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Rand;

import java.util.ArrayList;

public class Orakel extends Hauptrolle {
    public static final String ID = "Orakel";
    public static final String NAME = "Orakel";
    public static final String IMAGE_PATH = ImagePath.ORAKEL_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = "Orakel";
    public static final String STATEMENT_TITLE = "Bonusrolle";
    public static final String STATEMENT_BESCHREIBUNG = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    private static final String LAST_BUERGER_MESSAGE = "Du bist der letzte Bürger";

    private static ArrayList<String> geseheneBonusrollen = new ArrayList<>();

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
    public FrontendControl getInfo() {
        Bonusrolle randomBonusrolle = getRandomUnseenBonusrolle();

        if (randomBonusrolle != null) {
            return new FrontendControl(FrontendControlType.CARD, randomBonusrolle.imagePath);
        } else {
            return new FrontendControl(STATEMENT_TITLE, LAST_BUERGER_MESSAGE);
        }
    }

    private Bonusrolle getRandomUnseenBonusrolle() {
        ArrayList<Spieler> unseenBürger = getAllUnseenBürger();

        Bonusrolle bonusrolle;

        if (unseenBürger.size() > 0) {
            bonusrolle = Rand.getRandomElement(unseenBürger).bonusrolle;
            geseheneBonusrollen.add(bonusrolle.name);
        } else {
            if (geseheneBonusrollen.size() == 1 || geseheneBonusrollen.size() == 0) {
                //das heißt dass keine bonusrollen mehr bei bürgern sind
                return null;
            } else {
                geseheneBonusrollen.clear();
                return getRandomUnseenBonusrolle();
            }
        }

        return bonusrolle;
    }

    private static ArrayList<Spieler> getAllUnseenBürger() {
        ArrayList<Spieler> bürger = Fraktion.getFraktionsMembers(Bürger.NAME);
        ArrayList<Spieler> bürgerToRemove = new ArrayList<>();

        if (Rolle.rolleLebend(NAME)) {
            Bonusrolle orakelSpielerBonusrolle = game.findSpielerPerRolle(NAME).bonusrolle;
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
}
