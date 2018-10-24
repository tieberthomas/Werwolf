package root.Persona;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Spieler;

import java.awt.*;

public class Hauptrolle extends Rolle {
    public Fraktion fraktion = new Bürger();
    public boolean killing = false;

    public static final Hauptrolle DEFAULT_HAUPTROLLE = new Dorfbewohner();

    @Override
    public Color getColor() {
        return fraktion.color;
    }

    public BonusrollenType getBonusrollenTypeInfo(Spieler requester) {
        return null;
    }
}
