package root.Persona.Rollen.Nebenrollen;

import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class Schattenkutte extends Bonusrolle {
    public static final String NAME = "Schattenkutte";
    public static final String IMAGE_PATH = ImagePath.SCHATTENKUTTE_KARTE;
    public static final NebenrollenType TYPE = new Passiv();
    public static final Color COLOR = Schattenpriester_Fraktion.COLOR;

    public Schattenkutte() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;
    }
}
