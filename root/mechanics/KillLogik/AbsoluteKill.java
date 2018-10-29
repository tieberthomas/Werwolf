package root.mechanics.KillLogik;

import root.Spieler;

public class AbsoluteKill {
    public static void execute(Spieler opfer, Spieler täter) {
        boolean defendable = false;
        boolean hideable = false;
        boolean ressurectable = false;
        boolean killVisitors = true;
        boolean refreshSchamaninSchutz = true;

        Angriff angriff = new Angriff(opfer, täter, defendable, hideable, ressurectable, killVisitors, refreshSchamaninSchutz);
        angriff.execute();
    }
}
