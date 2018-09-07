package root.Persona.Rollen.Nebenrollen;

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
import java.util.List;

public class Schnüffler extends Nebenrolle {
    public static String title = "Spieler wählen";
    public static final String beschreibung = "Schnüffler erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String name = "Schnüffler";
    public static final String imagePath = ImagePath.SCHNÜFFLER_KARTE;
    public static boolean spammable = true;
    public NebenrollenType type = new Informativ();
    public static int MAX_ANZAHL_AN_INFORMATIONEN = 4;

    public ArrayList<SchnüfflerInformation> informationen = new ArrayList<>(); //TODO wenn schüffler stirbt wieder neu anlegen

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            SchnüfflerInformationGenerator informationGenerator = new SchnüfflerInformationGenerator(chosenPlayer);
            SchnüfflerInformation information = informationGenerator.generateInformation();
            informationen.add(information);

            List<SchnüfflerInformation> angezeigteInformationen;
            if (informationen.size() <= MAX_ANZAHL_AN_INFORMATIONEN) {
                angezeigteInformationen = (List<SchnüfflerInformation>)informationen.clone();
            } else {
                int lastindex = informationen.size()-1;
                int firstIndex = lastindex-4;
                angezeigteInformationen = informationen.subList(firstIndex, lastindex);
            }
            String pageTitle = chosenPlayer.name;
            return new FrontendControl(angezeigteInformationen, pageTitle);
        }

        return new FrontendControl();
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

    @Override
    public NebenrollenType getType() {
        return type;
    }
}
