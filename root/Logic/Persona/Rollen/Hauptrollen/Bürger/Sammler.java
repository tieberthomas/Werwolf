package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Bonusrollen.Totengräber;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.ResourceManagement.ImagePath;

public class Sammler extends Hauptrolle {
    public static final String ID = "ID_Sammler";
    public static final String NAME = "Sammler";
    public static final String IMAGE_PATH = ImagePath.SAMMLER_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String beschreibungAddiditon = "Der Sammler als ";
    public static final String konditorlehrlingSearchString = "Konditorlehrling erwachen ";
    public static final String beschreibungAddiditonLowerCase = "der Sammler als ";

    public Sammler() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;
    }


    public static boolean isSammlerRolle(String rolleID) {
        for (Bonusrolle currentRolle : Game.game.mitteBonusrollen) {
            if (currentRolle.equals(rolleID) &&
                    !currentRolle.equals(Totengräber.ID) &&
                    !currentRolle.type.equals(new Passiv()) &&
                    !currentRolle.type.equals(new Tarnumhang_BonusrollenType())) {
                return true;
            }
        }
        return false;
    }
}