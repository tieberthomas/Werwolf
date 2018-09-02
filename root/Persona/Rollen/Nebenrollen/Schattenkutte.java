package root.Persona.Rollen.Nebenrollen;

import root.ResourceManagement.ImagePath;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenTyp;

import java.awt.*;

/**
 * Created by Steve on 12.11.2017.
 */
public class Schattenkutte extends Nebenrolle
{
    public static final String name = "Schattenkutte";
    public static final String imagePath = ImagePath.SCHATTENKUTTE_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public NebenrollenTyp type = NebenrollenTyp.PASSIV;
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
    public NebenrollenTyp getType() {
        return type;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
