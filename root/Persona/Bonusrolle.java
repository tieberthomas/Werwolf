package root.Persona;

import root.Frontend.Frame.MyFrame;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Bonusrollen.Schatten;
import root.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Spieler;

import java.awt.*;

public class Bonusrolle extends Rolle {
    public BonusrollenType type = new Passiv();

    public static final Bonusrolle DEFAULT_BONUSROLLE = new Schatten();
    public static final Color DEFAULT_COLOR = MyFrame.DEFAULT_BUTTON_COLOR;

    public void tauschen(Bonusrolle bonusrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(this.name);
            if (spieler != null) {
                spieler.bonusrolle = bonusrolle;
            }
        } catch (NullPointerException e) {
            System.out.println(this.name + " nicht gefunden");
        }
    }

    public Bonusrolle getTauschErgebnis() {
        try {
            Spieler spieler = game.findSpielerPerRolle(this.name);
            if (spieler != null) {
                return spieler.bonusrolle;
            } else {
                return this;
            }
        } catch (NullPointerException e) {
            System.out.println(this.name + " nicht gefunden");
        }

        return this;
    }

    public boolean showTarnumhang(Bonusrolle requester, Spieler spieler) {
        return spieler != null && (spieler.bonusrolle.equals(Tarnumhang.NAME) || (spielerIsSchamanin(spieler) && thisRolleIsNotBuerger(requester)));
    }

    private boolean spielerIsSchamanin(Spieler spieler) {
        return spieler.hauptrolle.equals(Schamanin.NAME);
    }

    private boolean thisRolleIsNotBuerger(Bonusrolle requester) {
        Spieler spieler = game.findSpielerPerRolle(requester.name);

        return !spieler.hauptrolle.fraktion.equals(new Bürger());
    }

    public BonusrollenType getBonusrollenTypeInfo() {
        return type;
    }

    public Zeigekarte isTötendInfo() {
        return null;
    }

    public Zeigekarte getFraktionInfo() {
        return null;
    }
}
