package root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten;

import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.ResourceManagement.ImagePath;

public class BürgerZeigekarte extends Zeigekarte {
    public BürgerZeigekarte() {
        this.name = "Bürger_Zeigekarte";
        this.title = "Bürger";
        this.imagePath = ImagePath.BÜRGER_ICON;
    }
}
