package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Prostituierte extends Nebenrolle
{
    public static final String name = "Prostituierte";
    public static final String imagePath = ResourcePath.PROSTITUIERTE_KARTE;
    public static boolean spammable = false;

    public static Spieler host;

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if(chosenPlayer!=null && !chosenPlayer.equals(game.findSpielerPerRolle(name))) {
            besucht = chosenPlayer;

            host = chosenPlayer;
        } else {
            host = game.findSpielerPerRolle(name);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }
}
