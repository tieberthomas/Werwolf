package root.mechanics;

import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.Utils.ListHelper;
import root.Utils.Rand;

import java.util.List;

public class Liebespaar {
    public static final String IMAGE_PATH = ImagePath.LIEBESPAAR;
    private static final String ZUFÄLLIG = "Zufällig";

    public Spieler spieler1;
    public Spieler spieler2;

    public List<String> getDropdownOptions() {
        List<String> spielerStrings = Game.game.getLivingSpielerStrings();
        spielerStrings.add(ZUFÄLLIG);
        return spielerStrings;
    }

    public Liebespaar() {
    }

    public Liebespaar(String spieler1Name, String spieler2Name) {
        List<Spieler> spieler = ListHelper.cloneList(Game.game.spieler);

        if (spieler1Name.equals(ZUFÄLLIG)) {
            if (!spieler2Name.equals(ZUFÄLLIG)) {
                spieler2 = Game.game.findSpieler(spieler2Name);
                spieler.remove(spieler2);
            }
            spieler1 = generateRandomSpieler(spieler);
        } else {
            spieler1 = Game.game.findSpieler(spieler1Name);
        }

        if (spieler2Name.equals(ZUFÄLLIG)) {
            spieler.remove(spieler1);
            spieler2 = generateRandomSpieler(spieler);
        } else {
            spieler2 = Game.game.findSpieler(spieler2Name);
        }
    }

    public static Spieler generateRandomSpieler(List<Spieler> spieler) {
        return Rand.getRandomElement(spieler);
    }

    public Spieler getSpielerToDie() {
        if (spieler1 != null && spieler2 != null) {
            if (spieler1.lebend && !spieler2.lebend) {
                return spieler1;
            }
            if (!spieler1.lebend && spieler2.lebend) {
                return spieler2;
            }
        }

        return null;
    }
}
