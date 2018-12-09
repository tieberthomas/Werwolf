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

public class SetupNightStatementBuilder {
    public static List<Statement> statements;

    public static List<Statement> setupNightBuildStatements() {
        statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());

        statements.add(IndieStatements.getLiebespaarStatement());
        statements.add(IndieStatements.getLiebespaarFindenStatement());

        addStatementRolle(Seelenlicht.ID);
        addStatementRolle(Wolfspelz.ID);
        addStatementRolle(Vampirumhang.ID);
        addStatementRolle(Lamm.ID);

        addStatementRolle(Wolfsmensch.ID);
        addStatementFraktion(Werwölfe.ID);
        addStatementFraktion(Vampire.ID);
        addStatementFraktion(SchattenpriesterFraktion.ID);
        addStatementRolle(Henker.ID);

        statements.add(IndieStatements.getAlleWachenAufStatement());

        return statements;
    }

    private static void addStatementRolle(String rolleID) {
        Rolle rolle = Rolle.findRolle(rolleID);
        Statement statement = new SetupNightStatement(rolle);
        statements.add(statement);
    }

    private static void addStatementFraktion(String fraktionsID) {
        if (Fraktion.getFraktionsMembers(fraktionsID).size() > 1) {
            Fraktion fraktion = Fraktion.findFraktion(fraktionsID);
            Statement statement = new SetupNightStatement(fraktion);
            statements.add(statement);
        }
    }
}
