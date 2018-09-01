package root.Rollen.Hauptrollen.Bürger;

import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;

public class Dorfbewohner extends Hauptrolle {
    public static final String name = "Dorfbewohner";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.DORFBEWOHNER_KARTE;
    public static int numberOfPossibleInstances = 100;
    public static boolean spammable = false;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public int getNumberOfPossibleInstances() {
        return numberOfPossibleInstances;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }
}
