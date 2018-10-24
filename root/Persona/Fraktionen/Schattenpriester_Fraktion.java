package root.Persona.Fraktionen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Rollen.Bonusrollen.Schatten;
import root.Persona.Rollen.Bonusrollen.Schattenkutte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.SchattenpriesterZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

public class Schattenpriester_Fraktion extends Fraktion {
    public static final String STATEMENT_IDENTIFIER = "Schattenpriester_Fraktion";
    public static final String STATEMENT_TITLE = "Opfer wiederbeleben";
    public static final String STATEMENT_BESCHREIBUNG = "Die Schattenpriester erwachen und entscheiden welchen Verstorbenen dieser Nacht sie wiederbeleben und zum Kult hinzufügen möchten";
    public static final StatementType STATEMENT_TYPE = StatementType.FRAKTION_CHOOSE_ONE;

    public static final String NEUER_SCHATTENPRIESTER = "Schattenpriester_Fraktion_Neuer_Schattenpriester";
    public static final String SECOND_STATEMENT_TITLE = "Neuer Schattenpriester";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der Wiederbelebte erwacht und tauscht seine Karten gegen Schattenkarten";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public static final String FIRST_NIGHT_STATEMENT_IDENTIFIER = "First_Night_Schattenpriester_Fraktion";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Schattenpriester";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Die Schattenpriester erwachen und sehen einander";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public static final String NAME = "Schattenpriester";
    public static final String IMAGE_PATH = ImagePath.SCHATTENPRIESTER_ICON;
    public static final Color COLOR = Color.lightGray;
    public static final Zeigekarte zeigekarte = new SchattenpriesterZeigekarte();

    public static int deadSchattenPriester = 0;

    public Schattenpriester_Fraktion() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.color = COLOR;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementIdentifier = NEUER_SCHATTENPRIESTER;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.firstNightStatementIdentifier = FIRST_NIGHT_STATEMENT_IDENTIFIER;
        this.firstNightStatementTitle = FIRST_NIGHT_STATEMENT_TITLE;
        this.firstNightStatementBeschreibung = FIRST_NIGHT_STATEMENT_BESCHREIBUNG;
        this.firstNightStatementType = FIRST_NIGHT_STATEMENT_TYPE;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenOpfer = game.findSpieler(chosenOption);
        if (chosenOpfer != null) {
            Opfer.removeOpfer(chosenOpfer);

            if (!chosenOpfer.bonusrolle.name.equals(Schattenkutte.NAME)) {
                chosenOpfer.hauptrolle = new Schattenpriester();
                ((Schattenpriester) chosenOpfer.hauptrolle).neuster = true;
            }
            chosenOpfer.bonusrolle = new Schatten();
        }
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, getRessurectableOpfer());
    }

    private ArrayList<String> getRessurectableOpfer() {
        ArrayList<String> dropdownStrings = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadOpfer) {
            Spieler opferSpieler = currentOpfer.opfer;
            if (opferSpieler != null) {
                String fraktionDesOpfers = opferSpieler.hauptrolle.fraktion.name;
                if (opferSpieler.bonusrolle.name.equals(Schattenkutte.NAME) ||
                        (opferSpieler.ressurectable && !fraktionDesOpfers.equals(Schattenpriester_Fraktion.NAME))) {
                    if (!dropdownStrings.contains(opferSpieler.name)) {
                        dropdownStrings.add(opferSpieler.name);
                    }
                }
            }
        }

        dropdownStrings.add("");

        return dropdownStrings;
    }

    @Override
    public Zeigekarte getZeigeKarte() {
        return zeigekarte;
    }
}
