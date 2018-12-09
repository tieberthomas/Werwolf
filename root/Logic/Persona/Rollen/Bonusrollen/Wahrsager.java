package root.Logic.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Wahrsager extends Bonusrolle {
    public static final String ID = "ID_Wahrsager";
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
        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, new DropdownOptions(Fraktion.getFraktionStrings(), KEIN_OPFER));
    }

    @Override
    public FrontendControl getInfo() {
        return new FrontendControl(statementTitle, new DropdownOptions(Fraktion.getFraktionStrings(), KEIN_OPFER), rewardInformation());
    }

    public List<String> rewardInformation() {
        List<String> list = new ArrayList<>();

        List<Fraktion> fraktionen = Fraktion.getFraktionen();
        fraktionen.sort(Comparator.comparing(fraktion2 -> fraktion2.name));

        int i = 1;

        for (Fraktion fraktion : fraktionen) {
            int anzahl = Fraktion.getFraktionsMembers(fraktion.id).size();
            list.add(getRomanNumber(i) + "  " + fraktion.name + " " + anzahl);
            i++;
        }
        list.add(getRomanNumber(i) + "  " + KEIN_OPFER);

        return list;
    }

    public String getRomanNumber(int integer) {
        switch (integer) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            default:
                return "";
        }
    }

    public boolean guessedRight() {
        return (tipp == null && Wahrsager.opferFraktion == null) ||
                ((tipp != null && Wahrsager.opferFraktion != null) &&
                        tipp.equals(Wahrsager.opferFraktion));
    }
}
