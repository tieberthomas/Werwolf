package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.WölfinState;
import root.Persona.Rollen.Nebenrollen.Tarnumhang;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

public class Wölfin extends Hauptrolle {
    public static String title = "Opfer wählen";
    public static final String beschreibung = "Wölfin erwacht und wählt ein Opfer aus, wenn sie das tut, erfährt das Dorf ihre Bonusrolle";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;

    public static String secondTitle = "Wölfin";
    public static final String WÖLFIN_NEBENROLLE = "Das Dorf erfährt die Bonusrolle der Wölfin";
    public static final String secondBeschreibung = WÖLFIN_NEBENROLLE;
    public static StatementType secondStatementType = StatementType.ROLLE_INFO;

    public static final String NAME = "Wölfin";
    public static Fraktion fraktion = new Werwölfe();
    public static final String IMAGE_PATH = ImagePath.WÖLFIN_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;
    public static WölfinState state = WölfinState.WARTEND;

    public Wölfin() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        state = WölfinState.FERTIG;
        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            Spieler täter = game.findSpielerPerRolle(NAME);
            Opfer.addVictim(chosenPlayer, täter);
        }
    }

    @Override
    public FrontendControl getInfo() {
        if (Nacht.wölfinKilled) {
            Spieler wölfinSpieler = Nacht.wölfinSpieler;
            if (wölfinSpieler != null) {
                String imagePath = wölfinSpieler.nebenrolle.imagePath;
                if (wölfinSpieler.nebenrolle.name.equals(Tarnumhang.NAME)) {
                    imagePath = ImagePath.TARNUMHANG;
                }
                return new FrontendControl(FrontendControlType.IMAGE, imagePath);
            }
        }

        return new FrontendControl();
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
    public String getSecondTitle() {
        return secondTitle;
    }

    @Override
    public String getSecondBeschreibung() {
        return secondBeschreibung;
    }

    @Override
    public StatementType getSecondStatementType() {
        return secondStatementType;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }
}