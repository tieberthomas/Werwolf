package root.Rollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Hauptrollen.Bürger.Sammler;
import root.Rollen.Nebenrollen.Totengräber;
import root.Spieler;
import root.mechanics.Game;

import java.awt.*;
import java.util.ArrayList;

public class Rolle {
    public static Game game;

    public Spieler besucht;
    public Spieler besuchtLetzteNacht;
    public int abilityCharges = 1;

    public FrontendControl getDropdownOptions() {
        return new FrontendControl();
    }

    public void processChosenOption(String chosenOption) { }

    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        return new FrontendControl();
    }

    public static ArrayList<String> getMitteHauptrollenStrings(){
        ArrayList<String> mitteHauptrollenStrings = new ArrayList<String>();

        for(Hauptrolle hauptrolle : game.mitteHauptrollen) {
            mitteHauptrollenStrings.add(hauptrolle.getName());
        }

        return mitteHauptrollenStrings;
    }

    public FrontendControl getInfo() {
        return new FrontendControl();
    }

    public String getName() {
        return "";
    }

    public String getImagePath() {
        return ResourcePath.DEAKTIVIERT;
    }

    public int getNumberOfPossibleInstances() {
        return 1;
    }

    public boolean isSpammable() {
        return true;
    }

    public Color getFarbe() {
        return Color.WHITE;
    }

    public static Rolle findRolle(String wantedName) {
        Rolle wantedRolle;

        wantedRolle = game.findHauptrolle(wantedName);
        if(wantedRolle!=null)
            return wantedRolle;

        wantedRolle = game.findNebenrolle(wantedName);
        if(wantedRolle!=null)
            return wantedRolle;

        return null;
    }

    public static int numberOfOccurencesOfRoleInGame(Rolle rolle) {
        Hauptrolle wantedHauptRolle = game.findHauptrolle(rolle.getName());
        if(wantedHauptRolle!=null)
            return game.numberOfOccurencesOfMainRoleInGame(wantedHauptRolle);

        Nebenrolle wantedNebenrolleRolle = game.findNebenrolle(rolle.getName());
        if(wantedNebenrolleRolle!=null)
            return game.numberOfOccurencesOfSecondaryRoleInGame(wantedNebenrolleRolle);

        return 0;
    }


    public static boolean hauptRolleInNachtEnthalten(String rolle) {
        if(game.getMainRoleInGameNames().contains(rolle)) {
            for(Rolle currentRolle : game.mitteHauptrollen){
                if(currentRolle.getName().equals(rolle)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean rolleInNachtEnthalten(String rolle) {
        if(game.getMainRoleInGameNames().contains(rolle) || game.getSecondaryRoleInGameNames().contains(rolle)) {
            for(Rolle currentRolle : game.mitteHauptrollen){
                if(currentRolle.getName().equals(rolle)) {
                    return false;
                }
            }

            for (Rolle currentRolle : game.mitteNebenrollen) {
                if(!hauptRolleInNachtEnthalten(Sammler.name) || currentRolle.getName().equals(Totengräber.name)) {
                    if (currentRolle.getName().equals(rolle)) {
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
            if((currentSpieler.hauptrolle.getName().equals(rolle) || currentSpieler.nebenrolle.getName().equals(rolle)) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean rolleAktiv(String rolle) {
        for (Spieler currentSpieler : game.spieler) {
            if(currentSpieler.hauptrolle.getName().equals(rolle) && currentSpieler.aktiv) {
                return true;
            }
            if(currentSpieler.nebenrolle.getName().equals(rolle) && currentSpieler.aktiv) {
                return true;
            }
        }

        return false;
    }
}
