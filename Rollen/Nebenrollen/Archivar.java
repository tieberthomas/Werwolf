package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrollen.Bürger.Bestienmeister;
import root.Rollen.Nebenrolle;
import root.Rollen.RoleType;
import root.Spieler;
import root.mechanics.Liebespaar;

/**
 * Created by Steve on 12.11.2017.
 */
public class Archivar extends Nebenrolle
{
    public static final String name = "Archivar";
    public static int roleType = RoleType.CHOOSE_ONE;
    public static final String imagePath = ResourcePath.ARCHIVAR_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public String type = Nebenrolle.INFORMATIV;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        if(chosenPlayer!=null) {
            besucht = chosenPlayer;

            String type = chosenPlayer.nebenrolle.getType();

            if(chosenPlayer.hauptrolle.getName().equals(Bestienmeister.name)) {
                if(!Spieler.findSpielerPerRolle(name).hauptrolle.getFraktion().getName().equals(Bürger.name)) {
                    type = Nebenrolle.TARNUMHANG;
                }
            }

            return type;
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
    public String getType() { return type; }
}
