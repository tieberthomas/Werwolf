package root.Persona.Rollen.Hauptrollen.Schattenpriester;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Schattenpriester extends Hauptrolle {
    public static final String name = "Schattenpriester";
    public static Fraktion fraktion = new Schattenpriester_Fraktion();
    public static final String imagePath = ImagePath.SCHATTENPRIESTER_KARTE;
    public static int numberOfPossibleInstances = 100;
    public static boolean spammable = true;
    public boolean neuster = false;

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