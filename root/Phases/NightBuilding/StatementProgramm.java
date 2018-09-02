package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;

public class StatementProgramm extends Statement {
    public StatementProgramm(String beschreibung) {
        this.beschreibung = beschreibung;
        this.type = StatementType.PROGRAMM;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isLebend() {
        return true;
    }

    @Override
    public boolean isOpfer() {
        return false;
    }

    @Override
    public boolean isAktiv() {
        return true;
    }
}
