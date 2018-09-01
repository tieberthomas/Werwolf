package root.Rollen.Hauptrollen.Werwölfe;

import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;

public class Alphawolf extends Hauptrolle {
    public static final String name = "Alphawolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ImagePath.ALPHAWOLF_KARTE;
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
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }
}