package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Phases.NightBuilding.Constants.StatementType;

public class StatementFraktion extends Statement {
    public String fraktion;

    public StatementFraktion(Fraktion fraktion) {
        this(fraktion.statementIdentifier, fraktion.statementTitle, fraktion.statementBeschreibung, fraktion.statementType, fraktion.name);
    }

    public StatementFraktion(String identifier, String title, String beschreibung, StatementType type, String fraktion) {
        this.identifier = identifier;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.fraktion = fraktion;
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
