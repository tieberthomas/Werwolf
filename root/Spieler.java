package root;

import root.Persona.Bonusrolle;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.Schatten;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.mechanics.Game;

import java.awt.*;

public class Spieler {
    public static final Color ALIVE_BACKGROUND_COLOR = Color.WHITE;
    public static final Color DEAD_BACKGROUND_COLOR = Color.GRAY;

    public static Game game;

    public String name;
    public Hauptrolle hauptrolle;
    public Bonusrolle bonusrolle;
    public boolean lebend;
    public boolean aktiv;
    public boolean geschützt;
    public boolean ressurectable;

    public Spieler(String name) {
        this.name = name;
        this.lebend = true;
        this.aktiv = true;
        this.geschützt = false;
        this.ressurectable = true;
    }

    public Spieler(String name, String hauptrolleName, String bonusrolleName) {
        this(name);

        Hauptrolle hauptrolle = game.findHauptrolle(hauptrolleName);
        if (hauptrolle == null) {
            hauptrolle = new Dorfbewohner();
        }

        Bonusrolle bonusrolle = game.findBonusrolle(bonusrolleName);
        if (bonusrolle == null) {
            bonusrolle = new Schatten();
        }

        this.hauptrolle = hauptrolle;
        this.bonusrolle = bonusrolle;

        game.spieler.add(this);
    }

    public boolean equals(Spieler spieler) {
        return spieler != null && this.name.equals(spieler.name);
    }

    public boolean equals(String spielerName) {
        return this.name.equals(spielerName);
    }

    public Bonusrolle getBonusrolle() {
        return bonusrolle;
    }

    public BonusrollenType getBonusrollenType() {
        return bonusrolle.type;
    }

    public BonusrollenType getBonusrollenTypeInfo(Spieler requester) {
        BonusrollenType bonunsrollenInfo = bonusrolle.getBonusrollenTypeInfo();
        BonusrollenType hauptrollenInfo = hauptrolle.getBonusrollenTypeInfo(requester);

        if(hauptrollenInfo != null) {
            return  hauptrollenInfo;
        } else {
            return bonunsrollenInfo;
        }
    }

    public void setBonusrolle(Bonusrolle bonusrolle) {
        this.bonusrolle = bonusrolle;
    }
}
