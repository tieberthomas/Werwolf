package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;

/**
 * Created by Steve on 12.11.2017.
 */
public class Konditor extends Nebenrolle
{
    public static final String GUT = "Gut";
    public static final String SCHLECHT = "Schlecht";

    public static final String name = "Konditor";
    public static final String imagePath = ResourcePath.KONDITOR_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    @Override
    public String aktion(String chosenOption) {
        return chosenOption;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_WITHOUT_SUGGESTIONS;
        frontendControl.content.add(GUT);
        frontendControl.content.add(SCHLECHT);

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
