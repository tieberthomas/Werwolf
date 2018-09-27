package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Bruder extends Hauptrolle {
    public static final String NAME = "Bruder";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.BRUDER_KARTE;

    public Bruder() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.numberOfPossibleInstances = 2;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }
}