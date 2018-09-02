package root.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Rollen.Constants.WölfinState;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.Tarnumhang;
import root.Spieler;
import root.mechanics.Opfer;

public class Wölfin extends Hauptrolle {
    public static String title = "Opfer wählen";
    public static final String beschreibung = "Wölfin erwacht und wählt ein Opfer aus, wenn sie das tut, erfährt das dorf ihre Bonusrolle";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;
    public static final String name = "Wölfin";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ImagePath.WÖLFIN_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;
    public static WölfinState state = WölfinState.WARTEND;

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

            Spieler täter = game.findSpielerPerRolle(name);
            Opfer.addVictim(chosenPlayer, täter, false);
        }
    }

    @Override
    public FrontendControl getInfo() {
        if (Nacht.wölfinKilled) {
            Spieler wölfinSpieler = Nacht.wölfinSpieler;
            if (wölfinSpieler != null) {
                String imagePath = wölfinSpieler.nebenrolle.getImagePath();
                if (wölfinSpieler.nebenrolle.getName().equals(Tarnumhang.name)) {
                    imagePath = ImagePath.TARNUMHANG;
                }
                return new FrontendControl(FrontendControlType.IMAGE, imagePath);
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

    @Override
    public boolean isKilling() {
        return killing;
    }
}