package root.Persona;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;

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
