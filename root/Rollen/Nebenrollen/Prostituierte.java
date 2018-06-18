package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Rollen.RoleType;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Prostituierte extends Nebenrolle
{
    public static final String name = "Prostituierte";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static final String imagePath = ResourcePath.PROSTITUIERTE_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;

    public static Spieler host;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer!=null && !chosenPlayer.equals(Spieler.findSpielerPerRolle(name))) {
            besucht = chosenPlayer;

            host = chosenPlayer;
        } else {
            host = Spieler.findSpielerPerRolle(name);
        }

        return null;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getLivigPlayerOrNoneFrontendControl(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRoleType() {
        return roleType;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }
}
