package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;

import java.util.ArrayList;

import static root.Rollen.Constants.DropdownConstants.JA;
import static root.Rollen.Constants.DropdownConstants.NEIN;

public class Wirt extends Hauptrolle {
    public static final String name = "Wirt";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.WIRT_KARTE;
    public static boolean spammable = false;
    public static int freibierCharges = 1;

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.strings = new ArrayList<>();
        frontendControl.addString(JA);
        frontendControl.addString(NEIN);

        return frontendControl;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        if (chosenOption != null) {
            if (chosenOption.equals(JA.name)) {
                freibierCharges--;
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
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