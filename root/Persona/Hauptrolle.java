package root.Persona;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Spieler;

import java.awt.*;

public class Hauptrolle extends Rolle {
    public Fraktion fraktion = new Bürger();
    public boolean killing = false;

    @Override
    public Color getColor() {
        return fraktion.color;
    }

    public BonusrollenType getBonusrollenTypeInfo(Spieler requester) {
        return null;
    }

    public Zeigekarte isTötendInfo(Spieler requester) {
        if (killing) {
            return new Tötend();
        } else {
            return new Nicht_Tötend();
        }
    }

    public Zeigekarte getFraktionInfo() {
        return fraktion.zeigekarte;
    }

    public static Hauptrolle getDefaultHauptrolle() {
        return new Dorfbewohner();
    }
}
