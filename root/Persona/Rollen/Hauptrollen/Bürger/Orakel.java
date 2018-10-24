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

import java.util.ArrayList;

public class Orakel extends Hauptrolle {
    public static final String STATEMENT_IDENTIFIER = "Orakel";
    public static final String STATEMENT_TITLE = "Bonusrolle";
    public static final String STATEMENT_BESCHREIBUNG = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;
    public static final String VERBRAUCHT_TITLE = "Bonusrollen";

    public static final String NAME = "Orakel";
    public static final String IMAGE_PATH = ImagePath.ORAKEL_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static ArrayList<String> geseheneBonusrollen = new ArrayList<>();

    public Orakel() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
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
            Spieler orakelSpieler = game.findSpielerPerRolle(NAME);

            if (orakelSpieler != null) {
                ArrayList<String> bonusRolleList = (ArrayList<String>) geseheneBonusrollen.clone();
                bonusRolleList.remove(orakelSpieler.bonusrolle.name);
                FrontendControl info = new FrontendControl(FrontendControlType.LIST, bonusRolleList);
                info.title = VERBRAUCHT_TITLE;
                return info;
            } else {
                return new FrontendControl(VERBRAUCHT_TITLE);
            }
        }
    }

    private Bonusrolle getRandomUnseenBonusrolle() {
        ArrayList<Spieler> unseenBürger = getAllUnseenBürger();

        Bonusrolle bonusrolle;

        if (unseenBürger.size() > 0) {
            int randIndex = (int) (Math.random() * unseenBürger.size());

            bonusrolle = unseenBürger.get(randIndex).bonusrolle;
            geseheneBonusrollen.add(bonusrolle.name);
        } else {
            bonusrolle = null;
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
