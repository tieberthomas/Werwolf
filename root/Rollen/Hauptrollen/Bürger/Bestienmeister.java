package root.Rollen.Hauptrollen.Bürger;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;

public class Bestienmeister extends Hauptrolle
{
    public static final String name = "Bestienmeister";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.BESTIENMEISTER_KARTE;
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