package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Steve on 12.11.2017.
 */
public class Wahrsager extends Nebenrolle
{
    public static final String KEIN_OPFER = "Kein Opfer";

    public static final String WAHRSAGER_INFORMATION = "Wahrsager erwacht und erfährt die Anzahl der lebenden Mitglieder von jeder Fraktion";
    public static String title = "Anzahl Mitglieder";
    public static final String beschreibung = WAHRSAGER_INFORMATION;
    public static StatementType statementType = StatementType.ROLLE_SPECAL;

    public static final String WAHRSAGER_RATEN = "Wahrsager erwacht und gibt seinen Tipp ab welche Fraktion bei der Dorfabstimmung sterben wird";
    public static String secondTitle = "Fraktion wählen";
    public static final String secondBeschreibung = WAHRSAGER_RATEN;
    public static StatementType secondStatementType = StatementType.ROLLE_CHOOSE_ONE;

    public static final String name = "Wahrsager";
    public static final String imagePath = ImagePath.WAHRSAGER_KARTE;
    public static boolean spammable = false;
    public static Fraktion opferFraktion = null;
    public Fraktion tipp = null;

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl dropDownOptions = Fraktion.getFraktionOrNoneFrontendControl();

        dropDownOptions.strings.remove("");
        dropDownOptions.strings.add(KEIN_OPFER);

        return dropDownOptions;
    }

    @Override
    public FrontendControl getInfo() {
        ArrayList<String> list = new ArrayList<>();

        for (String fraktion : Fraktion.getFraktionStrings()) {
            int anzahl = Fraktion.getFraktionsMembers(fraktion).size();
            list.add(anzahl + " " + fraktion);
        }

        return new FrontendControl(FrontendControlType.LIST, list);
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
    public String getSecondTitle() {
        return secondTitle;
    }

    @Override
    public String getSecondBeschreibung() {
        return secondBeschreibung;
    }

    @Override
    public StatementType getSecondStatementType() {
        return secondStatementType;
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
                (tipp != null && Wahrsager.opferFraktion != null) &&
                        Objects.equals(tipp.getName(), Wahrsager.opferFraktion.getName());
    }
}
