package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;

public class StatementIndie extends Statement {
    public StatementIndie(String identifier, String title, String beschreibung, StatementType type) {
        this.identifier = identifier;
        this.title = title;
        this.beschreibung = beschreibung;
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

    public boolean isAufgebrauchtNow() {
        return false;
    }
}
