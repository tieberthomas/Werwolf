package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Nachtfürst extends Bonusrolle {
    public static final String ID = "ID_Nachtfürst";
    public static final String NAME = "Nachtfürst";
    public static final String IMAGE_PATH = ImagePath.NACHTFÜRST_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Anzahl der toten Opfer";
    public static final String STATEMENT_BESCHREIBUNG = "Nachtfürst erwacht und schätzt, wieviele Ofer es in der Nacht wird";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String KEIN_OPFER = "Kein Opfer";
    public static final String REWARD_TITLE = "Opfer wählen";

    private Integer tipp = null;
    public boolean guessedRight = false;

    public Nachtfürst() {
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
    public FrontendControl getDropdownOptionsFrontendControl() {
        List<String> numbers = new ArrayList<>();
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5");
        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, new DropdownOptions(numbers, KEIN_OPFER));
    }

    @Override
    public void processChosenOption(String chosenOption) {
        if (chosenOption != null) {
            if (chosenOption.equals(KEIN_OPFER)) {
                tipp = 0;
            } else {
                tipp = Integer.parseInt(chosenOption);
            }
        } else {
            tipp = null;
        }
    }

    public void checkGuess(int anzahlOpferDerNacht) {
        if (tipp == null) {
            guessedRight = false;
        } else {
            guessedRight = (anzahlOpferDerNacht == tipp);
        }

        if(guessedRight) {
            System.out.println("Nachtfürst lag richtig");
        } else {
            System.out.println("Nachtfürst lga falsch");
        }
    }
}
