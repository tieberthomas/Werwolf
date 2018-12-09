package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Wirt extends Hauptrolle {
    public static final String JA = "Ja";
    public static final String NEIN = "Nein";

    public static final String ID = "ID_Wirt";
    public static final String NAME = "Wirt";
    public static final String IMAGE_PATH = ImagePath.WIRT_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Freibier ausgeben";
    public static final String STATEMENT_BESCHREIBUNG = "Wirt erwacht und entscheidet sich, ob er Freibier ausgeben will";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static int freibierCharges = 1;

    public Wirt() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        List<String> dropdownStrings = new ArrayList<>();
        dropdownStrings.add(JA);
        dropdownStrings.add(NEIN);

        return new FrontendControl(new DropdownOptions(dropdownStrings));
    }

    @Override
    public void processChosenOption(String chosenOption) {
        if (chosenOption != null) {
            if (chosenOption.equals(JA)) {
                freibierCharges--;
            }
        }
    }
}