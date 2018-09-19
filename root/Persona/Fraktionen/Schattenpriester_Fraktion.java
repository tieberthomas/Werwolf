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
    public static String title = "Opfer wiederbeleben";
    public static final String beschreibung = "Die Schattenpriester erwachen und entscheiden welchen Verstorbenen dieser Nacht sie wiederbeleben und zum Kult hinzufügen möchten";
    public static StatementType statementType = StatementType.FRAKTION_CHOOSE_ONE;

    public static String secondTitle = "Neuer Schattenpriester";
    public static final String NEUER_SCHATTENPRIESTER = "Der Wiederbelebte erwacht und tauscht seine Karten gegen Schattenkarten";
    public static final String secondBeschreibung = NEUER_SCHATTENPRIESTER;
    public static StatementType secondStatementType = StatementType.FRAKTION_SPECAL;

    public static final String NAME = "Schattenpriester";
    public static final Color farbe = Color.lightGray;
    public static final String IMAGE_PATH = ImagePath.SCHATTENPRIESTER_ICON;
    public static final Zeigekarte zeigekarte = new SchattenpriesterZeigekarte();

    public static int deadSchattenPriester = 0;

    public Schattenpriester_Fraktion() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenOpfer = game.findSpieler(chosenOption);
        if (chosenOpfer != null) {
            Opfer.removeVictim(chosenOpfer);

            if (!chosenOpfer.nebenrolle.name.equals(Schattenkutte.NAME)) {
                chosenOpfer.hauptrolle = new Schattenpriester();
                ((Schattenpriester) chosenOpfer.hauptrolle).neuster = true;
            }
            chosenOpfer.nebenrolle = new Schatten();
        }
    }

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            String fraktionOpfer = currentOpfer.opfer.hauptrolle.getFraktion().name;
            if (currentOpfer.opfer.nebenrolle.name.equals(Schattenkutte.NAME) ||
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

    public String getSecondTitle() {
        return secondTitle;
    }

    public String getSecondBeschreibung() {
        return secondBeschreibung;
    }

    public StatementType getSecondStatementType() {
        return secondStatementType;
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
