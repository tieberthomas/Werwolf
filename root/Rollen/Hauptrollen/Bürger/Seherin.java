package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.*;
import root.Spieler;

public class Seherin extends Hauptrolle {
    public static final String TARNUMHANG_TITLE = "Tarnumhang";

    public static final String name = "Seherin";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.SEHERIN_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);

        if(chosenPlayer != null) {
            besucht = chosenPlayer;

            String nebenrolle = chosenPlayer.nebenrolle.getName();
            if (nebenrolle.equals(Lamm.name)) {
                return new FrontendControl(Bürger.imagePath);
            }
            if (nebenrolle.equals(Wolfspelz.name)) {
                return new FrontendControl(Werwölfe.imagePath);
            }
            if (nebenrolle.equals(Vampirumhang.name)) {
                return new FrontendControl(Vampire.imagePath);
            }
            if (nebenrolle.equals(Schattenkutte.name)) {
                return new FrontendControl(Schattenpriester_Fraktion.imagePath);
            }
            if (nebenrolle.equals(Tarnumhang.name)) {
                return new FrontendControl(ResourcePath.TARNUMHANG, TARNUMHANG_TITLE);
            }

            return new FrontendControl(chosenPlayer.hauptrolle.getFraktion().getImagePath(), chosenOption);
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
