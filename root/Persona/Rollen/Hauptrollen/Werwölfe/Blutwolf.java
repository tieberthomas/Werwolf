package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;

public class Blutwolf extends Hauptrolle {
    public static final String name = "Blutwolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ImagePath.BLUTWOLF_KARTE;
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
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }
}