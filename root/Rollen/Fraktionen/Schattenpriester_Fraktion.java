package root.Rollen.Fraktionen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktion;
import root.Rollen.Hauptrollen.Bürger.Riese;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Nebenrollen.Schattenkutte;
import root.Rollen.Rolle;
import root.Spieler;
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
    public static final String imagePath = ImagePath.SCHATTENPRIESTER_ICON;

    public static int deadSchattenPriester = 0;

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenOpfer = game.findSpieler(chosenOption);
        if(chosenOpfer != null) {
            Opfer.removeVictim(chosenOpfer);

            if (!chosenOpfer.nebenrolle.getName().equals(Schattenkutte.name)) {
                chosenOpfer.hauptrolle = new Schattenpriester();
                ((Schattenpriester)chosenOpfer.hauptrolle).neuster = true;
            }
            chosenOpfer.nebenrolle = new Schatten();
        }
    }


    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_LIST;
        frontendControl.strings = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            String fraktionOpfer = currentOpfer.opfer.hauptrolle.getFraktion().getName();
            if (currentOpfer.opfer.nebenrolle.getName().equals(Schattenkutte.name) ||
                    (currentOpfer.opfer.ressurectable && !fraktionOpfer.equals(Schattenpriester_Fraktion.name))) {
                Rolle täter = currentOpfer.täter.hauptrolle;
                if(!frontendControl.strings.contains(currentOpfer.opfer.name) && !täter.getName().equals(Riese.name)) {
                    frontendControl.strings.add(currentOpfer.opfer.name);
                }
            }
        }

        frontendControl.strings.add("");

        return frontendControl;
    }

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
}
