package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.SchattenpriesterZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.VampiereZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Nebenrollen.*;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Seherin extends Hauptrolle {
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
            Zeigekarte zeigekarte = chosenPlayer.hauptrolle.getFraktion().getZeigeKarte();

            String nebenrolle = chosenPlayer.nebenrolle.getName();
            if (nebenrolle.equals(Lamm.name)) {
                zeigekarte = new BürgerZeigekarte();
            }
            if (nebenrolle.equals(Wolfspelz.name)) {
                zeigekarte = new WerwölfeZeigekarte();
            }
            if (nebenrolle.equals(Vampirumhang.name)) {
                zeigekarte = new VampiereZeigekarte();
            }
            if (nebenrolle.equals(Schattenkutte.name)) {
                zeigekarte = new SchattenpriesterZeigekarte();
            }
            if (nebenrolle.equals(Tarnumhang.name)) {
                zeigekarte = new Tarnumhang_NebenrollenType();
            }

            return new FrontendControl(zeigekarte);
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
