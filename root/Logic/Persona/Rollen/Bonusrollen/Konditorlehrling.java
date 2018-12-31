package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Phases.NormalNight;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.Logic.Torte;
import root.ResourceManagement.ImagePath;

public class Konditorlehrling extends Bonusrolle {
    public static final String ID = "ID_Konditorlehrling";
    public static final String NAME = "Konditorlehrling";
    public static final String IMAGE_PATH = ImagePath.KONDITORLEHRLING_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Tortenstück";
    public static final String STATEMENT_BESCHREIBUNG = "Konditorlehrling erwacht, wählt einen Spielerund gibt ihm ein gutes oder schlechtes Tortenstück";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public Konditorlehrling() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.selfuseable = false;
        this.spammable = false;
    }

    public DropdownOptions getDropdownOptionsSpieler() {
        return Game.game.getSpielerDropdownOptions(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            besucht = chosenSpieler;
            NormalNight.torteSpieler = chosenSpieler;
            Torte.stückGut = FrontendObject.erzählerFrame.chosenOption2.equals(Konditor.GUT);
        }
    }

}
