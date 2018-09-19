package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;

import static root.Persona.Rollen.Constants.DropdownConstants.GUT;
import static root.Persona.Rollen.Constants.DropdownConstants.SCHLECHT;

public class Konditor extends Nebenrolle {
    public static String title = "Torte";
    public static final String beschreibung = "Konditor erwacht und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static StatementType statementType = StatementType.ROLLE_SPECAL;
    public static final String NAME = "Konditor";
    public static final String IMAGE_PATH = ImagePath.KONDITOR_KARTE;
    public static boolean spammable = true;

    public Konditor() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = new ArrayList<>();
        frontendControl.addString(GUT);
        frontendControl.addString(SCHLECHT);

        return frontendControl;
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
    public boolean isSpammable() {
        return spammable;
    }
}
