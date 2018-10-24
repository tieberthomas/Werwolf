package root.Persona.Rollen.Hauptrollen.Bürger;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.Lamm;
import root.Persona.Rollen.Bonusrollen.Schattenkutte;
import root.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.BürgerZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.SchattenpriesterZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.VampiereZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Irrlicht extends Hauptrolle {
    public static final String STATEMENT_IDENTIFIER = "Irrlicht";
    public static final String STATEMENT_TITLE = "Flackern";
    public static final String STATEMENT_BESCHREIBUNG = "Irrlichter flackern gar sehr";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Irrlicht";
    public static final String IMAGE_PATH = ""; //TODO Irrlich filepath ersetzen
    public static final Fraktion FRAKTION = new Bürger();

    public Irrlicht() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.numberOfPossibleInstances = 5; //TODO michael fragen
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        ArrayList<String> allSplieler = game.getLivingSpielerStrings();

        return new FrontendControl(FrontendControlType.IRRLICHT_DROPDOWN, allSplieler);
    }

    public static FrontendControl processFlackerndeIrrlichter(ArrayList<String> irrlichter) {
        System.out.println(irrlichter.size() + " irrlichter flackern gar sehr");

        return new FrontendControl();
    }
}
