package root.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;
import root.Spieler;
import root.mechanics.Opfer;

/**
 * Created by Steve on 12.11.2017.
 */
public class Riese extends Hauptrolle
{
    public static final String name = "Riese";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.RIESE_KARTE;
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
        return Spieler.getPlayerCheckSpammableFrontendControl(this);
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

    @Override
    public boolean isKilling() {
        return killing;
    }
}