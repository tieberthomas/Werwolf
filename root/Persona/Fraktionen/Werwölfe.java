package root.Persona.Fraktionen;

import root.Frontend.FrontendControl;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Werwölfe.*;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

public class Werwölfe extends Fraktion {
    public static String title = "Opfer wählen";
    public static final String beschreibung = "Die Werwölfe erwachen und wählen ein Opfer aus";
    public static StatementType statementType = StatementType.FRAKTION_CHOOSE_ONE;
    public static final String name = "Werwölfe";
    public static final Color farbe = Color.green;
    public static final String imagePath = ImagePath.WÖLFE_ICON; //sollte es das noch geben?
    public static final Zeigekarte zeigekarte = new WerwölfeZeigekarte();

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            Spieler täter = Fraktion.getFraktionsMembers(name).get(0);
            Opfer.addVictim(chosenPlayer, täter, true);
        }
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerFrontendControl();
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
    public Color getFarbe() {
        return farbe;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public Zeigekarte getZeigeKarte() {
        return zeigekarte;
    }

    public static boolean isTötend(String hauptrolle) {
        ArrayList<String> tötend = new ArrayList<>();

        tötend.add(Alphawolf.name);
        tötend.add(Blutwolf.name);
        tötend.add(Geisterwolf.name);
        tötend.add(Schreckenswolf.name);
        tötend.add(Werwolf.name);
        tötend.add(Wölfin.name);

        return tötend.contains(hauptrolle);
    }
}
