package root.Logic.KillLogic;

import root.Logic.Persona.Fraktion;
import root.Logic.Phases.NormalNight;
import root.Logic.Spieler;

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

    public static boolean isOpfer(Spieler spieler) {
        for (Opfer currentOpfer : NormalNight.opfer) {
            if (currentOpfer.spieler.equals(spieler)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isOpferPerRolle(String rolleID) {
        for (Opfer currentOpfer : NormalNight.opfer) {
            if (currentOpfer.spieler.hauptrolle.equals(rolleID) || currentOpfer.spieler.bonusrolle.equals(rolleID)) {
                return true;
            }
        }

        return false;
    }
}
