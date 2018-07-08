package root.Rollen.Hauptrollen.Überläufer;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Überläufer_Fraktion;
import root.Rollen.Hauptrolle;
import root.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Rollen.Rolle;
import root.Spieler;

public class Überläufer extends Hauptrolle
{
    public static final String name = "Überläufer";
    public static Fraktion fraktion = new Überläufer_Fraktion();
    public static final String imagePath = ResourcePath.ÜBERLÄUFER_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;

    @Override
    public FrontendControl getDropdownOptions() {
        return new FrontendControl(FrontendControl.DROPDOWN_LIST, Spieler.getDeadHauptrollen());
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Hauptrolle chosenHauptrolle = Hauptrolle.findHauptrolle(chosenOption);
        if (chosenHauptrolle != null) {
            try {
                Spieler spielerHauptrolle = Spieler.findSpielerPerRolle(chosenHauptrolle.getName());
                chosenHauptrolle = spielerHauptrolle.hauptrolle;
                spielerHauptrolle.hauptrolle = new Dorfbewohner();

                Spieler spielerÜberläufer = Spieler.findSpielerPerRolle(name);
                spielerÜberläufer.hauptrolle = chosenHauptrolle;

                Rolle.mitteHauptrollen.remove(chosenHauptrolle);
                Rolle.mitteHauptrollen.add(this);
            }catch (NullPointerException e) {
                System.out.println(name + " nicht gefunden");
            }
        }
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
}