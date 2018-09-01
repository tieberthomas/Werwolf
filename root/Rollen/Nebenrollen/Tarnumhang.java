package root.Rollen.Nebenrollen;

import root.ResourceManagement.ImagePath;
import root.Rollen.Nebenrolle;
import root.Rollen.NebenrollenTyp;

import java.awt.*;

/**
 * Created by Steve on 12.11.2017.
 */
public class Tarnumhang extends Nebenrolle
{
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
