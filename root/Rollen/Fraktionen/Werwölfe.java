package root.Rollen.Fraktionen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Hauptrollen.Werwölfe.Alphawolf;
import root.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Rollen.Hauptrollen.Werwölfe.Werwolf;
import root.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Steve on 25.11.2017.
 */
public class Werwölfe extends Fraktion
{
    public static final String name = "Werwölfe";
    public static final Color farbe = Color.green;
    public static final String imagePath = ResourcePath.WÖLFE_ICON;

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

    public static boolean isTötend (String hauptrolle) {
        ArrayList<String> tötend = new ArrayList<>();

        tötend.add(Blutwolf.name);
        tötend.add(Werwolf.name);
        tötend.add(Alphawolf.name);
        tötend.add(Wölfin.name);

        return tötend.contains(hauptrolle);
    }

    public void kill(String chosenOption)
    {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            Spieler täter = this.getFraktionsMembers().get(0);
            Opfer.addVictim(chosenPlayer, täter, true);
        }
    }
}
