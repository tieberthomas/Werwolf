package root.Logic.Persona.Fraktionen;

import root.Controller.FrontendObject.DropdownFrontendObject;
import root.Controller.FrontendObject.FrontendObjectType;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.KillLogic.Opfer;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rollen.Bonusrollen.Schatten;
import root.Logic.Persona.Rollen.Bonusrollen.Schattenkutte;
import root.Logic.Persona.Rollen.Constants.DropdownConstants;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.SchattenpriesterZeigekarte;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Logic.Phases.NormalNight;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SchattenpriesterFraktion extends Fraktion {
    public static final String ID = "ID_Schattenpriester_Fraktion";
    public static final String NAME = "Schattenpriester";
    public static final String IMAGE_PATH = ImagePath.SCHATTENPRIESTER_ICON;
    public static final Color COLOR = Color.lightGray;
    public static final Zeigekarte ZEIGEKARTE = new SchattenpriesterZeigekarte();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Opfer wiederbeleben";
    public static final String STATEMENT_BESCHREIBUNG = "Die Schattenpriester erwachen und entscheiden welchen Verstorbenen dieser Nacht sie wiederbeleben und zum Kult hinzufügen möchten";
    public static final StatementType STATEMENT_TYPE = StatementType.FRAKTION_CHOOSE_ONE;

    public static final String NEUER_SCHATTENPRIESTER = "Schattenpriester_Fraktion_Neuer_Schattenpriester";
    public static final String SECOND_STATEMENT_TITLE = "Neuer Schattenpriester";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der Wiederbelebte erwacht und tauscht seine Karten gegen Schattenkarten";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public static final String SETUP_NIGHT_STATEMENT_ID = "Setup_Night_Schattenpriester_Fraktion";
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Schattenpriester";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Die Schattenpriester erwachen und sehen einander";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public static Spieler spielerToChangeCards = null; //used for Schattenmensch

    public SchattenpriesterFraktion() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.color = COLOR;
        this.zeigekarte = ZEIGEKARTE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementID = NEUER_SCHATTENPRIESTER;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.setupNightStatementID = SETUP_NIGHT_STATEMENT_ID;
        this.setupNightStatementTitle = SETUP_NIGHT_STATEMENT_TITLE;
        this.setupNightStatementBeschreibung = SETUP_NIGHT_STATEMENT_BESCHREIBUNG;
        this.setupNightStatementType = SETUP_NIGHT_STATEMENT_TYPE;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            Opfer.removeOpfer(chosenSpieler);

            if (!chosenSpieler.bonusrolle.equals(Schattenkutte.ID)) {
                Schattenpriester schattenpriester = new Schattenpriester();
                schattenpriester.neuster = true;
                chosenSpieler.hauptrolle = schattenpriester;
            }
            chosenSpieler.bonusrolle = new Schatten();
        }
    }

    @Override
    public FrontendObject getFrontendObject() {
        return new DropdownFrontendObject(FrontendObjectType.DROPDOWN_LIST, new DropdownOptions(getRessurectableOpfer(), DropdownConstants.EMPTY));
    }

    private List<String> getRessurectableOpfer() {
        List<String> dropdownStrings = new ArrayList<>();

        for (Opfer currentOpfer : NormalNight.opfer) {
            Spieler opferSpieler = currentOpfer.spieler;
            if (opferSpieler != null) {
                Fraktion fraktionDesOpfers = opferSpieler.hauptrolle.fraktion;
                if (opferSpieler.bonusrolle.equals(Schattenkutte.ID) ||
                        (opferSpieler.ressurectable && !fraktionDesOpfers.equals(SchattenpriesterFraktion.ID))) {
                    if (!dropdownStrings.contains(opferSpieler.name)) {
                        dropdownStrings.add(opferSpieler.name);
                    }
                }
            }
        }

        return dropdownStrings;
    }
}
