package root.Persona.Fraktionen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
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
import java.util.List;

public class Werwölfe extends Fraktion {
    public static final String ID = "ID_Werwölfe";
    public static final String NAME = "Werwölfe";
    public static final String IMAGE_PATH = ImagePath.WÖLFE_ICON; //TODO sollte es das noch geben?
    public static final Color COLOR = Color.green;
    public static final Zeigekarte ZEIGEKARTE = new WerwölfeZeigekarte();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Opfer wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Die Werwölfe erwachen und die Wölfe wählen ein Opfer aus";
    public static final StatementType STATEMENT_TYPE = StatementType.FRAKTION_CHOOSE_ONE;

    public static final String FIRST_NIGHT_STATEMENT_ID = "First_Night_Werwölfe";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Werwölfe";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Die Werwölfe erwachen und sehen einander";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

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

        this.firstNightStatementID = FIRST_NIGHT_STATEMENT_ID;
        this.firstNightStatementTitle = FIRST_NIGHT_STATEMENT_TITLE;
        this.firstNightStatementBeschreibung = FIRST_NIGHT_STATEMENT_BESCHREIBUNG;
        this.firstNightStatementType = FIRST_NIGHT_STATEMENT_TYPE;
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

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        FrontendControlType typeOfContent = FrontendControlType.DROPDOWN_IMAGE;
        List<String> strings = Game.game.getLivingSpielerOrNoneStrings();
        String imagePath = zeigekarte.imagePath;
        if (blutWolfIsAktiv()) {
            imagePath = new Blutmond().imagePath;
        }

        return new FrontendControl(typeOfContent, strings, imagePath);
    }

    public static boolean blutWolfIsAktiv() {
        return Rolle.rolleLebend(Blutwolf.ID) && Rolle.rolleAktiv(Blutwolf.ID) && Blutwolf.deadly;
    }
}
