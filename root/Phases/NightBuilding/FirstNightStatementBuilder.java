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
import root.mechanics.Game;

import java.util.ArrayList;
import java.util.List;

public class FirstNightStatementBuilder {
    public static Game game;

    public static List<Statement> firstNightBuildStatements() {
        List<Statement> statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());

        statements.add(IndieStatements.getLiebespaarStatement());
        statements.add(IndieStatements.getLiebespaarFindenStatement());

        addStatementRolle(statements, Seelenlicht.NAME);
        addStatementRolle(statements, Wolfspelz.NAME);
        addStatementRolle(statements, Vampirumhang.NAME);
        addStatementRolle(statements, Lamm.NAME);

        addStatementRolle(statements, Wolfsmensch.NAME);
        addStatementFraktion(statements, Werwölfe.NAME);
        addStatementFraktion(statements, Vampire.NAME);
        addStatementFraktion(statements, SchattenpriesterFraktion.NAME);
        addStatementRolle(statements, Henker.NAME);

        statements.add(IndieStatements.getAlleWachenAufStatement());

        return statements;
    }

    private static void addStatementRolle(List<Statement> statements, String rollenName) {
        Rolle rolle = Rolle.findRolle(rollenName);
        Statement statement = Statement.newFirstNightStatement(rolle);
        statements.add(statement);
    }

    private static void addStatementFraktion(List<Statement> statements, String fraktionsName) {
        if (Fraktion.getFraktionsMembers(fraktionsName).size() > 1) {
            Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
            Statement statement = Statement.newFirstNightStatement(fraktion);
            statements.add(statement);
        }
    }
}
