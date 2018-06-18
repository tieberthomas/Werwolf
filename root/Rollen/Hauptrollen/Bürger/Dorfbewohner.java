package root.Rollen.Hauptrollen.Bürger;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;

/**
 * Created by Steve on 12.11.2017.
 */
public class Dorfbewohner extends Hauptrolle
{
    public static final String name = "Dorfbewohner";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.DORFBEWOHNER_KARTE;
    public static boolean unique = false;
    public static boolean spammable = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }
}
