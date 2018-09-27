package root.Persona.Fraktionen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.SchattenpriesterZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Persona.Rollen.Nebenrollen.Schatten;
import root.Persona.Rollen.Nebenrollen.Schattenkutte;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

import java.awt.*;
import java.util.ArrayList;

public class Schattenpriester_Fraktion extends Fraktion {
    public static final String STATEMENT_TITLE = "Opfer wiederbeleben";
    public static final String STATEMENT_BESCHREIBUNG = "Die Schattenpriester erwachen und entscheiden welchen Verstorbenen dieser Nacht sie wiederbeleben und zum Kult hinzufügen möchten";
    public static final StatementType STATEMENT_TYPE = StatementType.FRAKTION_CHOOSE_ONE;

    public static final String SECOND_STATEMENT_TITLE = "Neuer Schattenpriester";
    public static final String NEUER_SCHATTENPRIESTER = "Der Wiederbelebte erwacht und tauscht seine Karten gegen Schattenkarten";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = NEUER_SCHATTENPRIESTER;
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public static final String NAME = "Schattenpriester";
    public static final String IMAGE_PATH = ImagePath.SCHATTENPRIESTER_ICON;
    public static final Color COLOR = Color.lightGray;
    public static final Zeigekarte zeigekarte = new SchattenpriesterZeigekarte();

    public static int deadSchattenPriester = 0;

    public Schattenpriester_Fraktion() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.color = COLOR;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenOpfer = game.findSpieler(chosenOption);
        if (chosenOpfer != null) {
            Opfer.removeVictim(chosenOpfer);

            if (!chosenOpfer.bonusrolle.name.equals(Schattenkutte.NAME)) {
                chosenOpfer.hauptrolle = new Schattenpriester();
                ((Schattenpriester) chosenOpfer.hauptrolle).neuster = true;
            }
            chosenOpfer.bonusrolle = new Schatten();
        }
    }

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            String fraktionOpfer = currentOpfer.opfer.hauptrolle.fraktion.name;
            if (currentOpfer.opfer.bonusrolle.name.equals(Schattenkutte.NAME) ||
                    (currentOpfer.opfer.ressurectable && !fraktionOpfer.equals(Schattenpriester_Fraktion.NAME))) {
                if (!frontendControl.dropdownStrings.contains(currentOpfer.opfer.name)) {
                    frontendControl.dropdownStrings.add(currentOpfer.opfer.name);
                }
            }
        }

        frontendControl.dropdownStrings.add("");

        return frontendControl;
    }

    @Override
    public Zeigekarte getZeigeKarte() {
        return zeigekarte;
    }
}
