package root;

import root.Frontend.FrontendControl;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Hauptrollen.Bürger.Sammler;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Rollen.Nebenrolle;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Rolle;
import root.mechanics.Liebespaar;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Spieler
{
    public String name;
    public Hauptrolle hauptrolle;
    public Nebenrolle nebenrolle;
    public boolean lebend;
    public boolean aktiv;
    public boolean geschützt;
    public boolean ressurectable;

    public static ArrayList<Spieler> spieler = new ArrayList<>();
    public static Liebespaar liebespaar;

    public Spieler(String name)
    {
        this.name = name;
        this.lebend = true;
        this.aktiv = true;
        this.geschützt = false;
        this.ressurectable = true;
    }

    public void printSpieler() {
        System.out.println(this.name + " " + this.hauptrolle.getName() + " " + this.nebenrolle.getName());
    }

    public static void printAllSpieler() {
        for (Spieler currentSpieler: spieler)
        {
            printSpace(currentSpieler.name, 15);
            printSpace(currentSpieler.hauptrolle.getName(), 15);
            printSpace(currentSpieler.nebenrolle.getName(), 15);
            System.out.print("Lebend: " + currentSpieler.lebend + " ");
            System.out.print("Aktiv: " + currentSpieler.aktiv + " ");
            System.out.print("Geschützt: " + currentSpieler.geschützt + " ");
            System.out.print("Ressurectable: " + currentSpieler.ressurectable + " ");
            System.out.print("\n");
        }

        System.out.print("\n");
    }

    public static void printSpace(String string, int space) {
        System.out.print(string);
        for (int i=0; i<space-string.length(); i++) {
            System.out.print(" ");
        }
    }

    public static ArrayList<Spieler> getLivigPlayer() {
        ArrayList<Spieler> allSpieler = new ArrayList<>();

        for(Spieler currentSpieler : spieler) {
            if(currentSpieler.lebend) {
                allSpieler.add(currentSpieler);
            }
        }

        return allSpieler;
    }

    public static ArrayList<String> getLivigPlayerStrings() {
        ArrayList<String> allSpieler = new ArrayList<>();

        for(Spieler currentSpieler : spieler) {
            if(currentSpieler.lebend) {
                allSpieler.add(currentSpieler.name);
            }
        }

        return allSpieler;
    }

    public static ArrayList<String> getLivigPlayerOrNoneStrings() {
        ArrayList<String> allSpieler = getLivigPlayerStrings();
        allSpieler.add("");

        return allSpieler;
    }

    public static ArrayList<String> getPlayerCheckSpammableStrings(Rolle rolle) {
        ArrayList<String> allSpieler  = getLivigPlayerOrNoneStrings();
        if (!rolle.isSpammable() && rolle.besuchtLetzteNacht != null) {
            allSpieler.remove(rolle.besuchtLetzteNacht.name);
        }

        return allSpieler;
    }

    public static ArrayList<String> getMitspielerCheckSpammableStrings(Rolle rolle) {
        Spieler spieler = Spieler.findSpielerPerRolle(rolle.getName());

        ArrayList<String> mitspieler = getPlayerCheckSpammableStrings(rolle);
        if(spieler!=null) {
            mitspieler.remove(spieler.name);
        }

        return mitspieler;
    }

    public static FrontendControl getPlayerFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN;
        frontendControl.strings = getLivigPlayerOrNoneStrings();

        return frontendControl;
    }

    public static FrontendControl getPlayerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN;
        frontendControl.strings = getPlayerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public static FrontendControl getMitspielerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN;
        frontendControl.strings = getMitspielerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public static ArrayList<String> getDeadNebenrollen() {
        ArrayList<String> nebenrollenOrNon = new ArrayList<>();

        for(Spieler currentSpieler : Spieler.spieler) {
            String nebenrolleCurrentSpieler = currentSpieler.nebenrolle.getName();

            if(!currentSpieler.lebend) {
                nebenrollenOrNon.add(nebenrolleCurrentSpieler);
            }
        }

        nebenrollenOrNon.add("");

        return nebenrollenOrNon;
    }

    public static ArrayList<String> getDeadHauptrollen() {
        ArrayList<String> hauptrollenOrNon = new ArrayList<>();

        for(Spieler currentSpieler : Spieler.spieler) {
            String hauptrolleCurrentSpieler = currentSpieler.hauptrolle.getName();

            if(!currentSpieler.lebend && !hauptrollenOrNon.contains(hauptrolleCurrentSpieler)) {
                hauptrollenOrNon.add(hauptrolleCurrentSpieler);
            }
        }

        hauptrollenOrNon.add("");

        return hauptrollenOrNon;
    }

    public static boolean spielerExists(String name) {
        return findSpieler(name) != null;
    }

    public static Spieler findSpieler(String name) {
        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public static Spieler findSpielerOrDeadPerRolle(String name) {
        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public static Spieler findSpielerPerRolle(String name) {
        for(Nebenrolle nebenrolle : Rolle.mitteNebenrollen)
        {
            if(nebenrolle.getName().equals(name)) {
                return findSpielerPerRolle(Sammler.name);
            }
        }

        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public static ArrayList<String> findSpielersPerRolle(String name) {
        ArrayList<String> spielers = new ArrayList<>();

        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                spielers.add(currentSpieler.name);
            }
        }

        return spielers;
    }

    public static void killSpieler(Spieler spieler) {
        if(spieler!=null) {
            spieler.lebend = false;
            Rolle.mitteHauptrollen.add(spieler.hauptrolle);
            Rolle.mitteNebenrollen.add(spieler.nebenrolle);
            if(spieler.hauptrolle.getName().equals(Schattenpriester.name) && !spieler.nebenrolle.getName().equals(Schatten.name)) {
                Schattenpriester_Fraktion.deadSchattenPriester++;
            }

            if(Rolle.rolleLebend(Wölfin.name) && Wölfin.modus==Wölfin.WARTEND) {
                if(spieler.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
                    Wölfin.modus = Wölfin.TÖTEND;
                }
            }
        }
    }

    public static String checkVictory() {
        ArrayList<Fraktion> fraktionen = Fraktion.getLivingFraktionen();

        switch(fraktionen.size()){
            case 0:
                return "Tod";
            case 1:
                return fraktionen.get(0).getName();
            case 2:
                if(Spieler.getLivigPlayerStrings().size() == 2) {
                    Spieler spieler1 = Spieler.findSpieler(Spieler.getLivigPlayerStrings().get(0));
                    Spieler spieler2 = Spieler.findSpieler(Spieler.getLivigPlayerStrings().get(1));
                    if((Liebespaar.spieler1 == spieler1 && Liebespaar.spieler2 == spieler2) ||
                            (Liebespaar.spieler1 == spieler2 && Liebespaar.spieler2 == spieler1)) {
                        return "Liebespaar";
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            default:
                return null;
        }
    }
}
