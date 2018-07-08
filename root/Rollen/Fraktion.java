package root.Rollen;

import root.Frontend.Frame.MyFrame;
import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

public class Fraktion {

    public FrontendControl getDropdownOptions() {
        return new FrontendControl();
    }

    public void processChosenOption(String chosenOption) { }

    public String getName() {
        return "";
    }

    public Color getFarbe() {
        return MyFrame.DEFAULT_BUTTON_COLOR;
    }

    public String getImagePath() {
        return ResourcePath.DEAKTIVIERT;
    }

    public ArrayList<Spieler> getFraktionsMembers() {
        ArrayList<Spieler> fraktionsMembers = new ArrayList<>();

        for(Spieler currentSpieler : Spieler.spieler) {
            if(currentSpieler.lebend && !Opfer.isOpfer(currentSpieler.name) && currentSpieler.hauptrolle.getFraktion().getName().equals(this.getName())) {
                fraktionsMembers.add(currentSpieler);
            }
        }

        return fraktionsMembers;
    }

    public int getNumberOfFraktionsMembersInGame() {
        int numberOfFraktionsMembersInGame = 0;

        for(Hauptrolle currentHautprolle : Hauptrolle.mainRolesInGame) {
            if(currentHautprolle.getFraktion().getName().equals(this.getName())) {
                numberOfFraktionsMembersInGame++;
            }
        }

        return numberOfFraktionsMembersInGame;
    }

    public ArrayList<String> getFraktionsMemberStrings() {
        ArrayList<String> fraktionsMembers = new ArrayList<>();

        for(Spieler currentSpieler : Spieler.spieler) {
            if(currentSpieler.lebend && currentSpieler.hauptrolle.getFraktion().getName().equals(this.getName())) {
                fraktionsMembers.add(currentSpieler.name);
            }
        }

        return fraktionsMembers;
    }

    public FrontendControl getFraktionsMemberOrNonFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_LIST;
        frontendControl.strings = getFraktionsMemberStrings();
        frontendControl.strings.add("");
        if (!rolle.isSpammable() && rolle.besuchtLetzteNacht != null) {
            frontendControl.strings.remove(rolle.besuchtLetzteNacht.name);
        }

        return frontendControl;
    }

    public static boolean fraktionExists(String fraktion) {
        for (Spieler currentSpieler : Spieler.spieler) {
            if (currentSpieler.hauptrolle.getFraktion().getName().equals(fraktion)) {
                return true;
            }
        }

        return false;
    }

    public static boolean fraktionInNachtEnthalten(String fraktion) {
        if(getFraktionStrings().contains(fraktion)) {
            return !fraktionOffenkundigTot(fraktion);
        } else {
            return false;
        }
    }

    public static boolean fraktionLebend(String fraktion) {
        for (Spieler currentSpieler : Spieler.spieler) {
            if (currentSpieler.hauptrolle.getFraktion().getName().equals(fraktion) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean fraktionOffenkundigTot(String fraktion) {
        if (fraktion.equals(Schattenpriester_Fraktion.name)) {
            return false;
        }
        if (fraktion.equals(Werwölfe.name) && Hauptrolle.getMainRoleInGameNames().contains(Chemiker.name)) {
            return false;
        }

        int numberMainRolesInGame = 0;
        for (Hauptrolle hauptrolle : Hauptrolle.mainRolesInGame) {
            if (hauptrolle.getFraktion().getName().equals(fraktion)) {
                numberMainRolesInGame++;
            }
        }

        int numberMitteHauptrollen = 0;
        for(Hauptrolle mitteHauptrolle : Rolle.mitteHauptrollen) {
            if(mitteHauptrolle.getFraktion().getName().equals(fraktion)) {
                numberMitteHauptrollen++;
            }
        }

        return numberMitteHauptrollen >= numberMainRolesInGame;
    }

    public static boolean fraktionOpfer(String fraktion) {
        for (Spieler currentSpieler : Spieler.spieler) {
            String hauptrolleSpieler = currentSpieler.hauptrolle.getName();
            String fraktionSpieler = currentSpieler.hauptrolle.getFraktion().getName();

            if (fraktion.equals(Werwölfe.name)) {
                if (fraktionSpieler.equals(fraktion) && Werwölfe.isTötend(hauptrolleSpieler)) {
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
        ArrayList<Spieler> livingPlayers = Spieler.getLivigPlayer();

        for(Opfer opfer : Opfer.deadVictims) {
            livingPlayers.remove(opfer.opfer);
        }

        for (Spieler currentSpieler : livingPlayers) {
            String hauptrolleSpieler = currentSpieler.hauptrolle.getName();
            String fraktionSpieler = currentSpieler.hauptrolle.getFraktion().getName();

            if (fraktion.equals(Werwölfe.name)) {
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

        for (Spieler currentSpieler : Spieler.spieler) {
            if (currentSpieler.lebend) {
                String currentSpielerFraktion = currentSpieler.hauptrolle.getFraktion().getName();

                if(!allFraktionen.contains(currentSpielerFraktion)){
                    allFraktionen.add(currentSpielerFraktion);
                }
            }
        }

        return allFraktionen;
    }

    public static ArrayList<String> getFraktionStrings() {
        ArrayList<String> allFraktionen = new ArrayList<>();

        for(Hauptrolle hauptrolle : Hauptrolle.mainRolesInGame) {
            String currentFratkion = hauptrolle.getFraktion().getName();
            if(!allFraktionen.contains(currentFratkion)) {
                allFraktionen.add(currentFratkion);
            }
        }

        return allFraktionen;
    }

    public static ArrayList<String> getLivingFraktionOrNoneStrings(){
        ArrayList<String> allFraktionen = getLivingFraktionStrings();
        allFraktionen.add("");

        return allFraktionen;
    }

    public static ArrayList<String> getFraktionOrNoneStrings(){
        ArrayList<String> allFraktionen = getFraktionStrings();
        allFraktionen.add("");

        return allFraktionen;
    }

    public static FrontendControl getLivigFraktionOrNoneFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_LIST;
        frontendControl.strings = getLivingFraktionOrNoneStrings();

        return frontendControl;
    }

    public static FrontendControl getFraktionOrNoneFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_LIST;
        frontendControl.strings = getFraktionOrNoneStrings();

        return frontendControl;
    }

    public static Fraktion findFraktion(String searchedFraktion) {
        for(Hauptrolle currentHautprolle : Hauptrolle.mainRoles) {
            if(currentHautprolle.getFraktion().getName().equals(searchedFraktion)) {
                return currentHautprolle.getFraktion();
            }
        }

        return null;
    }
}
