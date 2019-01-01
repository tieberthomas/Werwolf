package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.DropdownFrontendObject;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Konditor extends Bonusrolle {
    public static final String GUT = "Gut";
    public static final String SCHLECHT = "Schlecht";

    public static final String ID = "ID_Konditor";
    public static final String NAME = "Konditor";
    public static final String IMAGE_PATH = ImagePath.KONDITOR_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Torte";
    public static final String STATEMENT_BESCHREIBUNG = "Konditor erwacht und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public Konditor() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return new DropdownFrontendObject(getTortenOptions());
    }

    public static DropdownOptions getTortenOptions() {
        List<String> dropdownStrings = new ArrayList<>();
        dropdownStrings.add(GUT);
        dropdownStrings.add(SCHLECHT);

        return new DropdownOptions(dropdownStrings);
    }
}
