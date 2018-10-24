package root.Phases.NightBuilding.StatementDependancy;

import root.Persona.Rolle;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.mechanics.Opfer;

public class StatementDependencyRolle extends StatementDependency {
    public Rolle rolle;

    public StatementDependencyRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    @Override
    public boolean isVisibleNow() {
        return Rolle.rolleContainedInNight(rolle.name);
    }

    @Override
    public boolean isLebendNow() {
        if (!Sammler.isSammlerRolle(rolle.name)) {
            return Rolle.rolleLebend(rolle.name);
        } else {
            return Rolle.rolleLebend(Sammler.NAME);
        }
    }

    @Override
    public boolean isOpferNow() {
        if (!Sammler.isSammlerRolle(rolle.name)) {
            return Opfer.isOpferPerRolle(rolle.name);
        } else {
            return Opfer.isOpferPerRolle(Sammler.NAME);
        }
    }

    @Override
    public boolean isAktivNow() {
        if (!Sammler.isSammlerRolle(rolle.name)) {
            return Rolle.rolleAktiv(rolle.name);
        } else {
            return Rolle.rolleAktiv(Sammler.NAME);
        }
    }

    @Override
    public boolean isAufgebrauchtNow() {
        return Rolle.rolleAufgebraucht(rolle.name);
    }
}
