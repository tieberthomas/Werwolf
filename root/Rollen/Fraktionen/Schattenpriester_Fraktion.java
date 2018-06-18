package root.Rollen.Fraktionen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Nebenrollen.Schattenkutte;
import root.mechanics.Opfer;

import java.awt.*;

/**
 * Created by Steve on 25.11.2017.
 */
public class Schattenpriester_Fraktion extends Fraktion
{
    public static final String name = "Schattenpriester";
    public static final Color farbe = Color.lightGray;
    public static final String imagePath = ResourcePath.SCHATTENPRIESTER_ICON;

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

    public static void wiederbeleben(Opfer opfer)
    {
        Opfer.deadVictims.remove(opfer);

        if (!opfer.opfer.nebenrolle.getName().equals(Schattenkutte.name)) {
            opfer.opfer.hauptrolle = new Schattenpriester();
            ((Schattenpriester)opfer.opfer.hauptrolle).neuster = true;
        }
        opfer.opfer.nebenrolle = new Schatten();
    }
}
