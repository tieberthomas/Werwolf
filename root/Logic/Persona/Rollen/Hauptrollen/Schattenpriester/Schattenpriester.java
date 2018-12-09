package root.Logic.Persona.Rollen.Hauptrollen.Schattenpriester;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Schattenpriester extends Hauptrolle {
    public static final String ID = "ID_Schattenpriester";
    public static final String NAME = "Schattenpriester";
    public static final String IMAGE_PATH = ImagePath.SCHATTENPRIESTER_KARTE;
    public static final Fraktion FRAKTION = new SchattenpriesterFraktion();
    public boolean neuster = false;

    public Schattenpriester() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.numberOfPossibleInstances = 100;
    }
}