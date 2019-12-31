package root.Logic;

import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Bonusrollen.Analytiker;
import root.Logic.Persona.Rollen.Bonusrollen.Schatten;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.InformationsCluster.BonusrollenInfo;
import root.Logic.Persona.Rollen.Constants.InformationsCluster.FraktionsInfo;
import root.Logic.Persona.Rollen.Constants.InformationsCluster.TötendInfo;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.MissVerona;

import java.awt.*;

public class Spieler implements Comparable {
    public static final Color ALIVE_BACKGROUND_COLOR = Color.WHITE;
    public static final Color DEAD_BACKGROUND_COLOR = Color.GRAY;

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

        Hauptrolle hauptrolle = Game.game.findHauptrollePerName(hauptrolleName);
        if (hauptrolle == null) {
            hauptrolle = new Dorfbewohner();
        }

        Bonusrolle bonusrolle = Game.game.findBonusrollePerName(bonusrolleName);
        if (bonusrolle == null) {
            bonusrolle = new Schatten();
        }

        this.hauptrolle = hauptrolle;
        this.bonusrolle = bonusrolle;

        Game.game.spieler.add(this);
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

        BonusrollenType information;

        Spieler getarnterSpieler = MissVerona.getarnterSpieler;

        if (hauptrollenInfo != null || this.equals(getarnterSpieler) || requester.equals(getarnterSpieler)) {
            information = new Tarnumhang_BonusrollenType();
        } else {
            information = bonunsrollenInfo;
        }

        if (this.equals(LadyAleera.gefälschterSpieler)) {
            return BonusrollenInfo.getWrongInformation(information);
        } else {
            return information;
        }
    }

    public void setBonusrolle(Bonusrolle bonusrolle) {
        this.bonusrolle = bonusrolle;
    }

    public boolean isTötend() {
        return hauptrolle.killing;
    }

    public Zeigekarte isTötendInfo(Spieler requester) {
        Zeigekarte hauptrolleIsTötend = hauptrolle.isTötendInfo(requester);
        Zeigekarte bonunsrolleIsTötend = bonusrolle.isTötendInfo();

        Zeigekarte information;

        Spieler getarnterSpieler = MissVerona.getarnterSpieler;

        if (bonunsrolleIsTötend instanceof Tarnumhang_BonusrollenType || this.equals(getarnterSpieler) || requester.equals(getarnterSpieler)) {
            information = new Tarnumhang_BonusrollenType();
        } else {
            information = hauptrolleIsTötend;
        }

        if (this.equals(LadyAleera.gefälschterSpieler)) {
            return TötendInfo.getWrongInformation(information);
        } else {
            return information;
        }
    }

    public Fraktion getFraktion() {
        return hauptrolle.fraktion;
    }

    public Zeigekarte getFraktionInfo(Spieler requester) {
        Zeigekarte hauptrollenFraktion = hauptrolle.getFraktionInfo();
        Zeigekarte bonunsrollenFraktion = bonusrolle.getFraktionInfo();

        Zeigekarte information;

        Spieler getarnterSpieler = MissVerona.getarnterSpieler;

        if (this.equals(getarnterSpieler) || requester.equals(getarnterSpieler)) {
            return new Tarnumhang_BonusrollenType();
        }

        if (bonunsrollenFraktion != null) {
            information = bonunsrollenFraktion;
        } else {
            information = hauptrollenFraktion;
        }

        if (this.equals(LadyAleera.gefälschterSpieler) && !requester.bonusrolle.equals(Analytiker.ID)) {
            return FraktionsInfo.getWrongInformation(information);
        } else {
            return information;
        }
    }

    @Override
    public int compareTo(Object spieler) {
        return this.name.compareToIgnoreCase(((Spieler) spieler).name);
    }
}
