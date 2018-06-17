package root.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Hauptrolle;
import root.Rollen.RoleType;
import root.Spieler;
import root.mechanics.Opfer;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class MissVerona extends Hauptrolle
{
    public static final String name = "Miss Verona";
    public static Fraktion fraktion = new Vampire();
    public static final String imagePath = ResourcePath.MISS_VERONA_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public static boolean killing = true;

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

    public ArrayList<String> findUntote(){
        ArrayList<String> untote = new ArrayList<>();

        for (Opfer possibleOpfer : Opfer.possibleVictims) {
            boolean überlebt = true;
            String currentPossibleOpferName = possibleOpfer.opfer.name;

            for (Opfer deadOpfer : Opfer.deadVictims) {
                if (currentPossibleOpferName.equals(deadOpfer.opfer.name)) {
                    überlebt = false;
                }
            }

            if (überlebt) {
                if (!untote.contains(currentPossibleOpferName))
                    untote.add(currentPossibleOpferName);
            }
        }

        return untote;
    }
}