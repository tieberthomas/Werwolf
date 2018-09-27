package root.Persona;

import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Persona.Rollen.Nebenrollen.Totengräber;
import root.Spieler;

import java.awt.*;
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

    public Color getFarbe() {
        return Color.WHITE;
    }

    public static Rolle findRolle(String wantedName) {
        Rolle wantedRolle;

        wantedRolle = game.findHauptrolle(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        wantedRolle = game.findNebenrolle(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        return null;
    }

    public static int numberOfOccurencesOfRoleInGame(Rolle rolle) {
        Hauptrolle wantedHauptRolle = game.findHauptrolle(rolle.name);
        if (wantedHauptRolle != null)
            return game.numberOfOccurencesOfMainRoleInGame(wantedHauptRolle);

        Nebenrolle wantedNebenrolleRolle = game.findNebenrolle(rolle.name);
        if (wantedNebenrolleRolle != null)
            return game.numberOfOccurencesOfSecondaryRoleInGame(wantedNebenrolleRolle);

        return 0;
    }

    public static boolean hauptRolleInNachtEnthalten(String rolle) {
        if (game.getMainRoleInGameNames().contains(rolle)) {
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
        if (game.getMainRoleInGameNames().contains(rolle) || game.getSecondaryRoleInGameNames().contains(rolle)) {
            for (Rolle currentRolle : game.mitteHauptrollen) {
                if (currentRolle.name.equals(rolle)) {
                    return false;
                }
            }

            for (Rolle currentRolle : game.mitteNebenrollen) {
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
            if ((currentSpieler.hauptrolle.name.equals(rolle) || currentSpieler.nebenrolle.name.equals(rolle)) && currentSpieler.lebend) {
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
            if (currentSpieler.nebenrolle.name.equals(rolle) && currentSpieler.aktiv) {
                return true;
            }
        }

        return false;
    }
}
