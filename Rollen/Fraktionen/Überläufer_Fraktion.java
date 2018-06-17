package root.Rollen.Fraktionen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;

import java.awt.*;

/**
 * Created by Steve on 25.11.2017.
 */
public class Überläufer_Fraktion extends Fraktion
{
    public static final String name = "Überläufer";
    public static final Color farbe = Color.white;
    public static final String imagePath = ResourcePath.ÜBERLÄUFER_ICON;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getFarbe() { return farbe; }

    @Override
    public String getImagePath() {
        return imagePath;
    }
}
