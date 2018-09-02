package root.Persona.Rollen.Nebenrollen;

import root.ResourceManagement.ImagePath;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenTyp;

import java.awt.*;

public class Tarnumhang extends Nebenrolle {
    public static final String title = "Tarnumhang";
    public static final String name = "Tarnumhang";
    public static final String imagePath = ImagePath.TARNUMHANG_KARTE;
    public static boolean spammable = false;
    public NebenrollenTyp type = NebenrollenTyp.TARNUMHANG;
    public Color farbe = Color.BLACK;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
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
