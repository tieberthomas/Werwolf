package root.Persona;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.Frame.MyFrame;
import root.Frontend.FrontendControl;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

public class Fraktion extends Persona {
    public Color getFarbe() {
        return MyFrame.DEFAULT_BUTTON_COLOR;
    }

    public Zeigekarte getZeigeKarte() {
        return new BürgerZeigekarte();
    }

    public static ArrayList<Spieler> getFraktionsMembers(String fraktion) {
        ArrayList<Spieler> fraktionsMembers = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.lebend && currentSpieler.hauptrolle.getFraktion().name.equals(fraktion)) {
                fraktionsMembers.add(currentSpieler);
            }
        }

        return fraktionsMembers;
    }

    public int getNumberOfFraktionsMembersInGame() {
        int numberOfFraktionsMembersInGame = 0;

        for (Hauptrolle currentHautprolle : game.mainRolesInGame) {
            if (currentHautprolle.getFraktion().name.equals(this.name)) {
                numberOfFraktionsMembersInGame++;
            }
        }

        return numberOfFraktionsMembersInGame;
    }

    public static ArrayList<String> getFraktionsMemberStrings(String fraktion) {
        ArrayList<String> fraktionsMembers = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.lebend && currentSpieler.hauptrolle.getFraktion().name.equals(fraktion)) {
                fraktionsMembers.add(currentSpieler.name);
            }
        }

        return fraktionsMembers;
    }

    public FrontendControl getFraktionsMemberOrNonFrontendControl(Hauptrolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = getFraktionsMemberStrings(rolle.getFraktion().name);
        frontendControl.dropdownStrings.add("");
        if (!rolle.spammable && rolle.besuchtLetzteNacht != null) {
            frontendControl.dropdownStrings.remove(rolle.besuchtLetzteNacht.name);
        }

        return frontendControl;
    }

    public static boolean fraktionInNachtEnthalten(String fraktion) {
        if (getFraktionStrings().contains(fraktion)) {
            return !fraktionOffenkundigTot(fraktion);
        } else {
            return false;
        }
    }

    public static boolean fraktionLebend(String fraktion) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.getFraktion().name.equals(fraktion) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean fraktionOffenkundigTot(String fraktion) {
        if (fraktion.equals(Schattenpriester_Fraktion.NAME)) {
            return false;
        }
        if (fraktion.equals(Werwölfe.NAME) && game.getMainRoleInGameNames().contains(Chemiker.NAME)) {
            return false;
        }

        int numberMainRolesInGame = 0;
        for (Hauptrolle hauptrolle : game.mainRolesInGame) {
            if (hauptrolle.getFraktion().name.equals(fraktion)) {
                numberMainRolesInGame++;
            }
        }

        int numberMitteHauptrollen = 0;
        for (Hauptrolle mitteHauptrolle : game.mitteHauptrollen) {
            if (mitteHauptrolle.getFraktion().name.equals(fraktion)) {
                numberMitteHauptrollen++;
            }
        }

        return numberMitteHauptrollen >= numberMainRolesInGame;
    }

    public static boolean fraktionOpfer(String fraktion) {
        for (Spieler currentSpieler : game.spieler) {
            String hauptrolleSpieler = currentSpieler.hauptrolle.name;
            String fraktionSpieler = currentSpieler.hauptrolle.getFraktion().name;

            if (fraktion.equals(Werwölfe.NAME)) {
                if (fraktionSpieler.equals(fraktion) && Werwölfe.isTötend(hauptrolleSpieler)) { //TODO currentSpieler.hauptrolle.isKilling() ?
                    if (currentSpieler.lebend && !Opfer.isOpfer(currentSpieler.name)) {
                        return false;
                    }
                }
            } else {
                if (fraktionSpieler.equals(fraktion)) {
                    if (currentSpieler.lebend && !Opfer.isOpfer(currentSpieler.name)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static boolean fraktionAktiv(String fraktion) {
        ArrayList<Spieler> livingPlayers = game.getLivingPlayer();

        for (Opfer opfer : Opfer.deadVictims) {
            livingPlayers.remove(opfer.opfer);
        }

        for (Spieler currentSpieler : livingPlayers) {
            String hauptrolleSpieler = currentSpieler.hauptrolle.name;
            String fraktionSpieler = currentSpieler.hauptrolle.getFraktion().name;

            if (fraktion.equals(Werwölfe.NAME)) {
                if (fraktionSpieler.equals(fraktion) && Werwölfe.isTötend(hauptrolleSpieler)) {
                    if (currentSpieler.aktiv) {
                        return true;
                    }
                }
            } else {
                if (fraktionSpieler.equals(fraktion)) {
                    if (currentSpieler.aktiv) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static ArrayList<Fraktion> getLivingFraktionen() {
        ArrayList<Fraktion> allFraktionen = new ArrayList<>();

        for (String currentFraktion : getLivingFraktionStrings()) {
            allFraktionen.add(Fraktion.findFraktion(currentFraktion));
        }

        return allFraktionen;
    }

    public static ArrayList<String> getLivingFraktionStrings() {
        ArrayList<String> allFraktionen = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.lebend) {
                String currentSpielerFraktion = currentSpieler.hauptrolle.getFraktion().name;

                if (!allFraktionen.contains(currentSpielerFraktion)) {
                    allFraktionen.add(currentSpielerFraktion);
                }
            }
        }

        return allFraktionen;
    }

    public static ArrayList<String> getFraktionStrings() {
        ArrayList<String> allFraktionen = new ArrayList<>();

        for (Hauptrolle hauptrolle : game.mainRolesInGame) {
            String currentFratkion = hauptrolle.getFraktion().name;
            if (!allFraktionen.contains(currentFratkion)) {
                allFraktionen.add(currentFratkion);
            }
        }

        return allFraktionen;
    }

    public static ArrayList<String> getLivingFraktionOrNoneStrings() {
        ArrayList<String> allFraktionen = getLivingFraktionStrings();
        allFraktionen.add("");

        return allFraktionen;
    }

    public static ArrayList<String> getFraktionOrNoneStrings() {
        ArrayList<String> allFraktionen = getFraktionStrings();
        allFraktionen.add("");

        return allFraktionen;
    }

    public static FrontendControl getLivigFraktionOrNoneFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = getLivingFraktionOrNoneStrings();

        return frontendControl;
    }

    public static FrontendControl getFraktionOrNoneFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = getFraktionOrNoneStrings();

        return frontendControl;
    }

    public static Fraktion findFraktion(String searchedFraktion) {
        for (Hauptrolle currentHautprolle : game.mainRoles) {
            if (currentHautprolle.getFraktion().name.equals(searchedFraktion)) {
                return currentHautprolle.getFraktion();
            }
        }

        return null;
    }
}
