package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.FrontendObjectType;
import root.Controller.FrontendObject.FrontendObject;
import root.Controller.FrontendObject.ImageFrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Archivar extends Bonusrolle {
    public static final String ID = "ID_Archivar";
    public static final String NAME = "Archivar";
    public static final String IMAGE_PATH = ImagePath.ARCHIVAR_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Archivar erwacht und lässt sich Auskunft über die Bonusrolle eines Mitspielers geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public Archivar() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.selfuseable = true;
        this.spammable = true;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public FrontendObject processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        Spieler archivarSpieler = Game.game.findSpielerPerRolle(this.id);

        if (chosenSpieler != null && archivarSpieler != null) {
            besucht = chosenSpieler;

            BonusrollenType chosenSpielerType = chosenSpieler.getBonusrollenTypeInfo(archivarSpieler);

            return new ImageFrontendObject(FrontendObjectType.IMAGE, chosenSpielerType.title, chosenSpielerType.imagePath);
        }

        return new FrontendObject();
    }
}
