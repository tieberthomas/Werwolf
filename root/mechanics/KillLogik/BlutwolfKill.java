package root.mechanics.KillLogik;

import root.Persona.Fraktion;
import root.Spieler;

public class BlutwolfKill {
    public static void execute(Spieler opfer, Fraktion täterFraktion) {
        boolean defendable = false;
        boolean hideable = true;
        boolean ressurectable = true;
        boolean killVisitors = true;
        boolean refreshSchamaninSchutz = true;

        Angriff angriff = new Angriff(opfer, täterFraktion, defendable, hideable, ressurectable, killVisitors, refreshSchamaninSchutz);
        angriff.execute();
    }
}
