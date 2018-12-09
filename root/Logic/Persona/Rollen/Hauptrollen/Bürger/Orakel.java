package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
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
    public static final String STATEMENT_BESCHREIBUNG = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    private static final String LAST_BUERGER_MESSAGE = "Du bist der letzte Bürger";

    private static List<String> geseheneBonusrollen = new ArrayList<>();

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
        List<Spieler> unseenBürger = getAllUnseenBürger();

        Bonusrolle bonusrolle;

        if (unseenBürger.size() > 0) {
            bonusrolle = Rand.getRandomElement(unseenBürger).bonusrolle;
            geseheneBonusrollen.add(bonusrolle.id);
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

    private static List<Spieler> getAllUnseenBürger() {
        List<Spieler> bürger = Fraktion.getFraktionsMembers(Bürger.ID);
        List<Spieler> bürgerToRemove = new ArrayList<>();

        if (Rolle.rolleLebend(ID)) {
            Bonusrolle orakelSpielerBonusrolle = Game.game.findSpielerPerRolle(ID).bonusrolle;
            if (!geseheneBonusrollen.contains(orakelSpielerBonusrolle.id)) {
                geseheneBonusrollen.add(orakelSpielerBonusrolle.id);
            }
        }

        for (Spieler currentBürger : bürger) {
            if (geseheneBonusrollen.contains(currentBürger.bonusrolle.id)) {
                bürgerToRemove.add(currentBürger);
            }
        }

        bürger.removeAll(bürgerToRemove);
        return bürger;
    }
}
