package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Persona.Rollen.Nebenrollen.Tarnumhang;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Späher extends Hauptrolle {
    public static String title = "Spieler wählen";
    public static final String beschreibung = "Späher erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String name = "Späher";
    public static Fraktion fraktion = new Bürger();
    public static final String imagePath = ImagePath.SPÄHER_KARTE;
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

            if (chosenPlayer.nebenrolle.getName().equals(Tarnumhang.name)) {
                return new FrontendControl(new Tarnumhang_NebenrollenType());
            }

            if (chosenPlayer.hauptrolle.isKilling()) {
                abilityCharges--;

                return new FrontendControl(new Tötend());
            } else {
                return new FrontendControl(new Nicht_Tötend());
            }
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
