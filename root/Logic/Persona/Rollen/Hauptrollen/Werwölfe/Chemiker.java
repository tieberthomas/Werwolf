package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.DropdownConstants;
import root.Logic.Phases.NightBuilding.Constants.StatementType;
import root.Logic.Phases.NormalNight;
import root.ResourceManagement.ImagePath;
import root.Logic.Spieler;
import root.Logic.Game;
import root.Logic.KillLogic.Opfer;

import java.util.ArrayList;
import java.util.List;

public class Chemiker extends Hauptrolle {
    public static final String ID = "ID_Chemiker";
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
        DropdownOptions dropdownOptions = new DropdownOptions(findResurrectableOpfer(), DropdownConstants.EMPTY);

        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, dropdownOptions);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            Opfer.removeOpfer(chosenSpieler);

            chosenSpieler.hauptrolle = new Werwolf();
        }
    }

    public List<String> findResurrectableOpfer() {
        List<String> resurrectableOpfer = new ArrayList<>();

        for (Opfer currentOpfer : NormalNight.opfer) {
            if (currentOpfer.täterFraktion != null) {
                Fraktion opferFraktion = currentOpfer.spieler.hauptrolle.fraktion;
                Fraktion täterFraktion = currentOpfer.täterFraktion;

                if (currentOpfer.spieler.ressurectable) {
                    if (täterFraktion.equals(Werwölfe.ID)) {
                        if (!opferFraktion.equals(Werwölfe.ID)) {
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