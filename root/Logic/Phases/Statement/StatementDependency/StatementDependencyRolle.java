package root.Logic.Phases.Statement.StatementDependency;

import root.Logic.KillLogic.Opfer;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Sammler;

public class StatementDependencyRolle extends StatementDependency {
    public Rolle rolle;

    public StatementDependencyRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    @Override
    public boolean isVisibleNow() {
        return Rolle.rolleContainedInNight(rolle.id);
    }

    @Override
    public boolean isLebendNow() {
        if (!Sammler.isSammlerRolle(rolle.id)) {
            return Rolle.rolleLebend(rolle.id);
        } else {
            return Rolle.rolleLebend(Sammler.ID);
        }
    }

    @Override
    public boolean isOpferNow() {
        if (!Sammler.isSammlerRolle(rolle.id)) {
            return Opfer.isOpferPerRolle(rolle.id);
        } else {
            return Opfer.isOpferPerRolle(Sammler.ID);
        }
    }

    @Override
    public boolean isAktivNow() {
        if (!Sammler.isSammlerRolle(rolle.id)) {
            return Rolle.rolleAktiv(rolle.id);
        } else {
            return Rolle.rolleAktiv(Sammler.ID);
        }
    }

    @Override
    public boolean isAufgebrauchtNow() {
        return Rolle.rolleAufgebraucht(rolle.id);
    }
}
