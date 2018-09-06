package root.Persona.Fraktionen;

import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class Bürger extends Fraktion {
    public static final String name = "Bürger";
    public static Color farbe = Color.yellow;
    public static final String imagePath = ImagePath.BÜRGER_ICON;
    public static final Zeigekarte zeigekarte = new BürgerZeigekarte();

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
