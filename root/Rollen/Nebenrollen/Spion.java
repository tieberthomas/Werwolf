package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Nebenrolle;
import root.Rollen.NebenrollenTyp;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Spion extends Nebenrolle
{
    public static final String name = "Spion";
    public static final String imagePath = ResourcePath.SPION_KARTE;
    public static boolean spammable = true;
    public NebenrollenTyp type = NebenrollenTyp.INFORMATIV;

    @Override
    public FrontendControl getDropdownOptions() {
        return Fraktion.getFraktionOrNoneFrontendControl();
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Fraktion fraktion = Fraktion.findFraktion(chosenOption);

        if (fraktion != null) {
            int fraktionAnzahl = Fraktion.getFraktionsMembers(fraktion.getName()).size();
            ArrayList<String> list = new ArrayList<>();
            list.add(Integer.toString(fraktionAnzahl));

            return new FrontendControl(FrontendControl.LIST, fraktion.getName(), list);
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
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenTyp getType() { return type; }
}
