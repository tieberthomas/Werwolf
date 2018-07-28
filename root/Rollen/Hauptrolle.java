package root.Rollen;

import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Hauptrollen.Bürger.*;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Rollen.Hauptrollen.Werwölfe.*;
import root.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Spieler;

import java.awt.*;
import java.util.ArrayList;

public class Hauptrolle extends Rolle
{
    public static ArrayList<Hauptrolle> mainRoles = new ArrayList<>();
    public static ArrayList<Hauptrolle> mainRolesInGame = new ArrayList<>();

    public static Hauptrolle defaultHauptrolle = new Dorfbewohner();

    public boolean isKilling() {
        return false;
    }

    public Fraktion getFraktion(){
        return new Bürger();
    }

    public static void generateAllAvailableMainRoles(){
        mainRoles.add(new Bestienmeister());
        mainRoles.add(new Bruder());
        mainRoles.add(new Buchhalter());
        mainRoles.add(new Dorfbewohner());
        mainRoles.add(new GuteHexe());
        mainRoles.add(new HoldeMaid());
        mainRoles.add(new Orakel());
        mainRoles.add(new Riese());
        mainRoles.add(new Sammler());
        mainRoles.add(new Seherin());
        mainRoles.add(new Späher());
        mainRoles.add(new Wirt());
        mainRoles.add(new Schattenpriester());
        mainRoles.add(new GrafVladimir());
        mainRoles.add(new LadyAleera());
        mainRoles.add(new MissVerona());
        mainRoles.add(new Alphawolf());
        mainRoles.add(new Blutwolf());
        mainRoles.add(new BöseHexe());
        mainRoles.add(new Chemiker());
        mainRoles.add(new Werwolf());
        mainRoles.add(new Wölfin());
        mainRoles.add(new Überläufer());
    }

    public static ArrayList<String> getMainRolesAlive(){
        ArrayList<String> names = new ArrayList<String>();

        for (Hauptrolle currentHauptrolle : mainRoles) {
            for(Spieler currentSpieler : Spieler.spieler) {
                if(currentSpieler.hauptrolle.getName().equals(currentHauptrolle.getName()) && Rolle.rolleLebend(currentSpieler.hauptrolle.getName())) {
                    names.add(currentSpieler.hauptrolle.getName());
                }
            }
        }

        return names;
    }

    public static ArrayList<String> getMainRoleNames(){
        ArrayList<String> names = new ArrayList<String>();

        for(Hauptrolle hauptrolle : mainRoles) {
            names.add(hauptrolle.getName());
        }

        return names;
    }

    public static ArrayList<String> getMainRoleInGameNames(){
        ArrayList<String> names = new ArrayList<String>();

        for(Hauptrolle hauptrolle : mainRolesInGame) {
            names.add(hauptrolle.getName());
        }

        return names;
    }

    public static ArrayList<String> getStillAvailableMainRoleNames() {
        ArrayList<Hauptrolle> mainroles = (ArrayList)mainRolesInGame.clone();
        ArrayList<String> names = new ArrayList<String>();

        for(Spieler spieler : Spieler.spieler) {
            mainroles.remove(spieler.hauptrolle);
        }

        for(Hauptrolle hauptrolle : mainroles){
            names.add(hauptrolle.getName());
        }

        return names;
    }

    public static ArrayList<String> getPossibleInGameMainRoleNames() {
        ArrayList<String> mainRolesInGame = getMainRoleInGameNames();

        for(Hauptrolle hauptrolle : Rolle.mitteHauptrollen) {
            if(!hauptrolle.getName().equals(Schattenpriester.name)) {
                mainRolesInGame.remove(hauptrolle.getName());
            }
        }

        for(int i=0; i< Schattenpriester_Fraktion.deadSchattenPriester; i++) {
            mainRolesInGame.remove(Schattenpriester.name);
        }

        return  mainRolesInGame;
    }

    public static Hauptrolle findHauptrolle(String wantedName) {
        for(Hauptrolle hauptrolle : mainRoles) {
            if(hauptrolle.getName().equals(wantedName))
                return hauptrolle;
        }

        return null;
    }

    public Color getFarbe() {
        return getFraktion().getFarbe();
    }
}
