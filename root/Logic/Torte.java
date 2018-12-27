package root.Logic;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Torte {
    public static boolean torte;
    public static boolean gut;
    public static boolean tortenStück;
    public static boolean stückGut;

    public static final String TORTENSTUECK_TITLE = "Tortenstück";
    public static final String TORTE_NEHMEN = "Torte nehmen";
    public static final String TORTE_NICHT_NEHMEN = "Torte nicht nehmen";

    public static List<Spieler> tortenEsser = new ArrayList<>();

    public static void setTortenEsser(List<String> names) {
        names.forEach(name -> tortenEsser.add(Game.game.findSpieler(name)));
    }

    public static FrontendControl getDropdownOptionsFrontendControl() {
        List<String> dropdownStrings = new ArrayList<>();
        dropdownStrings.add(TORTE_NEHMEN);
        dropdownStrings.add(TORTE_NICHT_NEHMEN);

        return new FrontendControl(FrontendControlType.DROPDOWN_IMAGE, new DropdownOptions(dropdownStrings), ImagePath.TORTE);
    }
}
