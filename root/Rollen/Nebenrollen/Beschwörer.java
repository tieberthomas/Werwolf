package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Beschwörer extends Nebenrolle
{
    public static final String name = "Beschwörer";
    public static final String imagePath = ResourcePath.BESCHWÖRER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;

    @Override
    public FrontendControl getDropdownOptions() {
        return Spieler.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer != null) {
            besucht = chosenPlayer;
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
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }
}
