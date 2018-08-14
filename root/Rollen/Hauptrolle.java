package root.Rollen;

import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrollen.Bürger.Dorfbewohner;

import java.awt.*;

public class Hauptrolle extends Rolle {
    public static Hauptrolle defaultHauptrolle = new Dorfbewohner();

    public boolean isKilling() {
        return false;
    }

    public Fraktion getFraktion() {
        return new Bürger();
    }

    public Color getFarbe() {
        return getFraktion().getFarbe();
    }
}
