package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;
import root.Persona.Fraktion;

/**
 * Created by Steve on 27.12.2017.
 */
public class StatementFraktion extends Statement {
    public String fraktion;

    public StatementFraktion(Fraktion fraktion) {
        this(fraktion.getBeschreibung(), fraktion.getTitle(), fraktion.getName(), fraktion.getStatementType());
    }

    public StatementFraktion(String beschreibung, String title, String fraktion, StatementType type) {
        this.beschreibung = beschreibung;
        this.title = title;
        this.fraktion = fraktion;
        this.type = type;
    }

    @Override
    public boolean isVisible() {
        return Fraktion.fraktionInNachtEnthalten(fraktion);
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

    public Fraktion getFraktion() {
        return Fraktion.findFraktion(fraktion);
    }
}
