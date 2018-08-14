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

        wantedRolle = Hauptrolle.findHauptrolle(wantedName);
        if(wantedRolle!=null)
            return wantedRolle;

        wantedRolle = Nebenrolle.findNebenrolle(wantedName);
        if(wantedRolle!=null)
            return wantedRolle;

        return null;
    }

    public static int numberOfOccurencesOfRoleInGame(Rolle rolle) {
        Hauptrolle wantedHauptRolle = Hauptrolle.findHauptrolle(rolle.getName());
        if(wantedHauptRolle!=null)
            return Hauptrolle.numberOfOccurencesOfMainRoleInGame(wantedHauptRolle);

        Nebenrolle wantedNebenrolleRolle = Nebenrolle.findNebenrolle(rolle.getName());
        if(wantedNebenrolleRolle!=null)
            return Nebenrolle.numberOfOccurencesOfSecondaryRoleInGame(wantedNebenrolleRolle);

        return 0;
    }


    public static boolean hauptRolleInNachtEnthalten(String rolle) {
        if(Hauptrolle.getMainRoleInGameNames().contains(rolle)) {
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
        if(Hauptrolle.getMainRoleInGameNames().contains(rolle) || Nebenrolle.getSecondaryRoleInGameNames().contains(rolle)) {
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
