package root.Persona.Rollen.Hauptrollen.Bürger;

import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;

public class Bruder extends Hauptrolle {
    public static final String name = "Bruder";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.BRUDER_KARTE;
    public static int numberOfPossibleInstances = 2;
    public static boolean spammable = false;

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
}