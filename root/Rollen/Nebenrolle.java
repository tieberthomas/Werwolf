package root.Rollen;

import root.Rollen.Nebenrollen.Schatten;
import root.Spieler;

import java.awt.*;

public class Nebenrolle extends Rolle {
    public static Nebenrolle defaultNebenrolle = new Schatten();
    public static Color defaultFarbe = new Color(240,240,240);

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(this.getName());
            if(spieler!=null) {
                spieler.nebenrolle = nebenrolle;
            }
        }catch (NullPointerException e) {
            System.out.println(this.getName() + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        try {
            Spieler spieler = game.findSpielerPerRolle(this.getName());
            if(spieler!=null) {
                return spieler.nebenrolle;
            } else {
                return this;
            }
        }catch (NullPointerException e) {
            System.out.println(this.getName() + " nicht gefunden");
        }

        return this;
    }

    public NebenrollenTyp getType() {
        return NebenrollenTyp.AKTIV;
    }


}
