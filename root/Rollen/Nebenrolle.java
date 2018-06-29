package root.Rollen;

import root.Rollen.Nebenrollen.*;
import root.Spieler;

import java.awt.*;
import java.util.ArrayList;

public class Nebenrolle extends Rolle
{
    public static final String AKTIV = "Aktiv";
    public static final String PASSIV = "Passiv";
    public static final String INFORMATIV = "Informativ";
    public static final String TARNUMHANG = "Tarnumhang";

    public static ArrayList<Nebenrolle> secondaryRoles = new ArrayList<>();
    public static ArrayList<Nebenrolle> secondaryRolesInGame = new ArrayList<>();
    public static Nebenrolle defaultNebenrolle = new Schatten();
    public static Color defaultFarbe = new Color(240,240,240);

    public static void generateAllAvailableSecondaryRoles(){
        secondaryRoles.add(new Analytiker());
        secondaryRoles.add(new Anästhesist());
        secondaryRoles.add(new Archivar());
        secondaryRoles.add(new Beschwörer());
        secondaryRoles.add(new Frisör());
        secondaryRoles.add(new Gefängniswärter());
        //Imitator
        secondaryRoles.add(new Konditor());
        secondaryRoles.add(new Konditorlehrling());
        secondaryRoles.add(new Lamm());
        secondaryRoles.add(new Nachbar());
        secondaryRoles.add(new Prostituierte());
        secondaryRoles.add(new ReinesLicht());
        secondaryRoles.add(new Schatten());
        secondaryRoles.add(new Schattenkutte());
        secondaryRoles.add(new Spion());
        secondaryRoles.add(new Tarnumhang());
        secondaryRoles.add(new Totengräber()); //ungetested
        secondaryRoles.add(new Vampirumhang());
        secondaryRoles.add(new Wachhund());
        secondaryRoles.add(new Wahrsager());
        secondaryRoles.add(new Wolfspelz());
    }

    public static ArrayList<String> getSecondaryRoleNames(){
        ArrayList<String> names = new ArrayList<String>();

        for(Nebenrolle nebenrolle : secondaryRoles) {
            names.add(nebenrolle.getName());
        }

        return names;
    }

    public static ArrayList<String> getSecondaryRoleInGameNames(){
        ArrayList<String> names = new ArrayList<String>();

        for(Nebenrolle nebenrolle : secondaryRolesInGame) {
            names.add(nebenrolle.getName());
        }

        return names;
    }

    public static ArrayList<String> getPossibleInGameMainRoleNames() {
        ArrayList<String> secondaryRoleInGameNames = getSecondaryRoleInGameNames();

        for(Nebenrolle nebenrolle : Rolle.mitteNebenrollen) {
            secondaryRoleInGameNames.remove(nebenrolle.getName());
        }

        return  secondaryRoleInGameNames;
    }

    public static ArrayList<String> getStillAvailableSecondaryRoleNames() {
        ArrayList<Nebenrolle> secondaryRoles = (ArrayList)secondaryRolesInGame.clone();
        ArrayList<String> names = new ArrayList<String>();

        for(Spieler spieler : Spieler.spieler) {
            secondaryRoles.remove(spieler.nebenrolle);
        }

        for(Nebenrolle nebenrolle : secondaryRoles){
            names.add(nebenrolle.getName());
        }

        return names;
    }

    public static Nebenrolle findNebenrolle(String wantedName) {
        for(Nebenrolle nebenrolle : secondaryRoles) {
            if(nebenrolle.getName().equals(wantedName))
                return nebenrolle;
        }

        return null;
    }

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = Spieler.findSpielerPerRolle(this.getName());
            spieler.nebenrolle = nebenrolle;
        }catch (NullPointerException e) {
            System.out.println(this.getName() + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        try {
            Spieler spieler = Spieler.findSpielerPerRolle(this.getName());

            return spieler.nebenrolle;
        }catch (NullPointerException e) {
            System.out.println(this.getName() + " nicht gefunden");
        }

        return this;
    }

    public String getType() {
        return AKTIV;
    }
}
