package root.Rollen.Hauptrollen.Werwölfe;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;

/**
 * Created by Steve on 12.11.2017.
 */
public class Blutwolf extends Hauptrolle
{
    public static final String name = "Blutwolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ResourcePath.BLUTWOLF_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public static boolean killing = true;
    public static int deadStacks = 0;
    public static boolean deadly = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
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

    @Override
    public boolean isKilling() {
        return killing;
    }
}