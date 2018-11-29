package root.Persona;

import root.Frontend.FrontendControl;
import root.Persona.Rollen.Bonusrollen.Totengräber;
import root.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Spieler;
import root.mechanics.Game;

import java.util.List;
import java.util.stream.Collectors;

public class Rolle extends Persona {
    public Spieler besucht;
    public Spieler besuchtLastNight;
    public int abilityCharges = 1;

    public boolean spammable = false;
    public int numberOfPossibleInstances = 1;

    public static List<String> getMitteHauptrollenNames() {
        return Game.game.mitteHauptrollen.stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public static Rolle findRolle(String rolleID) { //TODO move to Game
        Rolle wantedRolle;

        wantedRolle = Game.game.findHauptrolle(rolleID);
        if (wantedRolle != null)
            return wantedRolle;

        wantedRolle = Game.game.findBonusrolle(rolleID);
        if (wantedRolle != null)
            return wantedRolle;

        return null;
    }

    public static Rolle findRollePerName(String wantedName) { //TODO move to Game
        Rolle wantedRolle;

        wantedRolle = Game.game.findHauptrollePerName(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        wantedRolle = Game.game.findBonusrollePerName(wantedName);
        if (wantedRolle != null)
            return wantedRolle;

        return null;
    }

    public static int numberOfOccurencesOfRolleInGame(Rolle rolle) {
        Hauptrolle wantedHauptrolle = Game.game.findHauptrolle(rolle.id);
        if (wantedHauptrolle != null)
            return Game.game.numberOfOccurencesOfHauptrolleInGame(wantedHauptrolle);

        Bonusrolle wantedBonusrolle = Game.game.findBonusrolle(rolle.id);
        if (wantedBonusrolle != null)
            return Game.game.numberOfOccurencesOfBonusrolleInGame(wantedBonusrolle);

        return 0;
    }

    public static boolean hauptRolleContainedInNight(String rolleID) {
        if (Game.game.getHauptrolleInGameIDs().contains(rolleID)) {
            for (Rolle currentRolle : Game.game.mitteHauptrollen) {
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
        if (Game.game.getHauptrolleInGameIDs().contains(rolleID) || Game.game.getBonusrolleInGameIDs().contains(rolleID)) {
            if (rolleLebend(rolleID)) {
                return true;
            }

            for (Rolle currentRolle : Game.game.mitteHauptrollen) {
                if (currentRolle.equals(rolleID)) {
                    return false;
                }
            }

            for (Rolle currentRolle : Game.game.mitteBonusrollen) {
                if (!hauptRolleContainedInNight(Sammler.ID) || currentRolle.equals(Totengräber.ID)) {
                    if (currentRolle.equals(rolleID)) {
                        return false;
                    }
                }
            }

            Rolle rolle = Rolle.findRolle(rolleID);
            if(rolle instanceof Hauptrolle) {
                Hauptrolle hauptrolle = (Hauptrolle)rolle;

                if(!Fraktion.fraktionContainedInNight(hauptrolle.fraktion.id)) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean rolleLebend(String rolleID) {
        for (Spieler currentSpieler : Game.game.spieler) {
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
        if(Irrlicht.ID.equals(rolleID)) { //TODO find better solution
            return true;
        }

        for (Spieler currentSpieler : Game.game.spieler) {
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
        for (Spieler currentSpieler : Game.game.spieler) {
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
