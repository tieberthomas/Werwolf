package root.Phases;

/**
 * Created by Steve on 27.12.2017.
 */
public class StatementIndie extends Statement {
    public StatementIndie(String beschreibung, String titel, boolean visible) {
        this.beschreibung = beschreibung;
        this.titel = titel;
        this.visible = visible;
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
