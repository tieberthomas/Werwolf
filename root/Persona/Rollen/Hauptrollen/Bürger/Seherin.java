package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.SchattenpriesterZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.VampiereZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Persona.Rollen.Nebenrollen.*;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Seherin extends Hauptrolle {
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Seherin erwacht und lässt sich Auskunft über die Fraktion eines Mitspielers geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String NAME = "Seherin";
    public static Fraktion fraktion = new Bürger();
    public static final String IMAGE_PATH = ImagePath.SEHERIN_KARTE;

    public Seherin() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

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
            Zeigekarte zeigekarte = chosenPlayer.hauptrolle.getFraktion().getZeigeKarte();

            Nebenrolle nebenrolle = chosenPlayer.nebenrolle;
            Hauptrolle hauptrolle = chosenPlayer.hauptrolle;
            if (nebenrolle.equals(Lamm.NAME) || hauptrolle.equals(Wolfsmensch.NAME)) {
                zeigekarte = new BürgerZeigekarte();
            }
            if (nebenrolle.equals(Wolfspelz.NAME)) {
                zeigekarte = new WerwölfeZeigekarte();
            }
            if (nebenrolle.equals(Vampirumhang.NAME)) {
                zeigekarte = new VampiereZeigekarte();
            }
            if (nebenrolle.equals(Schattenkutte.NAME)) {
                zeigekarte = new SchattenpriesterZeigekarte();
            }
            if (nebenrolle.equals(Tarnumhang.NAME)) {
                zeigekarte = new Tarnumhang_NebenrollenType();
            }

            return new FrontendControl(zeigekarte);
        }

        return new FrontendControl();
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }
}
