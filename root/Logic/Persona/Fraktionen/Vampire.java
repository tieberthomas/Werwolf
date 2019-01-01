package root.Logic.Persona.Fraktionen;

import root.Controller.FrontendObject.DropdownFrontendObject;
import root.Controller.FrontendObject.FrontendObjectType;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.KillLogic.NormalKill;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.VampireZeigekarte;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.awt.*;

import static root.Logic.Persona.Constants.TitleConstants.CHOSE_OPFER_TITLE;

public class Vampire extends Fraktion {
    public static final String ID = "ID_Vampire";
    public static final String NAME = "Vampire";
    public static final String IMAGE_PATH = ImagePath.VAMPIRE_ICON;
    public static final Color COLOR = Color.red;
    public static final Zeigekarte ZEIGEKARTE = new VampireZeigekarte();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = CHOSE_OPFER_TITLE;
    public static final String STATEMENT_BESCHREIBUNG = "Die Vampire erwachen und wählen ein Opfer aus";
    public static final StatementType STATEMENT_TYPE = StatementType.FRAKTION_CHOOSE_ONE;

    public static final String SETUP_NIGHT_STATEMENT_ID = "Setup_Night_Vampire";
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Vampire";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Die Vampire erwachen und sehen einander";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public Vampire() {
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

        this.toetend = true;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            NormalKill.execute(chosenSpieler, this);
        }
    }

    @Override
    public FrontendObject getFrontendObject() {
        DropdownOptions dropdownOptions = Game.game.getSpielerDropdownOptions(true);

        return new DropdownFrontendObject(FrontendObjectType.DROPDOWN_IMAGE, dropdownOptions, zeigekarte.imagePath);
    }

    @Override
    public void beginNight() {
        for (Spieler currentSpieler : Game.game.spieler) {
            if (currentSpieler.hauptrolle.fraktion.equals(Vampire.ID)) {
                currentSpieler.ressurectable = false;
            }
        }
    }
}
