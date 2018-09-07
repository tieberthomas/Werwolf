package root.Persona.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Nebenrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

/**
 * Created by Steve on 12.11.2017.
 */
public class Wahrsager extends Nebenrolle
{
    public static final String KEIN_OPFER = "Kein Opfer";

    public static String title = "Fraktion wählen";
    public static final String beschreibung = "Wahrsager erwacht und gibt seinen Tipp ab welche Fraktion bei der Dorfabstimmung sterben wird";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;
    public static final String name = "Wahrsager";
    public static final String imagePath = ImagePath.WAHRSAGER_KARTE;
    public static boolean spammable = false;
    public Fraktion tipp = null;
    public static Fraktion opferFraktion = null;
    public static boolean isGuessing = false;

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl dropDownOptions = Fraktion.getFraktionOrNoneFrontendControl();

        dropDownOptions.strings.remove("");
        dropDownOptions.strings.add(KEIN_OPFER);

        return dropDownOptions;
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
        if((tipp == null && Wahrsager.opferFraktion == null) ||
                (tipp!=null && Wahrsager.opferFraktion != null) &&
                        tipp.getName().equals(Wahrsager.opferFraktion.getName()))
        {
            return true;
        } else {
            return false;
        }
    }
}
