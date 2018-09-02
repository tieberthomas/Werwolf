package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Nebenrollen.*;
import root.Spieler;

public class Seherin extends Hauptrolle {
    public static final String TARNUMHANG_TITLE = "Tarnumhang";

    public static String title = "Spieler wählen";
    public static final String beschreibung = "Seherin erwacht und lässt sich Auskunft über die Fraktion eines Mitspielers geben";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE_INFO;
    public static final String name = "Seherin";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.SEHERIN_KARTE;
    public static boolean spammable = true;

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            String nebenrolle = chosenPlayer.nebenrolle.getName();
            if (nebenrolle.equals(Lamm.name)) {
                return new FrontendControl(FrontendControlType.IMAGE, Bürger.imagePath);
            }
            if (nebenrolle.equals(Wolfspelz.name)) {
                return new FrontendControl(FrontendControlType.IMAGE, Werwölfe.imagePath);
            }
            if (nebenrolle.equals(Vampirumhang.name)) {
                return new FrontendControl(FrontendControlType.IMAGE, Vampire.imagePath);
            }
            if (nebenrolle.equals(Schattenkutte.name)) {
                return new FrontendControl(FrontendControlType.IMAGE, Schattenpriester_Fraktion.imagePath);
            }
            if (nebenrolle.equals(Tarnumhang.name)) {
                return new FrontendControl(FrontendControlType.IMAGE, TARNUMHANG_TITLE, ImagePath.TARNUMHANG);
            }

            return new FrontendControl(FrontendControlType.IMAGE, chosenOption, chosenPlayer.hauptrolle.getFraktion().getImagePath());
        }

        return new FrontendControl();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public StatementType getStatementType() {
        return statementType;
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
    public boolean isSpammable() {
        return spammable;
    }
}
