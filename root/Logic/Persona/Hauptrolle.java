package root.Logic.Persona;

import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Logic.Spieler;

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
