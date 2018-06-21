package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;
import root.Rollen.RoleType;
import root.Spieler;

public class HoldeMaid extends Hauptrolle
{
    public static final String name = "Holde Maid";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.HOLDE_MAID_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer!=null) {
            besucht = chosenPlayer;
        }

        return null;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getPlayerCheckSpammableFrontendControl(this);
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
