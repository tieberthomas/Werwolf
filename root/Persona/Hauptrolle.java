package root.Persona;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.TötendInformationType;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Spieler;

import java.awt.*;

import static root.Persona.Rollen.Constants.TötendInformationType.NICHT_TÖTEND;
import static root.Persona.Rollen.Constants.TötendInformationType.TÖTEND;

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

    public TötendInformationType isTötendInfo(Spieler requester) {
        if (killing) {
            return TÖTEND;
        } else {
            return NICHT_TÖTEND;
        }
    }
}
