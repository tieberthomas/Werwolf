package root.Logic;

import java.util.ArrayList;
import java.util.List;

public class Torte {
    public static boolean torte;
    public static boolean gut;

    public static List<Spieler> tortenEsser = new ArrayList<>();

    public static void setTortenEsser(List<String> names) {
        names.forEach(name -> tortenEsser.add(Game.game.findSpieler(name)));
    }
}
