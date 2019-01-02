package root.Logic;

import root.Controller.FrontendObject.DropdownImageFrontendObject;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Torte {
    public static boolean torte;
    public static boolean gut;
    public static boolean letzteTorteGut;
    static List<Spieler> tortenEsser = new ArrayList<>();

    public static boolean tortenStück;
    public static boolean stückGut;
    public static Spieler tortenStückEsser;
    public static boolean stückEssen;

    public static final String TORTENSTUECK_TITLE = "Tortenstück";
    public static final String TORTE_NEHMEN = "Torte nehmen";
    public static final String TORTE_NICHT_NEHMEN = "Torte nicht nehmen";


    public static void setTortenEsser(List<String> names) {
        names.forEach(name -> tortenEsser.add(Game.game.findSpieler(name)));
    }

    public static FrontendObject getFrontendObject() {
        List<String> dropdownStrings = new ArrayList<>();
        dropdownStrings.add(TORTE_NEHMEN);
        dropdownStrings.add(TORTE_NICHT_NEHMEN);

        return new DropdownImageFrontendObject(new DropdownOptions(dropdownStrings), ImagePath.TORTE);
    }

    public static void beginNight() {
        setSchütze();

        tortenEsser.clear();
        tortenStückEsser = null;
        stückEssen = false;
        torte = false;
        letzteTorteGut = gut;
        gut = false;
    }

    private static void setSchütze() {
        for (Spieler currentSpieler : Torte.tortenEsser) {
            if (Torte.torte) {
                eatTorte(currentSpieler, Torte.gut);
            }
        }

        if (Torte.tortenStück && tortenStückEsser != null && stückEssen) {
            eatTorte(tortenStückEsser, Torte.stückGut);
        }
    }

    private static void eatTorte(Spieler spieler, boolean gut) {
        if (gut) {
            spieler.geschützt = true;
        } else {
            spieler.aktiv = false;
        }
    }
}
