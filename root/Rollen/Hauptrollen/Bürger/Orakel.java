package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.Phases.Statement;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrolle;
import root.Rollen.Rolle;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 15.06.2018.
 */
public class Orakel extends Hauptrolle {
    public static final String name = "Orakel";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.ORAKEL_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    public static ArrayList<String> geseheneNebenrollen = new ArrayList<>();

    @Override
    public FrontendControl getInfo() {
        Nebenrolle randomNebenrolle = generateRandomNebenrolle();

        if (randomNebenrolle != null) {
            return new FrontendControl(FrontendControl.STATIC_CARD, randomNebenrolle.getImagePath());
        } else {
            ArrayList<String> nebenRolleList = (ArrayList<String>)geseheneNebenrollen.clone();
            Nebenrolle orakelSpielerNebenrolle = Spieler.findSpielerPerRolle(name).nebenrolle;
            nebenRolleList.remove(orakelSpielerNebenrolle.getName());
            FrontendControl info = new FrontendControl(FrontendControl.STATIC_LIST, nebenRolleList);
            info.title = Nacht.ORAKEL_VERBRAUCHT_TITLE;
            return info;
        }
    }

    @Override
    public String getName() {
        return name;
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
    public boolean isUnique() {
        return unique;
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
        ArrayList<Spieler> bürger = fraktion.getFraktionsMembers();
        ArrayList<Spieler> bürgerToRemove = new ArrayList<>();

        if(Rolle.rolleLebend(name)) {
            Nebenrolle orakelSpielerNebenrolle = Spieler.findSpielerPerRolle(name).nebenrolle;
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
