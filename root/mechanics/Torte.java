package root.mechanics;

import root.Spieler;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Torte {
    public static boolean torte;
    public static boolean gut;

    public static ArrayList<Spieler> tortenEsser = new ArrayList<>();

    public static ArrayList<String> getTortenesserNames() {
        return (ArrayList<String>) tortenEsser.stream().map(esser -> esser.name).collect(Collectors.toList());
    }
}
