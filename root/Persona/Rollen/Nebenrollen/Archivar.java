package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Archivar extends Nebenrolle {
    public static String title = "Spieler wählen";
    public static final String beschreibung = "Archivar erwacht und lässt sich Auskunft über die Bonusrolle eines Mitspielers geben";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String NAME = "Archivar";
    public static final String IMAGE_PATH = ImagePath.ARCHIVAR_KARTE;
    public static boolean spammable = true;
    public NebenrollenType type = new Informativ();

    public Archivar() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            NebenrollenType chosenPlayerType = chosenPlayer.nebenrolle.getType();

            if (showTarnumhang(this, chosenPlayer)) {
                chosenPlayerType = new Tarnumhang_NebenrollenType();
            }


            return new FrontendControl(FrontendControlType.IMAGE, chosenPlayerType.title, chosenPlayerType.imagePath);
        }

        return new FrontendControl();
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
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }
}
