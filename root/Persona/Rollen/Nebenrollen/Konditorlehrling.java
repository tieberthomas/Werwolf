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
    public static String title = Konditor.title;
    public static final String beschreibung = "Konditor und Konditorlehrling erwachen und entscheiden sich ob es eine gute oder schlechte Torte gibt";
    public static StatementType statementType = StatementType.ROLLE_SPECAL;
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
