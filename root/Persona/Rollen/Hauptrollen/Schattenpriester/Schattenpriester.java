package root.Persona.Rollen.Hauptrollen.Schattenpriester;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Schattenpriester extends Hauptrolle {
    public static final String NAME = "Schattenpriester";
    public static Fraktion fraktion = new Schattenpriester_Fraktion();
    public static final String IMAGE_PATH = ImagePath.SCHATTENPRIESTER_KARTE;
    public boolean neuster = false;

    public Schattenpriester() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.numberOfPossibleInstances = 100;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }
}