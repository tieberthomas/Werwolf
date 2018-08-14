package root;

import root.Rollen.Hauptrolle;
import root.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Rollen.Nebenrolle;
import root.Rollen.Nebenrollen.Schatten;
import root.mechanics.Game;

/**
 * Created by Steve on 12.11.2017.
 */
public class Spieler
{
    public static Game game;

    public String name;
    public Hauptrolle hauptrolle;
    public Nebenrolle nebenrolle;
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
        if(hauptrolle==null) {
            hauptrolle = new Dorfbewohner();
        }

        Nebenrolle nebenrolle = game.findNebenrolle(nebenrolleName);
        if(nebenrolle==null) {
            nebenrolle = new Schatten();
        }

        this.hauptrolle = hauptrolle;
        this.nebenrolle = nebenrolle;

        game.spieler.add(this);
    }
}
