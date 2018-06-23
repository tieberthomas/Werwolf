package root.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.mechanics.Opfer;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Chemiker extends Hauptrolle
{
    public static final String name = "Chemiker";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ResourcePath.CHEMIKER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;


    @Override
    public FrontendControl getDropdownOtions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN_WITH_SUGGESTIONS;

        frontendControl.content = findResurrectableOpfer();

        frontendControl.content.add("");

        return frontendControl;
    }

    @Override
    public String aktion(String chosenOption) {
        Opfer chosenOpfer = Opfer.findOpfer(chosenOption);
        if(chosenOpfer != null) {
            besucht = chosenOpfer.opfer;

            Opfer.deadVictims.remove(chosenOpfer);
            chosenOpfer.opfer.hauptrolle = new Werwolf();
        }

        return chosenOption;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    public ArrayList<String> findResurrectableOpfer() {
        ArrayList<String> resurrectableOpfer = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            String fraktionOpfer = currentOpfer.opfer.hauptrolle.getFraktion().getName();
            String fraktionTäter = currentOpfer.täter.hauptrolle.getFraktion().getName();
            String hauptrolleTäter = currentOpfer.täter.hauptrolle.getName();

            if (currentOpfer.opfer.ressurectable) {
                if(currentOpfer.fraktionsTäter && fraktionTäter.equals(Werwölfe.name) || hauptrolleTäter.equals(Wölfin.name)) {
                    if (!fraktionOpfer.equals(Werwölfe.name)) {
                        if (!resurrectableOpfer.contains(currentOpfer.opfer.name)) {
                            resurrectableOpfer.add(currentOpfer.opfer.name);
                        }
                    }
                }
            }
        }

        return resurrectableOpfer;
    }
}