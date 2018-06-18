package root.Rollen.Hauptrollen.Vampire;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Hauptrolle;

/**
 * Created by Steve on 12.11.2017.
 */
public class GrafVladimir extends Hauptrolle
{
    public static final String name = "Graf Vladimir";
    public static Fraktion fraktion = new Vampire();
    public static final String imagePath = ResourcePath.GRAF_VLADIMIR_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public static boolean killing = true;

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