package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementState;
import root.Phases.NightBuilding.Constants.StatementType;

public class Statement {
    public String beschreibung;
    public String title;
    public StatementType type;
    private StatementState state = StatementState.INVISIBLE_NOT_IN_GAME;
    public boolean alreadyOver = false;

    public Statement() {
        type = StatementType.EMPTY_STATEMENT;
    }

    public StatementState getState() {
        return state;
    }

    public void refreshState() {
        state = getStateNow();
    }

    public StatementState getStateNow() {
        if (!isVisibleNow()) {
            return StatementState.INVISIBLE_NOT_IN_GAME;
        }
        if (!isLebendNow()) {
            return StatementState.NOT_IN_GAME;
        }
        if (isOpferNow()) {
            return StatementState.DEAD;
        }
        if (!isAktivNow()) {
            return StatementState.DEAKTIV;
        }
        if (isAufgebrauchtNow()) {
            return StatementState.AUFGEBRAUCHT;
        }
        return StatementState.NORMAL;
    }

    public boolean isVisible() {
        switch (state) {
            case INVISIBLE_NOT_IN_GAME:
                return false;
            default:
                return true;
        }
    }

    public boolean isLebend() {
        switch (state) {
            case INVISIBLE_NOT_IN_GAME:
            case NOT_IN_GAME:
                return false;
            default:
                return true;
        }
    }

    public boolean isVisibleNow() {
        return true;
    }

    public boolean isLebendNow() {
        return true;
    }

    public boolean isOpferNow() {
        return false;
    }

    public boolean isAktivNow() {
        return true;
    }

    public boolean isAufgebrauchtNow() {
        return false;
    }
}
