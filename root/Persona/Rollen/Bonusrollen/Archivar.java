package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Archivar extends Bonusrolle {
    public static final String STATEMENT_IDENTIFIER = "Archivar";
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Archivar erwacht und lässt sich Auskunft über die Bonusrolle eines Mitspielers geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String NAME = "Archivar";
    public static final String IMAGE_PATH = ImagePath.ARCHIVAR_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public Archivar() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        Spieler archivarPlayer = game.findSpielerPerRolle(this.name);

        if (chosenPlayer != null && archivarPlayer != null) {
            besucht = chosenPlayer;

            BonusrollenType chosenPlayerType = chosenPlayer.getBonusrollenTypeInfo(archivarPlayer);

            return new FrontendControl(FrontendControlType.IMAGE, chosenPlayerType.title, chosenPlayerType.imagePath);
        }

        return new FrontendControl();
    }
}
