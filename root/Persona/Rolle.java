package root.Persona;

import root.Frontend.FrontendControl;
import root.Persona.Rollen.Bonusrollen.Totengräber;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Spieler;

import java.util.ArrayList;

public class Rolle extends Persona {
    public Spieler besucht;
    public Spieler besuchtLetzteNacht;
    public int abilityCharges = 1;

    public boolean spammable = false;
    public int numberOfPossibleInstances = 1;

    public static ArrayList<String> getMitteHauptrollenStrings() {
        ArrayList<String> mitteHauptrollenStrings = new ArrayList<String>();

        for (Hauptrolle hauptrolle : game.mitteHauptrollen) {
            mitteHauptrollenStrings.add(hauptrolle.name);
        }

        return mitteHauptrollenStrings;
    }

    public static Rolle findRolle(String wantedName) { //TODO move to Game
        Rolle wantedRolle;

        wantedRolle = game.findHauptrolle(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        wantedRolle = game.findBonusrolle(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        return null;
    }

    public static int numberOfOccurencesOfRoleInGame(Rolle rolle) {
        Hauptrolle wantedHauptRolle = game.findHauptrolle(rolle.name);
        if (wantedHauptRolle != null)
            return game.numberOfOccurencesOfHauptrolleInGame(wantedHauptRolle);

        Bonusrolle wantedBonusrolleRolle = game.findBonusrolle(rolle.name);
        if (wantedBonusrolleRolle != null)
            return game.numberOfOccurencesOfSecondaryRoleInGame(wantedBonusrolleRolle);

        return 0;
    }

    public static boolean hauptRolleInNachtEnthalten(String rolle) {
        if (game.getHauptrolleInGameNames().contains(rolle)) {
            for (Rolle currentRolle : game.mitteHauptrollen) {
                if (currentRolle.name.equals(rolle)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean rolleInNachtEnthalten(String rolle) {
        if (game.getHauptrolleInGameNames().contains(rolle) || game.getSecondaryRoleInGameNames().contains(rolle)) {
            for (Rolle currentRolle : game.mitteHauptrollen) {
                if (currentRolle.name.equals(rolle)) {
                    return false;
                }
            }

            for (Rolle currentRolle : game.mitteBonusrollen) {
                if (!hauptRolleInNachtEnthalten(Sammler.NAME) || currentRolle.name.equals(Totengräber.NAME)) {
                    if (currentRolle.name.equals(rolle)) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean rolleLebend(String rolle) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.name.equals(rolle) && currentSpieler.lebend) {
                return true;
            }
            if (currentSpieler.bonusrolle.name.equals(rolle) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean rolleAktiv(String rolle) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.name.equals(rolle) && currentSpieler.aktiv) {
                return true;
            }
            if (currentSpieler.bonusrolle.name.equals(rolle) && currentSpieler.aktiv) {
                return true;
            }
        }

        return false;
    }

    public static boolean rolleAufgebraucht(String rolle) {
        for (Spieler currentSpieler : game.spieler) {
            if (currentSpieler.hauptrolle.name.equals(rolle) && currentSpieler.hauptrolle.abilityCharges > 0) {
                return false;
            }
            if (currentSpieler.bonusrolle.name.equals(rolle) && currentSpieler.bonusrolle.abilityCharges > 0) {
                return false;
            }
        }

        return true;
    }

    public FrontendControl processChosenOptionsGetInfo(String chosenOption1, String chosenOption2) {
        return new FrontendControl();
    }
}
