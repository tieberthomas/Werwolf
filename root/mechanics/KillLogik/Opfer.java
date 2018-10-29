package root.mechanics.KillLogik;

import root.Persona.Fraktion;
import root.Phases.NormalNight;
import root.Spieler;

public class Opfer {
    public Spieler spieler;
    public Spieler täter;
    public Fraktion täterFraktion;
    public boolean fraktionsTäter;

    public Opfer(Angriff angriff) {
        this.spieler = angriff.opfer;
        this.täter = angriff.täter;
        this.täterFraktion = angriff.täterFraktion;
        this.fraktionsTäter = angriff.fraktionsTäter;
    }

    public static void removeOpfer(Spieler spieler) {
        NormalNight.opfer.removeIf(opfer -> opfer.spieler.equals(spieler));
    }

    public static boolean isOpfer(String name) {
        for (Opfer currentOpfer : NormalNight.opfer) {
            if (currentOpfer.spieler.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isOpferPerRolle(String name) {
        for (Opfer currentOpfer : NormalNight.opfer) {
            if (currentOpfer.spieler.hauptrolle.name.equals(name) || currentOpfer.spieler.bonusrolle.name.equals(name)) {
                return true;
            }
        }

        return false;
    }
}
