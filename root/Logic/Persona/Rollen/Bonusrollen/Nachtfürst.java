package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.DropdownFrontendObject;
import root.Controller.FrontendObject.DropdownListFrontendObject;
import root.Controller.FrontendObject.FrontendObject;
import root.Controller.FrontendObject.ImageFrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.KillLogic.NormalKill;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Vampire;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.DropdownConstants;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Geschützt;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Logic.Phases.NormalNight;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

import static root.Logic.Persona.Constants.TitleConstants.CHOSE_OPFER_TITLE;

public class Nachtfürst extends Bonusrolle {
    public static final String ID = "ID_Nachtfürst";
    public static final String NAME = "Nachtfürst";
    public static final String IMAGE_PATH = ImagePath.NACHTFÜRST_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String SCHÄTZEN_ID = ID;
    public static final String STATEMENT_TITLE = "Anzahl der toten Opfer";
    public static final String STATEMENT_BESCHREIBUNG = "Nachtfürst erwacht und schätzt, wieviele Opfer es in der Nacht geben wird";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE;

    public static final String TÖTEN_ID = "Nachtfürst_Kill";
    public static final String SECOND_STATEMENT_TITLE = CHOSE_OPFER_TITLE;
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Nachtfürst erwacht und tötet einen Spieler wenn er letzte Nacht richtig lag"; //TODO er ist ja schon wach, wording ändern
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.PERSONA_SPECAL;

    public static final String KEIN_OPFER = "Kein Opfer";
    public static final String NICHT_GESCHÜTZT = "Nicht geschützt";

    private Integer tipp = null;
    public boolean guessedRight = false;

    public Nachtfürst() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = SCHÄTZEN_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementID = TÖTEN_ID;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.spammable = true;
    }

    @Override
    public FrontendObject getFrontendObject() {
        List<String> numbers = new ArrayList<>();
        numbers.add("1");
        numbers.add("2");
        numbers.add("3");
        numbers.add("4");
        numbers.add("5"); //TODO 5 genügt?
        return new DropdownListFrontendObject(new DropdownOptions(numbers, KEIN_OPFER));
    }

    @Override
    public void processChosenOption(String chosenOption) {
        if (chosenOption != null) {
            if (chosenOption.equals(KEIN_OPFER)) {
                tipp = 0;
            } else {
                tipp = Integer.parseInt(chosenOption);
            }
        } else {
            tipp = null;
        }
    }

    public FrontendObject getSecondFrontendObject() {
        if (isTötendeFraktion()) {
            if (guessedRight) {
                return new DropdownFrontendObject(Game.game.getSpielerDropdownOptions(true));
            } else {
                return new ImageFrontendObject(new Nicht_Tötend());
            }
        } else {
            if (guessedRight) {
                return new ImageFrontendObject(new Geschützt());
            } else {
                return new DropdownFrontendObject(NICHT_GESCHÜTZT, new DropdownOptions(new ArrayList<>(), DropdownConstants.EMPTY));
            }
        }
    }

    public void processSecondChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        Spieler nachtfürstSpieler = Game.game.findSpielerPerRolle(id);
        if (chosenSpieler != null && nachtfürstSpieler != null) {
            besucht = chosenSpieler;
            NormalKill.execute(chosenSpieler, nachtfürstSpieler);
        }
    }

    public boolean isTötendeFraktion() { //TODO hier die Henker frattion überprüfung ändern
        Spieler nachtfürstSpieler = Game.game.findSpielerPerRolle(id);

        if (nachtfürstSpieler != null) {
            Fraktion fraktion = nachtfürstSpieler.getFraktion();
            Hauptrolle hauptrolle = nachtfürstSpieler.hauptrolle;
            return fraktion.equals(Werwölfe.ID) || fraktion.equals(Vampire.ID) || hauptrolle.equals(Henker.ID);
        } else {
            return false;
        }
    }

    private void checkGuess(int anzahlOpferDerNacht) {
        guessedRight = tipp != null && (anzahlOpferDerNacht == tipp);
    }

    @Override
    public void cleanUpAfterNight() {
        int anzahlOpferDerNacht = NormalNight.getAnzahlOpferDerNacht();
        checkGuess(anzahlOpferDerNacht);
        tipp = null;
    }
}
