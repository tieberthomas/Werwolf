package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Dorfbewohner extends Hauptrolle {
    public static final String NAME = "Dorfbewohner";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.DORFBEWOHNER_KARTE;
    public static int numberOfPossibleInstances = 100;

    public Dorfbewohner() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public int getNumberOfPossibleInstances() {
        return numberOfPossibleInstances;
    }
}
