package root.Persona;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Spieler;
import root.mechanics.Opfer;

import java.util.ArrayList;

public class Fraktion extends Persona {
    public Zeigekarte getZeigeKarte() {
        return new BürgerZeigekarte();
    }

    public static ArrayList<Spieler> getFraktionsMembers(String fraktion) {
        ArrayList<Spieler> fraktionsMembers = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.lebend && currentSpieler.hauptrolle.fraktion.name.equals(fraktion)) {
                fraktionsMembers.add(currentSpieler);
            }
        }

        return fraktionsMembers;
    }

    public int getNumberOfFraktionsMembersInGame() {
        int numberOfFraktionsMembersInGame = 0;

        for (Hauptrolle currentHautprolle : game.hauptrollenInGame) {
            if (currentHautprolle.fraktion.name.equals(this.name)) {
                numberOfFraktionsMembersInGame++;
            }
        }

        return numberOfFraktionsMembersInGame;
    }

    public static ArrayList<String> getFraktionsMemberStrings(String fraktion) {
        ArrayList<String> fraktionsMembers = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.lebend && currentSpieler.hauptrolle.fraktion.name.equals(fraktion)) {
                fraktionsMembers.add(currentSpieler.name);
            }
        }

        return fraktionsMembers;
    }

    public FrontendControl getFraktionsMemberOrNonFrontendControl(Hauptrolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = getFraktionsMemberStrings(rolle.fraktion.name);
        frontendControl.dropdownStrings.add("");
        if (!rolle.spammable && rolle.besuchtLastNight != null) {
            frontendControl.dropdownStrings.remove(rolle.besuchtLastNight.name);
        }

        return frontendControl;
    }

    public static boolean fraktionContainedInNight(String fraktion) {
        if (getFraktionStrings().contains(fraktion)) {
            return !fraktionOffenkundigTot(fraktion);
        } else {
            return false;
        }
    }

    public static boolean fraktionLebend(String fraktion) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.fraktion.name.equals(fraktion) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean fraktionOffenkundigTot(String fraktion) {
        if (fraktion.equals(Schattenpriester_Fraktion.NAME)) {
            return false;
        }
        if (fraktion.equals(Werwölfe.NAME) && game.getHauptrolleInGameNames().contains(Chemiker.NAME)) {
            return false;
        }

        int numberHauptrollenInGame = 0;
        for (Hauptrolle hauptrolle : game.hauptrollenInGame) {
            if (hauptrolle.fraktion.name.equals(fraktion)) {
                numberHauptrollenInGame++;
            }
        }

        int numberMitteHauptrollen = 0;
        for (Hauptrolle mitteHauptrolle : game.mitteHauptrollen) {
            if (mitteHauptrolle.fraktion.name.equals(fraktion)) {
                numberMitteHauptrollen++;
            }
        }

        return numberMitteHauptrollen >= numberHauptrollenInGame;
    }

    public static boolean fraktionOpfer(String fraktion) {
        for (Spieler currentSpieler : game.spieler) {
            String fraktionSpieler = currentSpieler.hauptrolle.fraktion.name;

            if (fraktion.equals(Werwölfe.NAME)) {
                if (fraktionSpieler.equals(fraktion) && currentSpieler.hauptrolle.killing) {
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
        ArrayList<Spieler> livingSpieler = game.getLivingSpieler();

        for (Opfer opfer : Opfer.deadOpfer) {
            livingSpieler.remove(opfer.spieler);
        }

        for (Spieler currentSpieler : livingSpieler) {
            String fraktionSpieler = currentSpieler.hauptrolle.fraktion.name;

            if (fraktion.equals(Werwölfe.NAME)) {
                if (fraktionSpieler.equals(fraktion) && currentSpieler.hauptrolle.killing) {
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
                String currentSpielerFraktion = currentSpieler.hauptrolle.fraktion.name;

                if (!allFraktionen.contains(currentSpielerFraktion)) {
                    allFraktionen.add(currentSpielerFraktion);
                }
            }
        }

        return allFraktionen;
    }

    public static ArrayList<String> getFraktionStrings() {
        ArrayList<String> allFraktionen = new ArrayList<>();

        for (Hauptrolle hauptrolle : game.hauptrollenInGame) {
            String currentFratkion = hauptrolle.fraktion.name;
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
        for (Hauptrolle currentHautprolle : game.hauptrollen) {
            if (currentHautprolle.fraktion.name.equals(searchedFraktion)) {
                return currentHautprolle.fraktion;
            }
        }

        return null;
    }
}
