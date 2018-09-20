package root.Persona.Rollen.Hauptrollen.Vampire;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Nebenrollen.Vampirumhang;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class LadyAleera extends Hauptrolle {
    public static final String STATEMENT_TITLE = "Geschützte Spieler";
    public static final String STATEMENT_BESCHREIBUNG = "Lady Aleera erwacht und sieht alle geschützten Spieler";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public static final String NAME = "Lady Aleera";
    public static Fraktion fraktion = new Vampire();
    public static final String IMAGE_PATH = ImagePath.LADY_ALEERA_KARTE;
    public static boolean killing = true;

    public LadyAleera() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getInfo() {
        return new FrontendControl(FrontendControlType.LIST, findGeschützeSpieler());
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }

    public ArrayList<String> findGeschützeSpieler() {
        ArrayList<String> geschützte = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            String nebenrolleCurrentSpieler = currentSpieler.nebenrolle.name;

            if ((currentSpieler.geschützt || nebenrolleCurrentSpieler.equals(Vampirumhang.NAME)) && currentSpieler.lebend) {
                geschützte.add(currentSpieler.name);
            }
        }

        return geschützte;
    }
}