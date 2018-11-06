package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.Objects;

public class Wahrsager extends Bonusrolle {
    public static final String ID = "Wahrsager";
    public static final String NAME = "Wahrsager";
    public static final String IMAGE_PATH = ImagePath.WAHRSAGER_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Fraktion wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Wahrsager erwacht, bekommt ggf. die Anzahl der Spieler in jeder Fraktion mitgeteilt und schätzt, welche Fraktion das Opfer der Dorfabstimmung haben wird";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String KEIN_OPFER = "Kein Opfer";
    public static final String REWARD_TITLE = "Anzahl Mitglieder";

    public static Fraktion opferFraktion = null;
    public Fraktion tipp = null;

    public Wahrsager() {
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
        ArrayList<String> dropdownStrings = Fraktion.getFraktionOrNoneStrings();

        dropdownStrings.remove("");
        dropdownStrings.add(KEIN_OPFER);

        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, dropdownStrings);
    }

    @Override
    public FrontendControl getInfo() {
        return new FrontendControl(rewardInformation());
    }

    public ArrayList<String> rewardInformation() {
        ArrayList<String> list = new ArrayList<>();

        for (String fraktion : Fraktion.getFraktionStrings()) {
            int anzahl = Fraktion.getFraktionsMembers(fraktion).size();
            list.add(anzahl + " " + fraktion);
        }

        return list;
    }

    public boolean guessedRight() {
        return (tipp == null && Wahrsager.opferFraktion == null) ||
                ((tipp != null && Wahrsager.opferFraktion != null) &&
                        Objects.equals(tipp.name, Wahrsager.opferFraktion.name));
    }
}
