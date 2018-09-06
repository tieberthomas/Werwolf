package root.Persona.Fraktionen;

import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;

import java.awt.*;

public class Überläufer_Fraktion extends Fraktion {
    public static final String name = "Überläufer";
    public static final Color farbe = Color.white;
    public static final String imagePath = ImagePath.ÜBERLÄUFER_ICON;
    public static final Zeigekarte zeigekarte = new WerwölfeZeigekarte();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public Zeigekarte getZeigeKarte() {
        return zeigekarte;
    }
}
