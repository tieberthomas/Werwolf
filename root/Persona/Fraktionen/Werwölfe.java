package root.Persona.Fraktionen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Persona.Fraktion;
import root.Persona.Rolle;
import root.Persona.Rollen.Constants.Zeigekarten.Blutmond;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Game;
import root.mechanics.KillLogik.BlutwolfKill;
import root.mechanics.KillLogik.NormalKill;

import java.awt.*;

public class Werwölfe extends Fraktion {
    public static final String ID = "ID_Werwölfe";
    public static final String NAME = "Werwölfe";
    public static final String IMAGE_PATH = ImagePath.WÖLFE_ICON; //TODO sollte es das noch geben?
    public static final Color COLOR = Color.green;
    public static final Zeigekarte ZEIGEKARTE = new WerwölfeZeigekarte();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Opfer wählen"; //TODO generischen kill titel machen?
    public static final String STATEMENT_BESCHREIBUNG = "Die Werwölfe erwachen und die Wölfe wählen ein Opfer aus";
    public static final StatementType STATEMENT_TYPE = StatementType.FRAKTION_CHOOSE_ONE;

    public static final String SETUP_NIGHT_STATEMENT_ID = "Setup_Night_Werwölfe";
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Werwölfe";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Die Werwölfe erwachen und sehen einander";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public Werwölfe() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.color = COLOR;
        this.zeigekarte = ZEIGEKARTE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.setupNightStatementID = SETUP_NIGHT_STATEMENT_ID;
        this.setupNightStatementTitle = SETUP_NIGHT_STATEMENT_TITLE;
        this.setupNightStatementBeschreibung = SETUP_NIGHT_STATEMENT_BESCHREIBUNG;
        this.setupNightStatementType = SETUP_NIGHT_STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        String imagePath = zeigekarte.imagePath;
        if (blutWolfIsAktiv()) {
            imagePath = new Blutmond().imagePath;
        }

        DropdownOptions dropdownOptions = Game.game.getSpielerDropdownOptions(true);

        return new FrontendControl(FrontendControlType.DROPDOWN_IMAGE, dropdownOptions, imagePath);
    }

    public static boolean blutWolfIsAktiv() {
        return Rolle.rolleLebend(Blutwolf.ID) && Rolle.rolleAktiv(Blutwolf.ID) && Blutwolf.deadly;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            if (blutWolfIsAktiv()) {
                BlutwolfKill.execute(chosenSpieler, this);
            } else {
                NormalKill.execute(chosenSpieler, this);
            }
        }
    }
}
