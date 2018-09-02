package root.Persona.Fraktionen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
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
    public static final String imagePath = ImagePath.VAMPIERE_ICON;

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            Spieler täter = Fraktion.getFraktionsMembers(name).get(0);
            Opfer.addVictim(chosenPlayer, täter, true);
        }
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerFrontendControl();
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
