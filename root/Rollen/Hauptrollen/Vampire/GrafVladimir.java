package root.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Hauptrolle;
import root.Spieler;

public class GrafVladimir extends Hauptrolle {
    public static String title = "Spieler unerkennbar machen";
    public static final String beschreibung = "Graf Vladimir erwacht und macht einen Spieler unerkennbar";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;
    public static final String name = "Graf Vladimir";
    public static Fraktion fraktion = new Vampire();
    public static final String imagePath = ImagePath.GRAF_VLADIMIR_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;
    public static Spieler unerkennbarerSpieler;

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
    public Fraktion getFraktion() {
        return fraktion;
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
    public boolean isKilling() {
        return killing;
    }
}