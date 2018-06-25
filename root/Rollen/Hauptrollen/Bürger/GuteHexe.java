package root.Rollen.Hauptrollen.Bürger;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;
import root.Spieler;
import root.mechanics.Opfer;

/**
 * Created by Steve on 12.11.2017.
 */
public class GuteHexe extends Hauptrolle
{
    public static final String name = "Gute Hexe";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ResourcePath.GUTE_HEXE_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public int schutzCharges = 1;
    public Spieler besuchtWiederbeleben = null;

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

    public void schützen(String chosenOption)
    {
        if(chosenOption!=null && !chosenOption.equals("") && schutzCharges>0) {
            Spieler chosenPlayer = Spieler.findSpieler(chosenOption);

            besucht = chosenPlayer;

            chosenPlayer.geschützt = true;

            schutzCharges--;
        }
    }

    public void wiederbeleben(Opfer opfer)
    {
        if(opfer!=null) {
            besuchtWiederbeleben = opfer.opfer;
        }

        Opfer.deadVictims.remove(opfer);
        abilityCharges--;
    }
}