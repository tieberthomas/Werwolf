package root.Persona.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class GrafVladimir extends Hauptrolle {
    public static String title = "Spieler unerkennbar machen";
    public static final String beschreibung = "Graf Vladimir erwacht und veerschleiert die Identität eines Spielers";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Graf Vladimir";
    public static Fraktion fraktion = new Vampire();
    public static final String IMAGE_PATH = ImagePath.GRAF_VLADIMIR_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;
    public static Spieler unerkennbarerSpieler;

    public GrafVladimir() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            unerkennbarerSpieler = chosenPlayer;
        }
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
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }
}