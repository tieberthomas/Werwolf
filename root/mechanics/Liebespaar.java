package root.mechanics;

import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.Utils.ListHelper;
import root.Utils.Rand;

import java.util.List;

public class Liebespaar {
    Game game;

    public static final String IMAGE_PATH = ImagePath.LIEBESPAAR;
    private static final String ZUFÄLLIG = "Zufällig";

    public Spieler spieler1;
    public Spieler spieler2;

    public List<String> getDropdownOptions() {
        List<String> spielerStrings = game.getLivingSpielerStrings();
        spielerStrings.add(ZUFÄLLIG);
        return spielerStrings;
    }

    public Liebespaar(Game game) {
        this.game = game;
    }

    public Liebespaar(String spieler1Name, String spieler2Name, Game game) {
        this.game = game;
        List<Spieler> spieler = ListHelper.cloneList(game.spieler);

        if (spieler1Name.equals(ZUFÄLLIG)) {
            if (!spieler2Name.equals(ZUFÄLLIG)) {
                spieler2 = game.findSpieler(spieler2Name);
                spieler.remove(spieler2);
            }
            spieler1 = generateRandomSpieler(spieler);
        } else {
            spieler1 = game.findSpieler(spieler1Name);
        }

        if (spieler2Name.equals(ZUFÄLLIG)) {
            spieler.remove(spieler1);
            spieler2 = generateRandomSpieler(spieler);
        } else {
            spieler2 = game.findSpieler(spieler2Name);
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
