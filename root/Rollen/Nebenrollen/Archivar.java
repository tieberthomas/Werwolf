package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrollen.Bürger.Bestienmeister;
import root.Rollen.Nebenrolle;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Archivar extends Nebenrolle
{
    public static final String PASSIV_TITLE = "Passiv";
    public static final String AKTIV_TITLE = "Aktiv";
    public static final String INFORMATIV_TITLE = "Informativ";
    public static final String TARNUMHANG_TITLE = "Tarnumhang";

    public static final String name = "Archivar";
    public static final String imagePath = ResourcePath.ARCHIVAR_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public String type = Nebenrolle.INFORMATIV;

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);

        if(chosenPlayer != null) {
            besucht = chosenPlayer;

            if(chosenPlayer.hauptrolle.getName().equals(Bestienmeister.name)) {
                if(!Spieler.findSpielerPerRolle(name).hauptrolle.getFraktion().getName().equals(Bürger.name)) {
                    return new FrontendControl(ResourcePath.TARNUMHANG, TARNUMHANG_TITLE);
                }
            }

            switch(chosenPlayer.nebenrolle.getType()) {
                case Nebenrolle.AKTIV:
                    return new FrontendControl(ResourcePath.AKTIV, AKTIV_TITLE);

                case Nebenrolle.PASSIV:
                    return new FrontendControl(ResourcePath.PASSIV, PASSIV_TITLE);

                case Nebenrolle.INFORMATIV:
                    return new FrontendControl(ResourcePath.INFORMATIV, INFORMATIV_TITLE);

                case Nebenrolle.TARNUMHANG:
                    return new FrontendControl(ResourcePath.TARNUMHANG, TARNUMHANG_TITLE);
            }
        }

        return new FrontendControl();
    }

    @Override
    public String getName() {
        return name;
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
