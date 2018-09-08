package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Persona.Rollen.Nebenrollen.Totengräber;
import root.ResourceManagement.ImagePath;

public class Sammler extends Hauptrolle {
    public static final String beschreibungAddiditon = "Der Sammler als ";
    public static final String beschreibungAddiditonLowerCase = "der Sammler als ";

    public static final String name = "Sammler";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.SAMMLER_KARTE;
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
    public boolean isSpammable() {
        return spammable;
    }

    public static boolean isSammlerRolle(String rolle) {
        for (Nebenrolle currentRolle : game.mitteNebenrollen) {
            if (currentRolle.getName().equals(rolle) &&
                    !currentRolle.getName().equals(Totengräber.name) &&
                    !currentRolle.getType().equals(new Passiv()) &&
                    !currentRolle.getType().equals(new Tarnumhang_NebenrollenType())) {
                return true;
            }
        }
        return false;
    }
}