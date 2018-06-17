package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Nebenrolle;
import root.Rollen.RoleType;
import root.Spieler;
import root.mechanics.Liebespaar;

/**
 * Created by Steve on 12.11.2017.
 */
public class Spion extends Nebenrolle
{
    public static final String name = "Spion";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static final String imagePath = ResourcePath.SPION_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public String type = Nebenrolle.INFORMATIV;

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

    @Override
    public String getType() { return type; }
}
