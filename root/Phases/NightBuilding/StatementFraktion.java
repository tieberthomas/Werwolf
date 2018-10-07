package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Phases.NightBuilding.Constants.StatementType;

public class StatementFraktion extends Statement {
    public String fraktion;

    public StatementFraktion(Fraktion fraktion) {
        this(fraktion.statementBeschreibung, fraktion.statementTitle, fraktion.name, fraktion.statementType);
    }

    public StatementFraktion(String beschreibung, String title, String fraktion, StatementType type) {
        this.beschreibung = beschreibung;
        this.title = title;
        this.fraktion = fraktion;
        this.type = type;
    }

    @Override
    public boolean isVisibleNow() {
        return Fraktion.fraktionInNachtEnthalten(fraktion);
    }

    @Override
    public boolean isLebendNow() {
        return Fraktion.fraktionLebend(fraktion);
    }

    @Override
    public boolean isOpferNow() {
        return Fraktion.fraktionOpfer(fraktion);
    }

    @Override
    public boolean isAktivNow() {
        return Fraktion.fraktionAktiv(fraktion);
    }

    public boolean isAufgebrauchtNow() {
        return false;
    }

    public Fraktion getFraktion() {
        return Fraktion.findFraktion(fraktion);
    }
}
