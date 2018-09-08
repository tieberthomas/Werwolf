package root.Persona.Rollen.Nebenrollen;

import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class SchwarzeSeele extends Nebenrolle {
    public static final String name = "Schwarze Seele";
    public static final String imagePath = ImagePath.SCHWARZE_SEELE_KARTE;
    public static boolean unique = false;
    public NebenrollenType type = new Passiv();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }
}
