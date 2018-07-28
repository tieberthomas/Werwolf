package root.Phases;

/**
 * Created by Steve on 27.12.2017.
 */
public class StatementProgramm extends Statement {
    public StatementProgramm(String beschreibung) {
        this.beschreibung = beschreibung;
        this.type = Statement.PROGRAMM;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isLebend() {
        return true;
    }

    @Override
    public boolean isOpfer() {
        return false;
    }

    @Override
    public boolean isAktiv() {
        return true;
    }
}
