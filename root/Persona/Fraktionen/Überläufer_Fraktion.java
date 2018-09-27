package root.Persona.Fraktionen;

import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class Überläufer_Fraktion extends Fraktion {
    public static final String NAME = "Überläufer";
    public static final String IMAGE_PATH = ImagePath.ÜBERLÄUFER_ICON;
    public static final Color COLOR = Color.white;
    public static final Zeigekarte zeigekarte = new WerwölfeZeigekarte();

    public Überläufer_Fraktion() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.color = COLOR;
    }

    @Override
    public Zeigekarte getZeigeKarte() {
        return zeigekarte;
    }
}
