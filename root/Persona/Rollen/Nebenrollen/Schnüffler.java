package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Persona.Rollen.SchnüfflerInformationGenerator;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Schnüffler extends Nebenrolle {
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Schnüffler erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String NAME = "Schnüffler";
    public static final String IMAGE_PATH = ImagePath.SCHNÜFFLER_KARTE;
    public static boolean spammable = true;
    public NebenrollenType type = new Informativ();
    public static int MAX_ANZAHL_AN_INFORMATIONEN = 4;

    public ArrayList<SchnüfflerInformation> informationen = new ArrayList<>(); //TODO wenn dieb schnüffler nimmt dann neu anlegen

    public Schnüffler() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = game.getLivingPlayerOrNoneStrings();

        removePreviousPlayers(frontendControl.dropdownStrings);

        return frontendControl;
    }

    private void removePreviousPlayers(ArrayList<String> allPlayers) {
        for (SchnüfflerInformation information : informationen) {
            allPlayers.remove(information.spielerName);
        }
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            SchnüfflerInformationGenerator informationGenerator = new SchnüfflerInformationGenerator(chosenPlayer);
            SchnüfflerInformation information = informationGenerator.generateInformation();
            informationen.add(information);

            if (informationen.size() > MAX_ANZAHL_AN_INFORMATIONEN) {
                informationen.remove(0);
            }

            String pageTitle = chosenPlayer.name;
            return new FrontendControl(informationen, pageTitle);
        }

        return new FrontendControl();
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }
}
