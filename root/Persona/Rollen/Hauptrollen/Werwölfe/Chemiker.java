package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NormalNight;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.KillLogik.Opfer;

import java.util.ArrayList;
import java.util.List;

public class Chemiker extends Hauptrolle {
    public static final String ID = "Chemiker";
    public static final String NAME = "Chemiker";
    public static final String IMAGE_PATH = ImagePath.CHEMIKER_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Opfer wiederbeleben";
    public static final String STATEMENT_BESCHREIBUNG = "Chemiker erwacht und kann ein Wolfsopfer dieser Nacht wiederbeleben und zum Wolfsrudel hinzufügen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NEUER_WERWOLF = "Chemiker_Neuer_Werwolf";
    public static final String SECOND_STATEMENT_TITLE = "Neuer Werwolf";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der Wiederbelebte erwacht und tauscht seine Hauptrollen- gegen eine Werwolfkarte";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.FRAKTION_SPECAL;

    public Chemiker() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementID = NEUER_WERWOLF;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.spammable = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.dropdownStrings = findResurrectableOpfer();
        frontendControl.dropdownStrings.add("");

        return frontendControl;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            Opfer.removeOpfer(chosenSpieler);

            chosenSpieler.hauptrolle = new Werwolf();
        }
    }

    public List<String> findResurrectableOpfer() {
        List<String> resurrectableOpfer = new ArrayList<>();

        for (Opfer currentOpfer : NormalNight.opfer) {
            if(currentOpfer.täterFraktion != null) {
                String opferFraktion = currentOpfer.spieler.hauptrolle.fraktion.name;
                String täterFraktion = currentOpfer.täterFraktion.name;

                if (currentOpfer.spieler.ressurectable) {
                    if (täterFraktion.equals(Werwölfe.NAME)) {
                        if (!opferFraktion.equals(Werwölfe.NAME)) {
                            if (!resurrectableOpfer.contains(currentOpfer.spieler.name)) {
                                resurrectableOpfer.add(currentOpfer.spieler.name);
                            }
                        }
                    }
                }
            }
        }

        return resurrectableOpfer;
    }
}