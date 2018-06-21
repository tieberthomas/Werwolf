package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Nebenrolle;

/**
 * Created by Steve on 12.11.2017.
 */
public class Wahrsager extends Nebenrolle
{
    public static final String name = "Wahrsager";
    public static final String imagePath = ResourcePath.WAHRSAGER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public Fraktion tipp = null;
    public static Fraktion opferFraktion = null;
    public static boolean allowedToTakeGuesses = false;

    public boolean guessedRight() {
        if((tipp == null && Wahrsager.opferFraktion == null) ||
                (tipp!=null && Wahrsager.opferFraktion != null) &&
                        tipp.getName().equals(Wahrsager.opferFraktion.getName()))
        {
            return true;
        } else {
            return false;
        }
    }

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
