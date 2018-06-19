package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Nebenrolle;
import root.Rollen.RoleType;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Wahrsager extends Nebenrolle
{
    public static final String name = "Wahrsager";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static final String imagePath = ResourcePath.WAHRSAGER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public Fraktion tipp = null;
    public static Fraktion opferFraktion = null;
    public static boolean firstNightOver = false;

    @Override
    public String aktion(String chosenOption) {
        return chosenOption;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Fraktion.getFraktionOrNoneFrontendControl();
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
