package root.Phases.NightBuilding;

import root.Rollen.Hauptrollen.Bürger.Sammler;
import root.Rollen.Rolle;
import root.mechanics.Opfer;

/**
 * Created by Steve on 27.12.2017.
 */
public class StatementRolle extends Statement {
    public String rolle;
    public boolean sammler;

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
        if(!sammler) {
            return Rolle.rolleLebend(rolle);
        } else {
            return Rolle.rolleLebend(Sammler.name);
        }
    }

    @Override
    public boolean isOpfer() {
        if(!sammler) {
            return Opfer.isOpferPerRolle(rolle);
        } else {
            return Opfer.isOpferPerRolle(Sammler.name);
        }
    }

    @Override
    public boolean isAktiv() {
        if(!sammler) {
            return Rolle.rolleAktiv(rolle);
        } else {
            return Rolle.rolleAktiv(Sammler.name);
        }
    }

    public Rolle getRolle() {
        return Rolle.findRolle(rolle);
    }
}
