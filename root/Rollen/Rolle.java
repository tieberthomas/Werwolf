package root.Rollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Hauptrollen.Bürger.Sammler;
import root.Spieler;

import java.util.ArrayList;

public class Rolle {
    public static ArrayList<Hauptrolle> mitteHauptrollen = new ArrayList<>();
    public static ArrayList<Nebenrolle> mitteNebenrollen = new ArrayList<>();
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

    public FrontendControl getInfo() {
        return new FrontendControl();
    }

    public String getName() {
        return "";
    }

    public String getImagePath() {
        return ResourcePath.DEAKTIVIERT;
    }

    public boolean isUnique() {
        return true;
    }

    public boolean isSpammable() {
        return true;
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

    public static boolean rolleExists(String rolle) {
        for (Spieler currentSpieler : Spieler.spieler) {
            if(currentSpieler.hauptrolle.getName().equals(rolle) || currentSpieler.nebenrolle.getName().equals(rolle)) {
                return true;
            }
        }

        return false;
    }

    public static boolean hauptRolleInNachtEnthalten(String rolle) {
        if(Hauptrolle.getMainRoleInGameNames().contains(rolle)) {
            for(Rolle currentRolle : Rolle.mitteHauptrollen){
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
            for(Rolle currentRolle : Rolle.mitteHauptrollen){
                if(currentRolle.getName().equals(rolle)) {
                    return false;
                }
            }

            if(!hauptRolleInNachtEnthalten(Sammler.name)) {
                for (Rolle currentRolle : Rolle.mitteNebenrollen) {
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

    public static boolean isSammlerRolle(String rolle) {
        for (Rolle currentRolle : Rolle.mitteNebenrollen) {
            if (currentRolle.getName().equals(rolle)) {
                return true;
            }
        }
        return false;
    }

    public static boolean rolleLebend(String rolle) {
        for (Spieler currentSpieler : Spieler.spieler) {
            if((currentSpieler.hauptrolle.getName().equals(rolle) || currentSpieler.nebenrolle.getName().equals(rolle)) && currentSpieler.lebend) {
                return true;
            }
        }

        return false;
    }

    public static boolean rolleAktiv(String rolle) {
        for (Spieler currentSpieler : Spieler.spieler) {
            //TODO deaktivierungen nochmal genauer ansehen mit Michael
            if(currentSpieler.hauptrolle.getName().equals(rolle) && currentSpieler.hauptrolle.aktiv && currentSpieler.aktiv) {
                return true;
            }
            if(currentSpieler.nebenrolle.getName().equals(rolle) && currentSpieler.aktiv) {
                return true;
            }
        }

        return false;
    }
}
