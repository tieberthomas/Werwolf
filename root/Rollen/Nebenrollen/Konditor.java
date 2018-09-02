package root.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Rollen.Nebenrolle;

import java.util.ArrayList;

import static root.Rollen.Constants.DropdownConstants.GUT;
import static root.Rollen.Constants.DropdownConstants.SCHLECHT;

public class Konditor extends Nebenrolle {
    public static String title = "Torte";
    public static final String beschreibung = "Konditor erwacht und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static StatementType statementType = StatementType.ROLLE_SPECAL;
    public static final String name = "Konditor";
    public static final String imagePath = ImagePath.KONDITOR_KARTE;
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
    public String getTitle() {
        return title;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public StatementType getStatementType() {
        return statementType;
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
