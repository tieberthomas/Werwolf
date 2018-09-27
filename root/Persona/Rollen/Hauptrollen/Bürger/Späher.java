package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Geisterwolf;
import root.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Späher extends Hauptrolle {
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Späher erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String NAME = "Späher";
    public static final String IMAGE_PATH = ImagePath.SPÄHER_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public Späher() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
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

            if (chosenPlayer.bonusrolle.name.equals(Tarnumhang.NAME)) {
                return new FrontendControl(new Tarnumhang_BonusrollenType());
            }

            if (chosenPlayer.hauptrolle.killing && !chosenPlayer.hauptrolle.equals(Geisterwolf.NAME)) {
                abilityCharges--;

                return new FrontendControl(new Tötend());
            } else {
                return new FrontendControl(new Nicht_Tötend());
            }
        }

        return new FrontendControl();
    }
}
