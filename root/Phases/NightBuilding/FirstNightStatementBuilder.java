package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Lamm;
import root.Persona.Rollen.Bonusrollen.Seelenlicht;
import root.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Persona.Rollen.Hauptrollen.Bürger.Bruder;
import root.Persona.Rollen.Hauptrollen.Bürger.Seherin;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Alphawolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Phases.NightBuilding.Constants.IndieStatements;
import root.mechanics.Game;

import java.util.ArrayList;

public class FirstNightStatementBuilder {
    public static Game game;

    public static ArrayList<Statement> ersteNachtBuildStatements() {
        ArrayList<Statement> statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());

        statements.add(IndieStatements.getLiebespaarStatement());
        statements.add(IndieStatements.getLiebespaarFindenStatement());

        addStatementRolle(statements, Seelenlicht.NAME);
        addStatementRolle(statements, Lamm.NAME);
        addStatementRolle(statements, Vampirumhang.NAME);
        addStatementRolle(statements, Wolfspelz.NAME);

        addStatementFraktion(statements, Vampire.NAME);
        addStatementRolle(statements, Wolfsmensch.NAME);
        addStatementFraktion(statements, Werwölfe.NAME);
        addStatementRolle(statements, Alphawolf.NAME);
        addStatementFraktion(statements, Schattenpriester_Fraktion.NAME);
        addStatementRolle(statements, Bruder.NAME);

        addStatementRolle(statements, Seherin.NAME);

        statements.add(IndieStatements.getAlleWachenAufStatement());

        return statements;
    }

    private static void addStatementRolle(ArrayList<Statement> statements, String rollenName) {
        Rolle rolle = Rolle.findRolle(rollenName);
        //TODO find better solution
        Statement statement = new StatementRolle(rolle.firstNightStatementIdentifier, rolle.firstNightStatementTitle, rolle.firstNightStatementBeschreibung, rolle.firstNightStatementType, rolle.name);
        statements.add(statement);
    }

    private static void addStatementFraktion(ArrayList<Statement> statements, String fraktionsName) {
        if (Fraktion.getFraktionsMembers(fraktionsName).size() > 1) {
            Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
            Statement statement = new StatementFraktion(fraktion.firstNightStatementIdentifier, fraktion.firstNightStatementTitle, fraktion.firstNightStatementBeschreibung, fraktion.firstNightStatementType, fraktion.name);
            statements.add(statement);
        }
    }
}
