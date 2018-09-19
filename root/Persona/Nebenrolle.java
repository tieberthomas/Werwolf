package root.Persona;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.NebenrollenType.Aktiv;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Persona.Rollen.Nebenrollen.Schatten;
import root.Persona.Rollen.Nebenrollen.Tarnumhang;
import root.Spieler;

import java.awt.*;

public class Nebenrolle extends Rolle {
    public static final Nebenrolle defaultNebenrolle = new Schatten();
    public static final Color defaultFarbe = new Color(240, 240, 240);

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(this.name);
            if (spieler != null) {
                spieler.nebenrolle = nebenrolle;
            }
        } catch (NullPointerException e) {
            System.out.println(this.name + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        try {
            Spieler spieler = game.findSpielerPerRolle(this.name);
            if (spieler != null) {
                return spieler.nebenrolle;
            } else {
                return this;
            }
        } catch (NullPointerException e) {
            System.out.println(this.name + " nicht gefunden");
        }

        return this;
    }

    public NebenrollenType getType() {
        return new Aktiv();
    }

    public boolean showTarnumhang(Nebenrolle requester, Spieler spieler) {
        return spieler != null && (spieler.nebenrolle.equals(Tarnumhang.NAME) || (playerIsSchamanin(spieler) && thisRolleIsNotBuerger(requester)));
    }

    private boolean playerIsSchamanin(Spieler player) {
        return player.hauptrolle.equals(Schamanin.NAME);
    }

    private boolean thisRolleIsNotBuerger(Nebenrolle requester) {
        Spieler spieler = game.findSpielerPerRolle(requester.name);

        return !spieler.hauptrolle.getFraktion().equals(new Bürger());
    }
}
