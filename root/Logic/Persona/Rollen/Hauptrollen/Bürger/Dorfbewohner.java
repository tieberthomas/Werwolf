package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Dorfbewohner extends Hauptrolle {
    public static final String ID = "ID_Dorfbewohner";
    public static final String NAME = "Dorfbewohner";
    public static final String IMAGE_PATH = ImagePath.DORFBEWOHNER_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public Dorfbewohner() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.numberOfPossibleInstances = 100;
    }
}
