package root.Rollen.Nebenrollen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Nebenrolle;

import java.awt.*;

/**
 * Created by Steve on 12.11.2017.
 */
public class Schattenkutte extends Nebenrolle
{
    public static final String name = "Schattenkutte";
    public static final String imagePath = ResourcePath.SCHATTENKUTTE_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public String type = Nebenrolle.PASSIV;
    public Color farbe = Schattenpriester_Fraktion.farbe;

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

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
