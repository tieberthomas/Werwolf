package root.Logic.Phases.NightBuilding.Statement.StatementDependency;

import root.Logic.Phases.NightBuilding.Constants.StatementState;

public class StatementDependency {
    public StatementState getState() {
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
