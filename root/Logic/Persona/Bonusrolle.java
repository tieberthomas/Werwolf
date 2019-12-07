package root.Logic.Persona;

import root.Frontend.Frame.MyFrame;
import root.Logic.Game;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Rollen.Bonusrollen.Schatten;
import root.Logic.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Logic.Spieler;

import java.awt.*;

public class Bonusrolle extends Rolle {
    public BonusrollenType type = new Passiv();

    public static final Bonusrolle DEFAULT_BONUSROLLE = new Schatten();
    public static final Color DEFAULT_COLOR = MyFrame.DEFAULT_BUTTON_COLOR;

    public void tauschen(Bonusrolle bonusrolle) {
        try {
            Spieler spieler = Game.game.findSpielerPerRolle(this.id);
            if (spieler != null) {
                spieler.bonusrolle = bonusrolle;
            }
        } catch (NullPointerException e) {
            System.out.println(this.name + " nicht gefunden");
        }
    }

    public Bonusrolle getTauschErgebnis() {
        try {
            Spieler spieler = Game.game.findSpielerPerRolle(this.id);
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
        Spieler getarnterSpieler = MissVerona.getarnterSpieler;
        Spieler requesterSpieler = Game.game.findSpielerPerRolle(this.id);
        return spieler != null && (spieler.bonusrolle.equals(Tarnumhang.ID) ||
                (spielerIsSchamanin(spieler) && thisRolleIsNotBuerger(requester))) ||
                requesterSpieler.equals(getarnterSpieler) || spieler.equals(getarnterSpieler);
    }

    private boolean spielerIsSchamanin(Spieler spieler) {
        return spieler.hauptrolle.equals(Schamanin.ID)
                || (spieler.hauptrolle.equals(Henker.ID) && Henker.fakeRolle.equals(Schamanin.ID))
                || (spieler.hauptrolle.equals(Wolfsmensch.ID) && Wolfsmensch.fakeRolle.equals(Schamanin.ID));
    }

    private boolean thisRolleIsNotBuerger(Bonusrolle requester) {
        Spieler spieler = Game.game.findSpielerPerRolle(requester.id);

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
