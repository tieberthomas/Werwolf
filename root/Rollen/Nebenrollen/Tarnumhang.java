package root.Rollen.Nebenrollen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;

/**
 * Created by Steve on 12.11.2017.
 */
public class Tarnumhang extends Nebenrolle
{
    public static final String name = "Tarnumhang";
    public static final String imagePath = ResourcePath.TARNUMHANG_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public String type = Nebenrolle.TARNUMHANG;

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

    public String getType() {
        return type;
    }
}
