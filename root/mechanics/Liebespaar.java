package root.mechanics;

import root.ResourceManagement.ResourcePath;
import root.Spieler;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Steve on 23.11.2017.
 */
public class Liebespaar
{
    public static final String imagePath = ResourcePath.LIEBESPAAR;
    public static final String ZUFÄLLIG = "Zufällig";

    public static Spieler spieler1;
    public static Spieler spieler2;

    public Liebespaar(Spieler spieler1, Spieler spieler2)
    {
        if(spieler1.name!=spieler2.name) {
            Liebespaar.spieler1 = spieler1;
            Liebespaar.spieler2 = spieler2;
        }
    }

    public static void neuesLiebespaar(String spieler1String, String spieler2String)
    {
        Spieler spieler1;
        Spieler spieler2;

        ArrayList<Spieler> spieler = (ArrayList<Spieler>)Spieler.spieler.clone();

        if(spieler1String.equals(ZUFÄLLIG)) {
            spieler1 = generateRandomSpieler(spieler);
        } else {
            spieler1 = Spieler.findSpieler(spieler1String);
        }

        if(spieler2String.equals(ZUFÄLLIG)) {
            spieler.remove(spieler1);
            spieler2 = generateRandomSpieler(spieler);
        } else {
            spieler2 = Spieler.findSpieler(spieler2String);
        }

        Spieler.liebespaar = new Liebespaar(spieler1,spieler2);
    }

    public static Spieler generateRandomSpieler(ArrayList<Spieler> spieler){
        int randomNum = ThreadLocalRandom.current().nextInt(0, spieler.size());

        return spieler.get(randomNum);
    }

    public static Spieler getPlayerToDie() {
        if(spieler1!=null && spieler2!=null) {
            if (Liebespaar.spieler1.lebend && !Liebespaar.spieler2.lebend) {
                return Liebespaar.spieler1;
            }
            if (!Liebespaar.spieler1.lebend && Liebespaar.spieler2.lebend) {
                return Liebespaar.spieler2;
            }
        }

        return null;
    }

    public static String getImagePath() {
        return imagePath;
    }
}
