package root.Rollen.Fraktionen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;

/**
 * Created by Steve on 25.11.2017.
 */
public class Vampire extends Fraktion
{
    public static final String name = "Vampire";
    public static final Color farbe = Color.red;
    public static final String imagePath = ResourcePath.VAMPIERE_ICON;

    @Override
    public String processChosenOption(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            Spieler täter = this.getFraktionsMembers().get(0);
            Opfer.addVictim(chosenPlayer, täter, true);
        }

        return chosenOption;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return Spieler.getPlayerFrontendControl();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getFarbe() { return farbe; }

    @Override
    public String getImagePath() {
        return imagePath;
    }
}
