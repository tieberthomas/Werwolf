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

    public static final String NAME = "Sammler";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.SAMMLER_KARTE;

    public Sammler() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }


    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    public static boolean isSammlerRolle(String rolle) {
        for (Nebenrolle currentRolle : game.mitteNebenrollen) {
            if (currentRolle.name.equals(rolle) &&
                    !currentRolle.name.equals(Totengräber.NAME) &&
                    !currentRolle.getType().equals(new Passiv()) &&
                    !currentRolle.getType().equals(new Tarnumhang_NebenrollenType())) {
                return true;
            }
        }
        return false;
    }
}