package root.Logic.Persona.Rollen.Bonusrollen;

import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Constants.DropdownConstants;
import root.Logic.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Logic.Persona.Rollen.SchnüfflerInformationGenerator;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Schnüffler extends Bonusrolle {
    public static final String ID = "ID_Schnüffler";
    public static final String NAME = "Schnüffler";
    public static final String IMAGE_PATH = ImagePath.SCHNÜFFLER_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Schnüffler erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static int MAX_ANZAHL_AN_INFORMATIONEN = 4;

    public List<SchnüfflerInformation> informationen = new ArrayList<>(); //TODO wenn dieb schnüffler nimmt dann neu anlegen

    public Schnüffler() {
        this.id = ID;
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
        return new FrontendControl(generateDropdownOptions());
    }

    private DropdownOptions generateDropdownOptions() {
        List<String> spielerStrings = Game.game.spieler.stream()
                .filter(spieler -> spieler.lebend)
                .map(spieler -> spieler.name)
                .collect(Collectors.toList());

        for (SchnüfflerInformation information : informationen) {
            spielerStrings.remove(information.spielerName);
        }

        return new DropdownOptions(spielerStrings, DropdownConstants.EMPTY);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);

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
