package root.Logic.KillLogic;

import root.Logic.Persona.Fraktion;
import root.Logic.Spieler;

public class NormalKill {
    public static void execute(Spieler opfer, Spieler täter) {
        boolean defendable = true;
        boolean hideable = true;
        boolean ressurectable = true;
        boolean killVisitors = true;
        boolean refreshSchamaninSchutz = true;

        Angriff angriff = new Angriff(opfer, täter, defendable, hideable, ressurectable, killVisitors, refreshSchamaninSchutz);
        angriff.execute();
    }

    public static void execute(Spieler opfer, Fraktion täterFraktion) {
        boolean defendable = true;
        boolean hideable = true;
        boolean ressurectable = true;
        boolean killVisitors = true;
        boolean refreshSchamaninSchutz = true;

        Angriff angriff = new Angriff(opfer, täterFraktion, defendable, hideable, ressurectable, killVisitors, refreshSchamaninSchutz);
        angriff.execute();
    }
}
