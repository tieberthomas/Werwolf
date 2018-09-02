package root.Persona.Rollen.Hauptrollen.Bürger;

import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;

public class Bestienmeister extends Hauptrolle {
    public static final String name = "Bestienmeister";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.BESTIENMEISTER_KARTE;
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