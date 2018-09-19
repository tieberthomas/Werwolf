package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

public class Riese extends Hauptrolle {
    public static String title = "Mitspieler töten";
    public static final String beschreibung = "Riese erwacht und entscheidet sich ob einen Mitspieler töten möchte";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Riese";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.RIESE_KARTE;
    public static boolean spammable = true;
    public static boolean killing = true;

    public Riese() {
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

            Spieler täter = game.findSpielerPerRolle(NAME);
            Opfer.addVictim(chosenPlayer, täter);

            abilityCharges--;
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