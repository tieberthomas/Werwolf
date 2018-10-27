package root;

import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.Schatten;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.TötendInformationType;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.mechanics.Game;

import java.awt.*;

import static root.Persona.Rollen.Constants.TötendInformationType.TARNUMHANG;

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

    public String getName() {
        return name;
    }

    public BonusrollenType getBonusrollenType() {
        return bonusrolle.type;
    }

    public BonusrollenType getBonusrollenTypeInfo(Spieler requester) {
        BonusrollenType bonunsrollenInfo = bonusrolle.getBonusrollenTypeInfo();
        BonusrollenType hauptrollenInfo = hauptrolle.getBonusrollenTypeInfo(requester);

        if (hauptrollenInfo != null) {
            return hauptrollenInfo;
        } else {
            return bonunsrollenInfo;
        }
    }

    public void setBonusrolle(Bonusrolle bonusrolle) {
        this.bonusrolle = bonusrolle;
    }

    public boolean isTötend() {
        return hauptrolle.killing;
    }

    public TötendInformationType isTötendInfo(Spieler requester) {
        TötendInformationType hauptrolleIsTötend = hauptrolle.isTötendInfo(requester);
        TötendInformationType bonunsrolleIsTötend = bonusrolle.isTötendInfo();

        if (TARNUMHANG.equals(bonunsrolleIsTötend)) {
            return bonunsrolleIsTötend;
        } else {
            return hauptrolleIsTötend;
        }
    }

    public Fraktion getFraktion() {
        return hauptrolle.fraktion;
    }

    public Zeigekarte getFraktionInfo() {
        Zeigekarte hauptrollenFraktion = hauptrolle.getFraktionInfo();
        Zeigekarte bonunsrollenFraktion = bonusrolle.getFraktionInfo();

        if (bonunsrollenFraktion != null) {
            return bonunsrollenFraktion;
        } else {
            return hauptrollenFraktion;
        }
    }
}
