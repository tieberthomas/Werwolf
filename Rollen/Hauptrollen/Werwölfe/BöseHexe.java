package root.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.RoleType;
import root.Spieler;
import root.mechanics.Opfer;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class BöseHexe extends Hauptrolle
{
    public static final String name = "Böse Hexe";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ResourcePath.BÖSE_HEXE_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public static boolean killing = true;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer!=null) {
            besucht = chosenPlayer;

            Spieler täter = Spieler.findSpielerPerRolle(name);
            Opfer.addVictim(chosenPlayer, täter, false);
            abilityCharges--;
        }

        return null;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getLivigPlayerOrNoneFrontendControl(this);
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

    @Override
    public boolean isKilling() {
        return killing;
    }
}