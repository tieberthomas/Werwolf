package root.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.ResourceManagement.ImagePath;
import root.Rollen.Nebenrolle;

import java.util.ArrayList;

import static root.Rollen.Constants.DropdownConstants.GUT;
import static root.Rollen.Constants.DropdownConstants.SCHLECHT;

public class Konditorlehrling extends Nebenrolle {
    public static final String name = "Konditorlehrling";
    public static final String imagePath = ImagePath.KONDITORLEHRLING_KARTE;
    public static boolean spammable = true;

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.strings = new ArrayList<>();
        frontendControl.addString(GUT);
        frontendControl.addString(SCHLECHT);

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
    public boolean isSpammable() {
        return spammable;
    }
}
