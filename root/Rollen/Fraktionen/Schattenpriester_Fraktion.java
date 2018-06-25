package root.Rollen.Fraktionen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Hauptrollen.Bürger.Riese;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Nebenrollen.Schattenkutte;
import root.Rollen.Rolle;
import root.mechanics.Opfer;

import java.awt.*;

/**
 * Created by Steve on 25.11.2017.
 */
public class Schattenpriester_Fraktion extends Fraktion
{
    public static final String name = "Schattenpriester";
    public static final Color farbe = Color.lightGray;
    public static final String imagePath = ResourcePath.SCHATTENPRIESTER_ICON;

    @Override
    public String processChosenOption(String chosenOption) {
        Opfer chosenOpfer = Opfer.findOpfer(chosenOption);
        if(chosenOpfer != null) {
            Opfer.deadVictims.remove(chosenOpfer);

            if (!chosenOpfer.opfer.nebenrolle.getName().equals(Schattenkutte.name)) {
                chosenOpfer.opfer.hauptrolle = new Schattenpriester();
                ((Schattenpriester)chosenOpfer.opfer.hauptrolle).neuster = true;
            }
            chosenOpfer.opfer.nebenrolle = new Schatten();
        }

        return chosenOption;
    }


    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_WITH_SUGGESTIONS;

        for (Opfer currentOpfer : Opfer.deadVictims) {
            String fraktionOpfer = currentOpfer.opfer.hauptrolle.getFraktion().getName();
            if (currentOpfer.opfer.nebenrolle.getName().equals(Schattenkutte.name) ||
                    (currentOpfer.opfer.ressurectable && !fraktionOpfer.equals(Schattenpriester_Fraktion.name))) {
                Rolle täter = currentOpfer.täter.hauptrolle;
                if(!frontendControl.content.contains(currentOpfer.opfer.name) && !täter.getName().equals(Riese.name)) {
                    frontendControl.content.add(currentOpfer.opfer.name);
                }
            }
        }

        frontendControl.content.add("");

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
