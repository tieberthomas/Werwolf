package root.Rollen.Hauptrollen.Schattenpriester;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Nebenrollen.Schattenkutte;
import root.mechanics.Opfer;

/**
 * Created by Steve on 12.11.2017.
 */
public class Schattenpriester extends Hauptrolle
{
    public static final String name = "Schattenpriester";
    public static Fraktion fraktion = new Schattenpriester_Fraktion();
    public static final String imagePath = ResourcePath.SCHATTENPRIESTER_KARTE;
    public static boolean unique = false;
    public static boolean spammable = true;
    public boolean neuster = false;

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