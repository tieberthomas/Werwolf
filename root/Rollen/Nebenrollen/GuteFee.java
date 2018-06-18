package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Rollen.RoleType;
import root.Spieler;

/**
 * Created by Steve on 06.12.2017.
 */
public class GuteFee extends Nebenrolle
{
    public static final String name = "Gute Fee";
    public static int roleType = RoleType.SPECIAL;
    public static final String imagePath = ResourcePath.GUTE_FEE_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer != null) {
            besucht = chosenPlayer;
        }

        return chosenOption;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getMitspielerFrontendControl(this);
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
