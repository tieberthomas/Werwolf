package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;

import java.util.ArrayList;

/**
 * Created by Steve on 21.01.2018.
 */
public class Konditorlehrling extends Nebenrolle
{
    public static final String GUT = "Gut";
    public static final String SCHLECHT = "Schlecht";

    public static final String name = "Konditorlehrling";
    public static final String imagePath = ResourcePath.KONDITORLEHRLING_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN;
        frontendControl.strings = new ArrayList<>();
        frontendControl.strings.add(GUT);
        frontendControl.strings.add(SCHLECHT);

        return frontendControl;
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
