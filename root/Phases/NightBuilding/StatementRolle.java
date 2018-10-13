package root.Phases.NightBuilding;

import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Konditorlehrling;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Phases.NightBuilding.Constants.StatementType;
import root.mechanics.Opfer;

public class StatementRolle extends Statement {
    public String rolle;
    public boolean sammler;
    public String sammlerBeschreibung;

    public StatementRolle(Rolle rolle) {
        this(rolle.statementIdentifier, rolle.statementTitle, rolle.statementBeschreibung, rolle.statementType, rolle.name);
    }

    public StatementRolle(String identifier, String title, String beschreibung, StatementType type, String rolle) {
        this.identifier = identifier;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.rolle = rolle;
        this.sammler = Sammler.isSammlerRolle(rolle);

        if (!this.identifier.equals(Konditorlehrling.STATEMENT_IDENTIFIER)) {
            this.sammlerBeschreibung = Sammler.beschreibungAddiditon + this.beschreibung;
        } else {
            this.sammlerBeschreibung = this.beschreibung.replace(Sammler.konditorlehrlingSearchString, Sammler.beschreibungAddiditonLowerCase + Sammler.konditorlehrlingSearchString);
        }

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
