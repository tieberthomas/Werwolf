package root.Rollen.Hauptrollen.Bürger;

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

    public Nebenrolle generateRandomNebenrolle() {
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

        Nebenrolle nebenrolle;

        if(bürger.size()>0) {
            int randIndex = (int) (Math.random() * bürger.size());

            nebenrolle = bürger.get(randIndex).nebenrolle;
            geseheneNebenrollen.add(nebenrolle.getName());
        } else {
            nebenrolle = null;
        }

        return nebenrolle;
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
}
