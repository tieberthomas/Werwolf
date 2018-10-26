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
    public static final String STATEMENT_ID = "Vampire";
    public static final String STATEMENT_TITLE = "Opfer wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Die Vampire erwachen und wählen ein Opfer aus";
    public static final StatementType STATEMENT_TYPE = StatementType.FRAKTION_CHOOSE_ONE;

    public static final String FIRST_NIGHT_STATEMENT_ID = "First_Night_Vampire";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Vampire";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Die Vampire erwachen und sehen einander";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public static final String NAME = "Vampire";
    public static final String IMAGE_PATH = ImagePath.VAMPIERE_ICON;
    public static final Color COLOR = Color.red;
    public static final Zeigekarte zeigekarte = new VampiereZeigekarte();

    public Vampire() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.color = COLOR;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.firstNightStatementID = FIRST_NIGHT_STATEMENT_ID;
        this.firstNightStatementTitle = FIRST_NIGHT_STATEMENT_TITLE;
        this.firstNightStatementBeschreibung = FIRST_NIGHT_STATEMENT_BESCHREIBUNG;
        this.firstNightStatementType = FIRST_NIGHT_STATEMENT_TYPE;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            Opfer.addOpfer(chosenSpieler, this);
        }
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        FrontendControlType typeOfContent = FrontendControlType.DROPDOWN_IMAGE;
        ArrayList<String> strings = game.getLivingSpielerOrNoneStrings();
        String imagePath = zeigekarte.imagePath;

        return new FrontendControl(typeOfContent, strings, imagePath);
    }

    @Override
    public Zeigekarte getZeigeKarte() {
        return zeigekarte;
    }
}
