package root.Logic.Persona;

import root.Logic.Game;
import root.Logic.Persona.Fraktionen.*;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Logic.Spieler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hauptrolle extends Rolle {
    public Fraktion fraktion = new Bürger();
    public boolean killing = false;

    @Override
    public Color getColor() {
        return fraktion.color;
    }

    public BonusrollenType getBonusrollenTypeInfo(Spieler requester) {
        return null;
    }

    public Zeigekarte isTötendInfo(Spieler requester) {
        if (killing) {
            return new Tötend();
        } else {
            return new Nicht_Tötend();
        }
    }

    public Zeigekarte getFraktionInfo() {
        return fraktion.zeigekarte;
    }

    public static Hauptrolle getDefaultHauptrolle() {
        return new Dorfbewohner();
    }

    public static void sortByFraktion(List<String> hauptrollen) {
        List<String> bürger = new ArrayList<>();
        List<String> werwölfe = new ArrayList<>();
        List<String> vampire = new ArrayList<>();
        List<String> schattenpriester = new ArrayList<>();
        List<String> überläufer = new ArrayList<>();

        for (String hauptrollenName : hauptrollen) {
            Hauptrolle hauptrolle = Game.game.findHauptrollePerName(hauptrollenName);

            switch (hauptrolle.fraktion.id) {
                case Bürger.ID:
                    bürger.add(hauptrollenName);
                    break;

                case Werwölfe.ID:
                    werwölfe.add(hauptrollenName);
                    break;

                case Vampire.ID:
                    vampire.add(hauptrollenName);
                    break;

                case SchattenpriesterFraktion.ID:
                    schattenpriester.add(hauptrollenName);
                    break;

                case ÜberläuferFraktion.ID:
                    überläufer.add(hauptrollenName);
                    break;

                default:
                    System.out.println("Unknown Fraktion: " + hauptrolle.fraktion.id);
            }
        }

        bürger.sort(String.CASE_INSENSITIVE_ORDER);
        werwölfe.sort(String.CASE_INSENSITIVE_ORDER);
        vampire.sort(String.CASE_INSENSITIVE_ORDER);
        schattenpriester.sort(String.CASE_INSENSITIVE_ORDER);
        überläufer.sort(String.CASE_INSENSITIVE_ORDER);

        hauptrollen.clear();

        hauptrollen.addAll(bürger);
        hauptrollen.addAll(werwölfe);
        hauptrollen.addAll(vampire);
        hauptrollen.addAll(schattenpriester);
        hauptrollen.addAll(überläufer);
    }
}
