package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;

public class StatementProgramm extends Statement {
    public StatementProgramm(String beschreibung) {
        this.beschreibung = beschreibung;
        this.type = StatementType.PROGRAMM;
    }

    @Override
    public boolean isVisibleNow() {
        return false;
    }

    @Override
    public boolean isLebendNow() {
        return true;
    }

    @Override
    public boolean isOpferNow() {
        return false;
    }

    @Override
    public boolean isAktivNow() {
        return true;
    }

    public boolean isAufgebrauchtNow() {
        return false;
    }
}
