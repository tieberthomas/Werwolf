package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Persona.Rollen.SchnüfflerInformationGenerator;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;
import java.util.List;

public class Schnüffler extends Bonusrolle {
    public static final String STATEMENT_ID = "Schnüffler";
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Schnüffler erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String NAME = "Schnüffler";
    public static final String IMAGE_PATH = ImagePath.SCHNÜFFLER_KARTE;
    public static final BonusrollenType TYPE = new Informativ();
    public static int MAX_ANZAHL_AN_INFORMATIONEN = 4;

    public ArrayList<SchnüfflerInformation> informationen = new ArrayList<>(); //TODO wenn dieb schnüffler nimmt dann neu anlegen

    public Schnüffler() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = false;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = game.getLivingSpielerOrNoneStrings();

        removePreviousSpieler(frontendControl.dropdownStrings);

        return frontendControl;
    }

    private void removePreviousSpieler(List<String> spieler) {
        for (SchnüfflerInformation information : informationen) {
            spieler.remove(information.spielerName);
        }
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            SchnüfflerInformationGenerator informationGenerator = new SchnüfflerInformationGenerator(chosenSpieler);
            SchnüfflerInformation information = informationGenerator.generateInformation();
            informationen.add(information);

            if (informationen.size() > MAX_ANZAHL_AN_INFORMATIONEN) {
                informationen.remove(0);
            }

            String pageTitle = chosenSpieler.name;
            return new FrontendControl(informationen, pageTitle);
        }

        return new FrontendControl();
    }
}
