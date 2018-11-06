package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.SchattenpriesterZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class Schattenkutte extends Bonusrolle {
    public static final String ID = "Schattenkutte";
    public static final String NAME = "Schattenkutte";
    public static final String IMAGE_PATH = ImagePath.SCHATTENKUTTE_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public static final Color COLOR = SchattenpriesterFraktion.COLOR;

    public Schattenkutte() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;
    }

    public Zeigekarte getFraktionInfo() {
        return new SchattenpriesterZeigekarte();
    }
}
