package root.Persona;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;

import java.awt.*;

public class Hauptrolle extends Rolle {
    public Fraktion fraktion = new Bürger();
    public boolean killing = false;

    public static Hauptrolle defaultHauptrolle = new Dorfbewohner();

    @Override
    public Color getColor() {
        return fraktion.color;
    }
}
