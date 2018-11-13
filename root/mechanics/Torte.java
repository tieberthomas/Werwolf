package root.mechanics;

import root.Spieler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Torte {
    public static boolean torte;
    public static boolean gut;

    public static List<Spieler> tortenEsser = new ArrayList<>();

    public static List<String> getTortenesserNames() {
        return tortenEsser.stream().map(esser -> esser.name).collect(Collectors.toList());
    }
}
