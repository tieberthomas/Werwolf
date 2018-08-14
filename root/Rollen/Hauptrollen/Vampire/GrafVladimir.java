package root.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Hauptrolle;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class GrafVladimir extends Hauptrolle
{
    public static final String name = "Graf Vladimir";
    public static Fraktion fraktion = new Vampire();
    public static final String imagePath = ResourcePath.GRAF_VLADIMIR_KARTE;
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
        if(chosenPlayer!=null) {
            besucht = chosenPlayer;

            unerkennbarerSpieler = chosenPlayer;
        }
    }

    @Override
    public String getName() {
        return name;
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