package root.Phases.NightBuilding;

import root.Persona.Rolle;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Phases.NightBuilding.Constants.StatementType;
import root.mechanics.Opfer;

public class StatementRolle extends Statement {
    public String rolle;
    public boolean sammler;

    public StatementRolle(Rolle rolle) {
        this(rolle.statementBeschreibung, rolle.statementTitle, rolle.name, rolle.statementType);
    }

    public StatementRolle(String beschreibung, String title, String rolle, StatementType type) {
        this.beschreibung = beschreibung;
        this.title = title;
        this.rolle = rolle;
        this.type = type;
        this.sammler = Sammler.isSammlerRolle(rolle);
    }

    @Override
    public boolean isVisibleNow() {
        return Rolle.rolleInNachtEnthalten(rolle);
    }

    @Override
    public boolean isLebendNow() {
        if (!sammler) {
            return Rolle.rolleLebend(rolle);
        } else {
            return Rolle.rolleLebend(Sammler.NAME);
        }
    }

    @Override
    public boolean isOpferNow() {
        if (!sammler) {
            return Opfer.isOpferPerRolle(rolle);
        } else {
            return Opfer.isOpferPerRolle(Sammler.NAME);
        }
    }

    @Override
    public boolean isAktivNow() {
        if (!sammler) {
            return Rolle.rolleAktiv(rolle);
        } else {
            return Rolle.rolleAktiv(Sammler.NAME);
        }
    }

    public boolean isAufgebrauchtNow() {
        return Rolle.rolleAufgebraucht(rolle);
    }

    public Rolle getRolle() {
        return Rolle.findRolle(rolle);
    }
}
