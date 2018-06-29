package root.mechanics;

import root.Frontend.FrontendControl;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrollen.Bürger.Riese;
import root.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Rollen.Nebenrollen.*;
import root.Rollen.Rolle;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 27.12.2017.
 */
public class Opfer {
    public static ArrayList<Opfer> possibleVictims = new ArrayList<>();
    public static ArrayList<Opfer> deadVictims = new ArrayList<>();

    public Spieler opfer;
    public Spieler täter;
    public boolean fraktionsTäter;

    public Opfer(Spieler opfer, Spieler täter, boolean fraktionsTäter) {
        this.opfer = opfer;
        this.täter = täter;
        this.fraktionsTäter = fraktionsTäter;
    }

    public static ArrayList<String> getOpferOrNonStrings() {
        ArrayList<String> toteOrNon = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            if(!toteOrNon.contains(currentOpfer.opfer.name))
                toteOrNon.add(currentOpfer.opfer.name);
        }

        toteOrNon.add("");

        return toteOrNon;
    }


    public static FrontendControl getOpferOrNonFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_WITH_SUGGESTIONS;
        frontendControl.content = getOpferOrNonStrings();

        return frontendControl;
    }

    public static ArrayList<Spieler> getErweckbareOpfer() {
        ArrayList<Spieler> erweckbareOrNon = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            if (currentOpfer.opfer.ressurectable) {
                if(!erweckbareOrNon.contains(currentOpfer.opfer))
                    erweckbareOrNon.add(currentOpfer.opfer);
            }
        }

        return erweckbareOrNon;
    }

    public static ArrayList<String> getErweckbareStringsOrNon() {
        ArrayList<Spieler> erweckbareOpfer = getErweckbareOpfer();
        ArrayList<String> erweckbareStrings = new ArrayList<>();

        for (Spieler currentOpfer : erweckbareOpfer) {
            erweckbareStrings.add(currentOpfer.name);
        }

        erweckbareStrings.add("");

        return erweckbareStrings;
    }

    public static void addVictim(Spieler opfer, Spieler täter, boolean fraktionsTäter) {
        possibleVictims.add(new Opfer(opfer, täter, fraktionsTäter));

        String opferNebenrolle = opfer.nebenrolle.getName();
        String täterFraktion = täter.hauptrolle.getFraktion().getName();

        if(täter.hauptrolle.getName().equals(Riese.name)) {
            addDeadVictim(opfer, täter, fraktionsTäter, true);
        }
        else {
            boolean blutwolfDeadly = false;
            if(fraktionsTäter && täterFraktion.equals(Werwölfe.name)) {
                if(Rolle.rolleLebend(Blutwolf.name) && Blutwolf.deadly) {
                    blutwolfDeadly = true;
                }
            }

            if (!opfer.geschützt || blutwolfDeadly) {
                if (!(opferNebenrolle.equals(Vampirumhang.name) && täterFraktion.equals(Vampire.name) ||
                        opferNebenrolle.equals(Wolfspelz.name) && täterFraktion.equals(Werwölfe.name) && !blutwolfDeadly)) {
                    addDeadVictim(opfer, täter, fraktionsTäter, false);
                }
            }
        }
    }

    public static void addDeadVictim(Spieler opfer, Spieler täter, boolean fraktionsTäter, boolean riese) {
        Spieler prostituierteSpieler = Spieler.findSpielerPerRolle(Prostituierte.name);
        String hostProstituierte = "";
        if(prostituierteSpieler!=null) {
            hostProstituierte = Prostituierte.host.name;
        }

        if(riese) {
            opfer.ressurectable = false;
        }
        String opferNebenrolle = opfer.nebenrolle.getName();
        if (!opferNebenrolle.equals(Prostituierte.name) || riese) {
            deadVictims.add(new Opfer(opfer, täter, fraktionsTäter));
        }

        if(prostituierteSpieler!=null) {
            if (opfer.name.equals(hostProstituierte)) {
                if (!prostituierteSpieler.geschützt || riese) {
                    deadVictims.add(new Opfer(prostituierteSpieler, täter, fraktionsTäter));
                }
            }
        }
    }

    public static Opfer findOpfer(String name) {
        for (Opfer currentOpfer : deadVictims) {
            if (currentOpfer.opfer.name.equals(name)) {
                return currentOpfer;
            }
        }

        return null;
    }

    public static boolean isOpfer(String name) {
        for (Opfer currentOpfer : deadVictims) {
            if(currentOpfer.opfer.name.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isOpferPerRolle(String name) {
        for (Opfer currentOpfer : deadVictims) {
            if(currentOpfer.opfer.hauptrolle.getName().equals(name) || currentOpfer.opfer.nebenrolle.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
