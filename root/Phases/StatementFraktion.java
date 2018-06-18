package root.Phases;

import root.Rollen.Fraktion;

/**
 * Created by Steve on 27.12.2017.
 */
public class StatementFraktion extends Statement {
    String fraktion;

    public StatementFraktion(String beschreibung, String titel, String fraktion, boolean visible) {
        this.beschreibung = beschreibung;
        this.titel = titel;
        this.fraktion = fraktion;
        this.visible = visible;
    }

    @Override
    public boolean isLebend() {
        return Fraktion.fraktionLebend(fraktion);
    }

    @Override
    public boolean isOpfer() {
        return Fraktion.fraktionOpfer(fraktion);
    }

    @Override
    public boolean isAktiv() {
        return Fraktion.fraktionAktiv(fraktion);
    }
}
