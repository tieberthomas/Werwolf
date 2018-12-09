package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Lamm;
import root.Persona.Rollen.Bonusrollen.Seelenlicht;
import root.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Phases.NightBuilding.Constants.IndieStatements;

import java.util.ArrayList;
import java.util.List;

public class FirstNightStatementBuilder {
    public static List<Statement> firstNightBuildStatements() {
        List<Statement> statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());

        statements.add(IndieStatements.getLiebespaarStatement());
        statements.add(IndieStatements.getLiebespaarFindenStatement());

        addStatementRolle(statements, Seelenlicht.ID);
        addStatementRolle(statements, Wolfspelz.ID);
        addStatementRolle(statements, Vampirumhang.ID);
        addStatementRolle(statements, Lamm.ID);

        addStatementRolle(statements, Wolfsmensch.ID);
        addStatementFraktion(statements, Werwölfe.ID);
        addStatementFraktion(statements, Vampire.ID);
        addStatementFraktion(statements, SchattenpriesterFraktion.ID);
        addStatementRolle(statements, Henker.ID);

        statements.add(IndieStatements.getAlleWachenAufStatement());

        return statements;
    }

    private static void addStatementRolle(List<Statement> statements, String rolleID) {
        Rolle rolle = Rolle.findRolle(rolleID);
        Statement statement = new FirstNightStatement(rolle);
        statements.add(statement);
    }

    private static void addStatementFraktion(List<Statement> statements, String fraktionsID) {
        if (Fraktion.getFraktionsMembers(fraktionsID).size() > 1) {
            Fraktion fraktion = Fraktion.findFraktion(fraktionsID);
            Statement statement = new FirstNightStatement(fraktion);
            statements.add(statement);
        }
    }
}
