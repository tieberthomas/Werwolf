package root.Logic.Phases.Statement.StatementDependency;

import root.Logic.Persona.Fraktion;

public class StatementDependencyFraktion extends StatementDependency {
    public Fraktion fraktion;

    public StatementDependencyFraktion(Fraktion fraktion) {
        this.fraktion = fraktion;
    }

    @Override
    public boolean isVisibleNow() {
        return Fraktion.fraktionContainedInNight(fraktion.id);
    }

    @Override
    public boolean isLebendNow() {
        return Fraktion.fraktionLebend(fraktion.id);
    }

    @Override
    public boolean isOpferNow() {
        return Fraktion.fraktionOpfer(fraktion.id);
    }

    @Override
    public boolean isAktivNow() {
        return Fraktion.fraktionAktiv(fraktion.id);
    }

    @Override
    public boolean isAufgebrauchtNow() {
        return false;
    }
}
