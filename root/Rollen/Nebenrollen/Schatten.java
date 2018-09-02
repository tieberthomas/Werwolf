package root.Rollen.Nebenrollen;

import root.ResourceManagement.ImagePath;
import root.Rollen.Nebenrolle;
import root.Rollen.Constants.NebenrollenTyp;

public class Schatten extends Nebenrolle {
    public static final String name = "Schatten";
    public static final String imagePath = ImagePath.SCHATTEN_KARTE;
    public static int numberOfPossibleInstances = 100;
    public static boolean spammable = false;
    public NebenrollenTyp type = NebenrollenTyp.PASSIV;

    @Override
    public String getName() {
        return name;
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

    @Override
    public NebenrollenTyp getType() {
        return type;
    }
}