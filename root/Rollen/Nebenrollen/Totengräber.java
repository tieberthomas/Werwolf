package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Totengräber extends Nebenrolle
{
    public static final String name = "Totengräber";
    public static final String imagePath = ResourcePath.TOTENGRÄBER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;

    @Override
    public String aktion(String chosenOption) {
        Nebenrolle chosenNebenrolle = Nebenrolle.findNebenrolle(chosenOption);
        if (chosenNebenrolle != null) {
            try {
                Spieler spielerNebenrolle = Spieler.findSpielerPerRolle(chosenNebenrolle.getName());
                spielerNebenrolle.nebenrolle = new Schatten();

                Spieler spielerTotengräber = Spieler.findSpielerPerRolle(name);
                spielerTotengräber.nebenrolle = chosenNebenrolle;
            }catch (NullPointerException e) {
                System.out.println(name + " nicht gefunden");
            }
        }

        return null;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return new FrontendControl(FrontendControl.DROPDOWN_WITH_SUGGESTIONS, Spieler.getDeadNebenrollen());
    }

    @Override
    public String getName() {
        return name;
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
}
