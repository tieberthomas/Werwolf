package root.Persona;

import root.Frontend.FrontendControl;
import root.Persona.Rollen.Bonusrollen.Totengräber;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Spieler;

import java.util.List;
import java.util.stream.Collectors;

public class Rolle extends Persona {
    public Spieler besucht;
    public Spieler besuchtLastNight;
    public int abilityCharges = 1;

    public boolean spammable = false;
    public int numberOfPossibleInstances = 1;

    public static List<String> getMitteHauptrollenNames() {
        return game.mitteHauptrollen.stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public static Rolle findRolle(String rolleID) { //TODO move to Game
        Rolle wantedRolle;

        wantedRolle = game.findHauptrolle(rolleID);
        if (wantedRolle != null)
            return wantedRolle;

        wantedRolle = game.findBonusrolle(rolleID);
        if (wantedRolle != null)
            return wantedRolle;

        return null;
    }

    public static Rolle findRollePerName(String wantedName) { //TODO move to Game
        Rolle wantedRolle;

        wantedRolle = game.findHauptrollePerName(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        wantedRolle = game.findBonusrollePerName(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        return null;
    }

    public static int numberOfOccurencesOfRolleInGame(Rolle rolle) {
        Hauptrolle wantedHauptrolle = game.findHauptrolle(rolle.id);
        if (wantedHauptrolle != null)
            return game.numberOfOccurencesOfHauptrolleInGame(wantedHauptrolle);

        Bonusrolle wantedBonusrolle = game.findBonusrolle(rolle.id);
        if (wantedBonusrolle != null)
            return game.numberOfOccurencesOfBonusrolleInGame(wantedBonusrolle);

        return 0;
    }

    public static boolean hauptRolleContainedInNight(String rolleID) {
        if (game.getHauptrolleInGameIDs().contains(rolleID)) {
            for (Rolle currentRolle : game.mitteHauptrollen) {
                if (currentRolle.equals(rolleID)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean rolleContainedInNight(String rolleID) {
        if (game.getHauptrolleInGameIDs().contains(rolleID) || game.getBonusrolleInGameIDs().contains(rolleID)) {
            if (rolleLebend(rolleID)) {
                return true;
            }

            for (Rolle currentRolle : game.mitteHauptrollen) {
                if (currentRolle.equals(rolleID)) {
                    return false;
                }
            }

            for (Rolle currentRolle : game.mitteBonusrollen) {
                if (!hauptRolleContainedInNight(Sammler.ID) || currentRolle.equals(Totengräber.ID)) {
                    if (currentRolle.equals(rolleID)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean rolleLebend(String rolleID) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.equals(rolleID) && currentSpieler.lebend) {
                return true;
            }
            if (currentSpieler.bonusrolle.equals(rolleID) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean rolleAktiv(String rolleID) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.equals(rolleID) && currentSpieler.aktiv) {
                return true;
            }
            if (currentSpieler.bonusrolle.equals(rolleID) && currentSpieler.aktiv) {
                return true;
            }
        }

        return false;
    }

    public static boolean rolleAufgebraucht(String rolleID) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.equals(rolleID) && currentSpieler.hauptrolle.abilityCharges > 0) {
                return false;
            }
            if (currentSpieler.bonusrolle.equals(rolleID) && currentSpieler.bonusrolle.abilityCharges > 0) {
                return false;
            }
        }

        return true;
    }

    public FrontendControl processChosenOptionsGetInfo(String chosenOption1, String chosenOption2) {
        return new FrontendControl();
    }
}
