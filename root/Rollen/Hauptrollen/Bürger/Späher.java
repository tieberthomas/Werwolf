package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.*;
import root.Rollen.RoleType;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Späher extends Hauptrolle
{
    public static final String TÖTEND = "Tötend";
    public static final String NICHT_TÖTEND = "Nicht Tötend";
    public static final String TARNUMHANG = "Tarnumhang";

    public static final String name = "Späher";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.SPÄHER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer!=null) {
            besucht = chosenPlayer;

            if(chosenPlayer.nebenrolle.getName().equals(Tarnumhang.name)) {
                return TARNUMHANG;
            }

            if(chosenPlayer.hauptrolle.isKilling()) {
                abilityCharges--;
                return TÖTEND;
            } else {
                return NICHT_TÖTEND;
            }
        }

        return null;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getMitspielerCheckSpammableFrontendControl(this);
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
