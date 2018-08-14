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

    public Spieler(String name)
    {
        this.name = name;
        this.lebend = true;
        this.aktiv = true;
        this.geschützt = false;
        this.ressurectable = true;
    }

    public Spieler(String name, String hauptrolleName, String nebenrolleName) {
        Spieler newPlayer = new Spieler(name);
        game.spieler.add(newPlayer);

        Hauptrolle hauptrolle = Hauptrolle.findHauptrolle(hauptrolleName);
        if(hauptrolle==null)
            hauptrolle = new Dorfbewohner();
        newPlayer.hauptrolle = hauptrolle;

        Nebenrolle nebenrolle = Nebenrolle.findNebenrolle(nebenrolleName);
        if(nebenrolle==null)
            nebenrolle = new Schatten();
        newPlayer.nebenrolle = nebenrolle;
        Nebenrolle.secondaryRolesInGame.remove(nebenrolle);
        newPlayer.nebenrolle = newPlayer.nebenrolle.getTauschErgebnis();
    }

    /*



    public static boolean spielerExists(String name) {
        return findSpieler(name) != null;
    }

    public static Spieler findSpielerPerRolle(String name) {
        for(Nebenrolle nebenrolle : Rolle.mitteNebenrollen)
        {
            if(nebenrolle.getName().equals(name)) {
                return findSpielerPerRolle(Sammler.name);
            }
        }

        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }


*/
}
