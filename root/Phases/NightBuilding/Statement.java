package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementState;
import root.Phases.NightBuilding.Constants.StatementType;

public class Statement {
    public String beschreibung;
    public String title;
    public StatementType type;

    public Statement() {
        type = StatementType.EMPTY_STATEMENT;
    }

    public StatementState getState() {
        if (!isVisible()) {
            return StatementState.INVISIBLE_NOT_IN_GAME;
        }
        if (!isLebend()) {
            return StatementState.NOT_IN_GAME;
        }
        if (isOpfer()) {
            return StatementState.DEAD;
        }
        if (!isAktiv()) {
            return StatementState.DEAKTIV;
        }
        return StatementState.NORMAL;
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
