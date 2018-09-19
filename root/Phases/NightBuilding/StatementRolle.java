package root.Phases.NightBuilding;

import root.Persona.Rolle;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Phases.NightBuilding.Constants.StatementType;
import root.mechanics.Opfer;

public class StatementRolle extends Statement {
    public String rolle;
    public boolean sammler;

    public StatementRolle(Rolle rolle) {
        this(rolle.getBeschreibung(), rolle.getTitle(), rolle.name, rolle.getStatementType());
    }

    public StatementRolle(String beschreibung, String title, String rolle, StatementType type) {
        this.beschreibung = beschreibung;
        this.title = title;
        this.rolle = rolle;
        this.type = type;
        this.sammler = Sammler.isSammlerRolle(rolle);
    }

    @Override
    public boolean isVisible() {
        return Rolle.rolleInNachtEnthalten(rolle);
    }

    @Override
    public boolean isLebend() {
        if (!sammler) {
            return Rolle.rolleLebend(rolle);
        } else {
            return Rolle.rolleLebend(Sammler.NAME);
        }
    }

    @Override
    public boolean isOpfer() {
        if (!sammler) {
            return Opfer.isOpferPerRolle(rolle);
        } else {
            return Opfer.isOpferPerRolle(Sammler.NAME);
        }
    }

    @Override
    public boolean isAktiv() {
        if (!sammler) {
            return Rolle.rolleAktiv(rolle);
        } else {
            return Rolle.rolleAktiv(Sammler.NAME);
        }
    }

    public Rolle getRolle() {
        return Rolle.findRolle(rolle);
    }
}
