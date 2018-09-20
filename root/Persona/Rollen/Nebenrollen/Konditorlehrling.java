package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;

import static root.Persona.Rollen.Constants.DropdownConstants.GUT;
import static root.Persona.Rollen.Constants.DropdownConstants.SCHLECHT;

public class Konditorlehrling extends Nebenrolle {
    public static final String STATEMENT_TITLE = Konditor.STATEMENT_TITLE;
    public static final String STATEMENT_BESCHREIBUNG = "Konditor und Konditorlehrling erwachen und entscheiden sich ob es eine gute oder schlechte Torte gibt";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Konditorlehrling";
    public static final String IMAGE_PATH = ImagePath.KONDITORLEHRLING_KARTE;

    public Konditorlehrling() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
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
}
