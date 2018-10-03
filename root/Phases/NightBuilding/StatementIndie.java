package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;

public class StatementIndie extends Statement {
    public StatementIndie(String beschreibung, String title, StatementType type) {
        this.beschreibung = beschreibung;
        this.title = title;
        this.type = type;
    }

    @Override
    public boolean isVisibleNow() {
        return true;
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
}
