package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.Tarnumhang;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Späher extends Hauptrolle
{
    public static final String TÖTEND_TITLE = "Tötend";
    public static final String NICHT_TÖTEND_TITLE = "Nicht Tötend";
    public static final String TARNUMHANG_TITLE = "Tarnumhang";

    public static final String name = "Späher";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.SPÄHER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    @Override
    public FrontendControl getDropdownOptions() {
        return Spieler.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);

        if(chosenPlayer != null) {
            besucht = chosenPlayer;

            if(chosenPlayer.nebenrolle.getName().equals(Tarnumhang.name)) {
                return new FrontendControl(TARNUMHANG_TITLE, ResourcePath.TARNUMHANG);
            }

            if(chosenPlayer.hauptrolle.isKilling()) {
                abilityCharges--;

                return new FrontendControl(TÖTEND_TITLE, ResourcePath.TÖTEND);
            } else {
                return new FrontendControl(NICHT_TÖTEND_TITLE, ResourcePath.NICHT_TÖTEND);
            }
        }

        return new FrontendControl();
    }

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
