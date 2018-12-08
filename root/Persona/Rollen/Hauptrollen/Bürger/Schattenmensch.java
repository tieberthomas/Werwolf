package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.ReineSeele;
import root.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Game;

public class Schattenmensch extends Hauptrolle {
    public static final String ID = "ID_Schattenmensch";
    public static final String NAME = "Schattenmensch";
    public static final String IMAGE_PATH = ImagePath.SCHATTENMENSCH_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static boolean shallBeTransformed = false;
    public static boolean changeReineSeele = false;

    public Schattenmensch() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.numberOfPossibleInstances = 1;
    }

    public static void transform() {
        Spieler schattenmenschSpieler = Game.game.findSpielerPerRolle(ID);
        if (schattenmenschSpieler != null) {
            Schattenpriester schattenpriester = new Schattenpriester(); //TODO ist new hier sinnvoll?
            schattenpriester.neuster = true;
            schattenmenschSpieler.hauptrolle = schattenpriester;
            if(changeReineSeele) {
                ReineSeele reineSeele = new ReineSeele();
                reineSeele.dayInvincibility = false;
                schattenmenschSpieler.bonusrolle = reineSeele;
            }
            SchattenpriesterFraktion.spielerToChangeCards = schattenmenschSpieler;
        }

        shallBeTransformed = false; //TODO move into cleanup after death
    }
}
