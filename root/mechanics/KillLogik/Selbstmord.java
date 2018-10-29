package root.mechanics.KillLogik;

import root.Spieler;

public class Selbstmord {
    public static void execute(Spieler opfer) {
        boolean defendable = false;
        boolean hideable = false;
        boolean ressurectable = false;
        boolean killVisitors = false;
        boolean refreshSchamaninSchutz = false;

        Angriff angriff = new Angriff(opfer, defendable, hideable, ressurectable, killVisitors, refreshSchamaninSchutz);
        angriff.execute();
    }
}
