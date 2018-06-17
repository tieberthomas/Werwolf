package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrolle;
import root.Rollen.Nebenrollen.*;
import root.Rollen.RoleType;
import root.Spieler;

public class Seherin extends Hauptrolle {
    public static final String name = "Seherin";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.SEHERIN_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer != null) {
            besucht = chosenPlayer;

            String nebenrolle = chosenPlayer.nebenrolle.getName();

            if (nebenrolle.equals(Lamm.name)) {
                return Bürger.name;
            }
            if (nebenrolle.equals(Wolfspelz.name)) {
                return Werwölfe.name;
            }
            if (nebenrolle.equals(Vampirumhang.name)) {
                return Vampire.name;
            }
            if (nebenrolle.equals(Schattenkutte.name)) {
                return Schattenpriester_Fraktion.name;
            }
            if (nebenrolle.equals(Tarnumhang.name)) {
                return Nebenrolle.TARNUMHANG;
            }

            return chosenPlayer.hauptrolle.getFraktion().getName();
        }

        return null;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getMitspielerFrontendControl(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRoleType() {
        return roleType;
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
