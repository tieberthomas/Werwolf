package root.mechanics;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rollen.Bonusrollen.Prostituierte;
import root.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Persona.Rollen.Hauptrollen.Bürger.Riese;
import root.Spieler;

import java.util.ArrayList;

public class Opfer {
    public static Game game;

    public static ArrayList<Opfer> possibleOpfer = new ArrayList<>();
    public static ArrayList<Opfer> deadOpfer = new ArrayList<>();

    public Spieler spieler;
    public Spieler täter;
    public Fraktion täterFraktion;
    public boolean fraktionsTäter;

    public Opfer(Spieler spieler, Spieler täter) {
        this.spieler = spieler;
        this.täter = täter;
        this.täterFraktion = täter.hauptrolle.fraktion;
        this.fraktionsTäter = false;
    }

    public Opfer(Spieler spieler, Fraktion fraktion) {
        this.spieler = spieler;
        this.täterFraktion = fraktion;
        this.fraktionsTäter = true;
    }

    public static ArrayList<String> getOpferOrNonStrings() {
        ArrayList<String> toteOrNon = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadOpfer) {
            if (!toteOrNon.contains(currentOpfer.spieler.name))
                toteOrNon.add(currentOpfer.spieler.name);
        }

        toteOrNon.add("");

        return toteOrNon;
    }

    public static FrontendControl getOpferOrNonFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = getOpferOrNonStrings();

        return frontendControl;
    }

    public static ArrayList<Spieler> getErweckbareOpfer() {
        ArrayList<Spieler> erweckbareOrNon = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadOpfer) {
            if (currentOpfer.spieler.ressurectable) {
                if (!erweckbareOrNon.contains(currentOpfer.spieler))
                    erweckbareOrNon.add(currentOpfer.spieler);
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

    public static void addOpfer(Spieler spieler, Spieler täter) {
        possibleOpfer.add(new Opfer(spieler, täter));

        String opferBonusrolle = spieler.bonusrolle.name;
        String täterFraktion = täter.hauptrolle.fraktion.name;

        if (täter.hauptrolle.name.equals(Riese.NAME)) {
            addDeadOpfer(spieler, täter, true);
        } else {
            if (!spieler.geschützt) {
                if (!(opferBonusrolle.equals(Vampirumhang.NAME) && täterFraktion.equals(Vampire.NAME) ||
                        opferBonusrolle.equals(Wolfspelz.NAME) && täterFraktion.equals(Werwölfe.NAME))) {
                    addDeadOpfer(spieler, täter);
                }
            }
        }
    }

    public static void addOpfer(Spieler spieler, Fraktion täterFraktion) {
        possibleOpfer.add(new Opfer(spieler, täterFraktion));

        String opferBonusrolle = spieler.bonusrolle.name;

        if (täterFraktion.equals(Werwölfe.NAME) && Werwölfe.blutWolfIsAktiv()) {
            addDeadOpfer(spieler, täterFraktion);
        } else {
            if (!spieler.geschützt) {
                if (!(opferBonusrolle.equals(Vampirumhang.NAME) && täterFraktion.equals(Vampire.NAME) ||
                        opferBonusrolle.equals(Wolfspelz.NAME) && täterFraktion.equals(Werwölfe.NAME))) {
                    addDeadOpfer(spieler, täterFraktion);
                }
            }
        }
    }

    private static void addDeadOpfer(Spieler spieler, Spieler täter) {
        addDeadOpfer(spieler, täter, false);
    }

    private static void addDeadOpfer(Spieler spieler, Spieler täter, boolean riese) {
        addDeadOpfer(new Opfer(spieler, täter), riese);
    }

    private static void addDeadOpfer(Spieler spieler, Fraktion täter) {
        addDeadOpfer(new Opfer(spieler, täter), false);
    }

    private static void addDeadOpfer(Opfer opfer, boolean riese) {
        Spieler prostituierteSpieler = game.findSpielerPerRolle(Prostituierte.NAME);
        String hostProstituierte = "";
        if (prostituierteSpieler != null) {
            hostProstituierte = Prostituierte.host.name;
        }

        if (riese) {
            opfer.spieler.ressurectable = false;
        }
        if (!opfer.spieler.equals(prostituierteSpieler) || riese) {
            deadOpfer.add(opfer);
        }

        if (prostituierteSpieler != null) {
            if (opfer.spieler.name.equals(hostProstituierte)) {
                if (!prostituierteSpieler.geschützt || riese) {
                    Opfer prostituierteOpferopfer;
                    if (opfer.fraktionsTäter) {
                        prostituierteOpferopfer = new Opfer(prostituierteSpieler, opfer.täterFraktion);
                    } else {
                        prostituierteOpferopfer = new Opfer(prostituierteSpieler, opfer.täter);
                    }
                    deadOpfer.add(prostituierteOpferopfer);
                }
            }
        }
    }

    public static void removeOpfer(Spieler spieler) {
        deadOpfer.removeIf(deadOpfer -> deadOpfer.spieler.equals(spieler));
    }

    public static Opfer findOpfer(String name) {
        for (Opfer currentOpfer : deadOpfer) {
            if (currentOpfer.spieler.name.equals(name)) {
                return currentOpfer;
            }
        }

        return null;
    }

    public static boolean isOpfer(String name) {
        for (Opfer currentOpfer : deadOpfer) {
            if (currentOpfer.spieler.name.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isOpferPerRolle(String name) {
        for (Opfer currentOpfer : deadOpfer) {
            if (currentOpfer.spieler.hauptrolle.name.equals(name) || currentOpfer.spieler.bonusrolle.name.equals(name)) {
                return true;
            }
        }

        return false;
    }
}
