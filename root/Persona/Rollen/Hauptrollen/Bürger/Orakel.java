package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Nebenrolle;
import root.Persona.Rolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Orakel extends Hauptrolle {
    public static final String STATEMENT_TITLE = "Bonusrolle";
    public static final String STATEMENT_BESCHREIBUNG = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;
    public static final String VERBRAUCHT_TITLE = "Bonusrollen";

    public static final String NAME = "Orakel";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.ORAKEL_KARTE;
    public static boolean spammable = true;

    public static ArrayList<String> geseheneNebenrollen = new ArrayList<>();

    public Orakel() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getInfo() {
        Nebenrolle randomNebenrolle = generateRandomNebenrolle();

        if (randomNebenrolle != null) {
            return new FrontendControl(FrontendControlType.CARD, randomNebenrolle.imagePath);
        } else {
            Spieler orakelSpieler = game.findSpielerPerRolle(NAME);

            if (orakelSpieler != null) {
                ArrayList<String> nebenRolleList = (ArrayList<String>) geseheneNebenrollen.clone();
                nebenRolleList.remove(orakelSpieler.nebenrolle.name);
                FrontendControl info = new FrontendControl(FrontendControlType.LIST, nebenRolleList);
                info.title = VERBRAUCHT_TITLE;
                return info;
            } else {
                return new FrontendControl(VERBRAUCHT_TITLE);
            }
        }
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    public Nebenrolle generateRandomNebenrolle() {
        ArrayList<Spieler> unseenBürger = getUnseenBürger();

        Nebenrolle nebenrolle;

        if (unseenBürger.size() > 0) {
            int randIndex = (int) (Math.random() * unseenBürger.size());

            nebenrolle = unseenBürger.get(randIndex).nebenrolle;
            geseheneNebenrollen.add(nebenrolle.name);
        } else {
            nebenrolle = null;
        }

        return nebenrolle;
    }

    public static ArrayList<Spieler> getUnseenBürger() {
        ArrayList<Spieler> bürger = Fraktion.getFraktionsMembers(Bürger.NAME);
        ArrayList<Spieler> bürgerToRemove = new ArrayList<>();

        if (Rolle.rolleLebend(NAME)) {
            Nebenrolle orakelSpielerNebenrolle = game.findSpielerPerRolle(NAME).nebenrolle;
            if (!geseheneNebenrollen.contains(orakelSpielerNebenrolle.name)) {
                geseheneNebenrollen.add(orakelSpielerNebenrolle.name);
            }
        }

        for (Spieler currentBürger : bürger) {
            if (geseheneNebenrollen.contains(currentBürger.nebenrolle.name)) {
                bürgerToRemove.add(currentBürger);
            }
        }

        bürger.removeAll(bürgerToRemove);
        return bürger;
    }
}
