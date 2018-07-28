package root.Phases;

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

    public static final int PROGRAMM = 0;
    public static final int SHOW_TITLE = 1;
    public static final int INDIE = 2;
    public static final int ROLLE_CHOOSE_ONE = 10;
    public static final int ROLLE_CHOOSE_ONE_INFO = 11;
    public static final int ROLLE_INFO = 12;
    public static final int ROLLE_SPECAL = 13;
    public static final int FRAKTION_CHOOSE_ONE = 20;
    public static final int FRAKTION_SPECAL = 21;

    public String beschreibung;
    public String title;
    public int type;

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
