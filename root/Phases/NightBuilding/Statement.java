package root.Phases.NightBuilding;

/**
 * Created by Steve on 23.11.2017.
 */
public class Statement
{
    public static final int NORMAL = 0;
    public static final int DEAKTIV = 1;
    public static final int DEAD = 2;
    public static final int NOT_IN_GAME = 3;
    public static final int INVISIBLE_NOT_IN_GAME = 4;

    public String beschreibung;
    public String title;
    public StatementType type;

    public int getState() {
        if(!isVisible()) {
            return INVISIBLE_NOT_IN_GAME;
        }
        if(!isLebend()) {
            return NOT_IN_GAME;
        }
        if(isOpfer()) {
            return DEAD;
        }
        if(!isAktiv()) {
            return DEAKTIV;
        }
        return NORMAL;
    }

    public boolean isVisible() {
        return true;
    }

    public boolean isLebend() {
        return true;
    }

    public boolean isOpfer() {
        return false;
    }

    public boolean isAktiv() {
        return true;
    }
}
