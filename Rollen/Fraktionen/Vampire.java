package root.Rollen.Fraktionen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Steve on 25.11.2017.
 */
public class Vampire extends Fraktion
{
    public static final String name = "Vampire";
    public static final Color farbe = Color.red;
    public static final String imagePath = ResourcePath.VAMPIERE_ICON;

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

    public static void kill(String chosenOption)
    {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            Spieler täter = null;
            for (Spieler currentSpieler : Spieler.spieler) {
                if (currentSpieler.hauptrolle.getFraktion().getName().equals(name)) {
                    täter = currentSpieler;
                }
            }
            Opfer.addVictim(chosenPlayer, täter, true);
        }
    }
}
