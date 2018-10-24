package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;

import static root.Persona.Rollen.Constants.DropdownConstants.GUT;
import static root.Persona.Rollen.Constants.DropdownConstants.SCHLECHT;

public class Konditor extends Bonusrolle {
    public static final String STATEMENT_IDENTIFIER = "Konditor";
    public static final String STATEMENT_TITLE = "Torte";
    public static final String STATEMENT_BESCHREIBUNG = "Konditor erwacht und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Konditor";
    public static final String IMAGE_PATH = ImagePath.KONDITOR_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public Konditor() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = new ArrayList<>();
        frontendControl.addString(GUT);
        frontendControl.addString(SCHLECHT);

        return frontendControl;
    }
}
