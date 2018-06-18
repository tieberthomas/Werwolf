package root.Rollen.Fraktionen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Hauptrollen.Bürger.Riese;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Nebenrollen.Schattenkutte;
import root.Rollen.Rolle;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Steve on 25.11.2017.
 */
public class Schattenpriester_Fraktion extends Fraktion
{
    public static final String name = "Schattenpriester";
    public static final Color farbe = Color.lightGray;
    public static final String imagePath = ResourcePath.SCHATTENPRIESTER_ICON;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getFarbe() { return farbe; }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    public static void wiederbeleben(Opfer opfer) {
        Opfer.deadVictims.remove(opfer);

        if (!opfer.opfer.nebenrolle.getName().equals(Schattenkutte.name)) {
            opfer.opfer.hauptrolle = new Schattenpriester();
            ((Schattenpriester)opfer.opfer.hauptrolle).neuster = true;
        }
        opfer.opfer.nebenrolle = new Schatten();
    }

    public static ArrayList<String> getRessurectableVictimsOrNone() {
        ArrayList<String> erweckbareOrNon = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            String fraktionOpfer = currentOpfer.opfer.hauptrolle.getFraktion().getName();
            if (currentOpfer.opfer.nebenrolle.getName().equals(Schattenkutte.name) ||
                    (currentOpfer.opfer.ressurectable && !fraktionOpfer.equals(Schattenpriester_Fraktion.name))) {
                Rolle täter = currentOpfer.täter.hauptrolle;
                if(!erweckbareOrNon.contains(currentOpfer.opfer.name) && !täter.getName().equals(Riese.name)) {
                    erweckbareOrNon.add(currentOpfer.opfer.name);
                }
            }
        }

        erweckbareOrNon.add("");

        return erweckbareOrNon;
    }
}
