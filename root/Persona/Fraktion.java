package root.Persona;

import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Phases.NormalNight;
import root.Spieler;
import root.mechanics.KillLogik.Opfer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Fraktion extends Persona {
    public Zeigekarte zeigekarte = new BürgerZeigekarte();

    public static List<Spieler> getFraktionsMembers(String fraktion) {
        List<Spieler> fraktionsMembers = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.lebend && currentSpieler.hauptrolle.fraktion.equals(fraktion)) {
                fraktionsMembers.add(currentSpieler);
            }
        }

        return fraktionsMembers;
    }

    public static List<String> getFraktionsMemberStrings(String fraktion) {
        List<String> fraktionsMembers = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.lebend && currentSpieler.hauptrolle.fraktion.equals(fraktion)) {
                fraktionsMembers.add(currentSpieler.name);
            }
        }

        return fraktionsMembers;
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
            if (currentSpieler.hauptrolle.fraktion.equals(fraktion) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean fraktionOffenkundigTot(String fraktion) {
        if (fraktion.equals(SchattenpriesterFraktion.NAME)) {
            return false;
        }
        if (fraktion.equals(Werwölfe.NAME) && game.getHauptrolleInGameNames().contains(Chemiker.NAME)) {
            return false;
        }

        int numberHauptrollenInGame = 0;
        for (Hauptrolle hauptrolle : game.hauptrollenInGame) {
            if (hauptrolle.fraktion.equals(fraktion)) {
                numberHauptrollenInGame++;
            }
        }

        int numberMitteHauptrollen = 0;
        for (Hauptrolle mitteHauptrolle : game.mitteHauptrollen) {
            if (mitteHauptrolle.fraktion.equals(fraktion)) {
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
        List<Spieler> livingSpieler = game.getLivingSpieler();

        for (Opfer opfer : NormalNight.opfer) {
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

    public static List<Fraktion> getFraktionen() {
        return game.hauptrollenInGame.stream()
                .map(fraktion -> fraktion.id)
                .distinct()
                .map(Fraktion::findFraktion)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<String> getFraktionStrings() {
        return getFraktionen().stream()
                .map(fraktion -> fraktion.name)
                .collect(Collectors.toList());
    }

    public static List<Fraktion> getLivingFraktionen() {
        return game.spieler.stream()
                .filter(spieler -> spieler.lebend)
                .map(spieler -> spieler.hauptrolle.fraktion.id)
                .distinct()
                .map(Fraktion::findFraktion)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static List<String> getLivingFraktionStrings() {
        return getLivingFraktionen().stream()
                .map(fraktion -> fraktion.name)
                .collect(Collectors.toList());
    }

    public static Fraktion findFraktion(String searchedFraktion) {
        for (Hauptrolle currentHautprolle : game.hauptrollen) {
            if (currentHautprolle.fraktion.equals(searchedFraktion)) {
                return currentHautprolle.fraktion;
            }
        }

        return null;
    }
}
