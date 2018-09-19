package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Geisterwolf;
import root.Persona.Rollen.Nebenrollen.Tarnumhang;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Späher extends Hauptrolle {
    public static String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Späher erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String NAME = "Späher";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.SPÄHER_KARTE;
    public static boolean spammable = true;

    public Späher() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            if (chosenPlayer.nebenrolle.name.equals(Tarnumhang.NAME)) {
                return new FrontendControl(new Tarnumhang_NebenrollenType());
            }

            if (chosenPlayer.hauptrolle.isKilling() && !chosenPlayer.hauptrolle.equals(Geisterwolf.NAME)) {
                abilityCharges--;

                return new FrontendControl(new Tötend());
            } else {
                return new FrontendControl(new Nicht_Tötend());
            }
        }

        return new FrontendControl();
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }
}
