package root.Rollen.Nebenrollen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Rollen.NebenrollenTyp;

/**
 * Created by Steve on 12.11.2017.
 */
public class ReineSeele extends Nebenrolle
{
    public static final String name = "Reine Seele";
    public static final String imagePath = ResourcePath.REINE_SEELE_KARTE;
    public static boolean unique = false;
    public static boolean spammable = false;
    public boolean dayInvincibility = true;
    public NebenrollenTyp type = NebenrollenTyp.PASSIV;

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
    public NebenrollenTyp getType() { return type; }
}
