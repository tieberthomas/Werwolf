package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Controller.FrontendObject.FrontendObject;
import root.Controller.FrontendObject.ImageFrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Späher extends Hauptrolle {
    public static final String ID = "ID_Späher";
    public static final String NAME = "Späher";
    public static final String IMAGE_PATH = ImagePath.SPÄHER_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Späher erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public Späher() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

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
        Spieler späherSpieler = Game.game.findSpielerPerRolle(this.id);

        if (chosenSpieler != null && späherSpieler != null) {
            besucht = chosenSpieler;

            Zeigekarte info = chosenSpieler.isTötendInfo(späherSpieler);

            if (info instanceof Tötend) {
                abilityCharges--;
            }

            return new ImageFrontendObject(info);
        }

        return new FrontendObject();
    }
}
