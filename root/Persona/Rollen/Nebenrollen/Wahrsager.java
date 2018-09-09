package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.Objects;

public class Wahrsager extends Nebenrolle {
    public static final String KEIN_OPFER = "Kein Opfer";

    public static final String WAHRSAGER_INFORMATION = "Wahrsager erwacht, bekommt ggf. die Anzahl der Spieler in jeder Fraktion mitgeteilt und schätzt, welche Frktion das Opfer der Dorfabstimmung haben wird";
    public static String title = "Fraktion wählen";
    public static final String REWARD_TITLE = "Anzahl Mitglieder";
    public static final String beschreibung = WAHRSAGER_INFORMATION;
    public static StatementType statementType = StatementType.ROLLE_SPECAL;

    public static final String name = "Wahrsager";
    public static final String imagePath = ImagePath.WAHRSAGER_KARTE;
    public static boolean spammable = false;
    public static Fraktion opferFraktion = null;
    public Fraktion tipp = null;

    @Override
    public FrontendControl getDropdownOptions() {
        ArrayList<String> strings = Fraktion.getFraktionOrNoneStrings();

        strings.remove("");
        strings.add(KEIN_OPFER);
        FrontendControlType typeOfContent = FrontendControlType.DROPDOWN_SEPERATED_LIST;

        return new FrontendControl(typeOfContent, strings);
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

    public boolean guessedRight() {
        return (tipp == null && Wahrsager.opferFraktion == null) ||
                ((tipp != null && Wahrsager.opferFraktion != null) &&
                        Objects.equals(tipp.getName(), Wahrsager.opferFraktion.getName()));
    }
}
