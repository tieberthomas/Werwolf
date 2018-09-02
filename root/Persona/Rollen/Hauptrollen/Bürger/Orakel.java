package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Nebenrolle;
import root.Persona.Rolle;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 15.06.2018.
 */
public class Orakel extends Hauptrolle {
    public static String title = "Bonusrolle";
    public static final String beschreibung = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static StatementType statementType = StatementType.ROLLE_INFO;
    public static final String VERBRAUCHT_TITLE = "Bonusrollen";
    public static final String name = "Orakel";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.ORAKEL_KARTE;
    public static boolean spammable = true;

    public static ArrayList<String> geseheneNebenrollen = new ArrayList<>();

    @Override
    public FrontendControl getInfo() {
        Nebenrolle randomNebenrolle = generateRandomNebenrolle();

        if (randomNebenrolle != null) {
            return new FrontendControl(FrontendControlType.CARD, randomNebenrolle.getImagePath());
        } else {
            Spieler orakelSpieler = game.findSpielerPerRolle(name);

            if(orakelSpieler != null) {
                ArrayList<String> nebenRolleList = (ArrayList<String>) geseheneNebenrollen.clone();
                nebenRolleList.remove(orakelSpieler.nebenrolle.getName());
                FrontendControl info = new FrontendControl(FrontendControlType.LIST, nebenRolleList);
                info.title = VERBRAUCHT_TITLE;
                return info;
            } else {
                return new FrontendControl(VERBRAUCHT_TITLE);
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public StatementType getStatementType() {
        return statementType;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    public Nebenrolle generateRandomNebenrolle() {
        ArrayList<Spieler> unseenBürger = getUnseenBürger();

        Nebenrolle nebenrolle;

        if(unseenBürger.size()>0) {
            int randIndex = (int) (Math.random() * unseenBürger.size());

            nebenrolle = unseenBürger.get(randIndex).nebenrolle;
            geseheneNebenrollen.add(nebenrolle.getName());
        } else {
            nebenrolle = null;
        }

        return nebenrolle;
    }

    public static ArrayList<Spieler> getUnseenBürger(){
        ArrayList<Spieler> bürger = Fraktion.getFraktionsMembers(Bürger.name);
        ArrayList<Spieler> bürgerToRemove = new ArrayList<>();

        if(Rolle.rolleLebend(name)) {
            Nebenrolle orakelSpielerNebenrolle = game.findSpielerPerRolle(name).nebenrolle;
            if (!geseheneNebenrollen.contains(orakelSpielerNebenrolle.getName())) {
                geseheneNebenrollen.add(orakelSpielerNebenrolle.getName());
            }
        }

        for(Spieler currentBürger : bürger) {
            if(geseheneNebenrollen.contains(currentBürger.nebenrolle.getName())) {
                bürgerToRemove.add(currentBürger);
            }
        }

        bürger.removeAll(bürgerToRemove);
        return bürger;
    }
}
