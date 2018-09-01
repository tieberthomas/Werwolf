package root.Rollen.Hauptrollen.Werwölfe;

import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;

public class Werwolf extends Hauptrolle {
    public static final String name = "Werwolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ImagePath.WERWOLF_KARTE;
    public static int numberOfPossibleInstances = 100;
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
    public int getNumberOfPossibleInstances() {
        return numberOfPossibleInstances;
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