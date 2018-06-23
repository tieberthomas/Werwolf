package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Nebenrolle;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Spion extends Nebenrolle
{
    public static final String name = "Spion";
    public static final String imagePath = ResourcePath.SPION_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public String type = Nebenrolle.INFORMATIV;

    @Override
    public FrontendControl getDropdownOtions() {
        return Fraktion.getFraktionOrNoneFrontendControl();
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Fraktion fraktion = Fraktion.findFraktion(chosenOption);

        if (fraktion != null) {
            int fraktionAnzahl = fraktion.getFraktionsMembers().size();
            ArrayList<String> list = new ArrayList<>();
            list.add(Integer.toString(fraktionAnzahl));

            return new FrontendControl(FrontendControl.STATIC_LIST, list, fraktion.getName());
        }

        return new FrontendControl();
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

    @Override
    public String getType() { return type; }
}
