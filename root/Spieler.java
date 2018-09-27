package root;

import root.Persona.Bonusrolle;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Persona.Rollen.Nebenrollen.Schatten;
import root.mechanics.Game;

public class Spieler {
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

    public Spieler(String name, String hauptrolleName, String nebenrolleName) {
        this(name);

        Hauptrolle hauptrolle = game.findHauptrolle(hauptrolleName);
        if (hauptrolle == null) {
            hauptrolle = new Dorfbewohner();
        }

        Bonusrolle bonusrolle = game.findNebenrolle(nebenrolleName);
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
        return spielerName != null && this.name.equals(spielerName);
    }
}
