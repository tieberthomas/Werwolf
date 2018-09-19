package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Schamanin extends Hauptrolle {
    public static String title = "Mitspieler schützen";
    public static final String beschreibung = "Schamanin erwacht und entscheidet ob sie einen Spieler schützen möchte";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Schamanin";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.SCHAMANIN_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;

    public Schamanin() {
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
            chosenPlayer.geschützt = true;
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