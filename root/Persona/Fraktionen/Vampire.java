package root.Persona.Fraktionen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.VampiereZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

public class Vampire extends Fraktion {
    public static String title = "Opfer wählen";
    public static final String beschreibung = "Die Vampire erwachen und wählen ein Opfer aus";
    public static StatementType statementType = StatementType.FRAKTION_CHOOSE_ONE;
    public static final String NAME = "Vampire";
    public static final Color farbe = Color.red;
    public static final String IMAGE_PATH = ImagePath.VAMPIERE_ICON;
    public static final Zeigekarte zeigekarte = new VampiereZeigekarte();

    public Vampire() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            Opfer.addVictim(chosenPlayer, this);
        }
    }

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControlType typeOfContent = FrontendControlType.DROPDOWN_IMAGE;
        ArrayList<String> strings = game.getLivingPlayerOrNoneStrings();
        String imagePath = zeigekarte.imagePath;

        return new FrontendControl(typeOfContent, strings, imagePath);
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
    public Zeigekarte getZeigeKarte() {
        return zeigekarte;
    }
}
