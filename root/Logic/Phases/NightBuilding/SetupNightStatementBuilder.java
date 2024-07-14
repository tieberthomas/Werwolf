package root.Logic.Phases.NightBuilding;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Fraktionen.Vampire;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Bonusrollen.Engel;
import root.Logic.Persona.Rollen.Bonusrollen.Schafspelz;
import root.Logic.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Logic.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Logic.Persona.Rollen.Hauptrollen.Henker.Henker;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Logic.Phases.Statement.Constants.IndieStatements;
import root.Logic.Phases.Statement.SecondStatement;
import root.Logic.Phases.Statement.SetupNightStatement;
import root.Logic.Phases.Statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class SetupNightStatementBuilder {
    public static List<Statement> statements;

    public static List<Statement> setupNightBuildStatements() {
        statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());

        statements.add(IndieStatements.getLiebespaarStatement());
        statements.add(IndieStatements.getLiebespaarFindenStatement());

        addStatementRolle(Engel.ID);
        addStatementRolle(Wolfspelz.ID);
        addStatementRolle(Vampirumhang.ID);
        addStatementRolle(Schafspelz.ID);

        addStatementRolle(Wolfsmensch.ID);
        addStatementFraktion(Werwölfe.ID);
        addStatementFraktion(Vampire.ID);
        addStatementFraktion(SchattenpriesterFraktion.ID);
        addStatementRolle(Henker.ID);

        addStatementRolle(Blutwolf.ID);
        addSecondStatementRolle(Blutwolf.ID); //TODO blaue rahmen

        statements.add(IndieStatements.getAlleWachenAufStatement());

        return statements;
    }

    private static void addStatementRolle(String rolleID) {
        Rolle rolle = Rolle.findRolle(rolleID);
        Statement statement = new SetupNightStatement(rolle);
        statements.add(statement);
    }

    private static void addSecondStatementRolle(String rolleID) { //TODO Resolve conflict (Rollen can't have Second Statement in both nights)
        Rolle rolle = Rolle.findRolle(rolleID);

        Statement firstStatement = statements.stream()
                .filter(s -> s.id.equals(rolle.setupNightStatementID))
                .findAny().orElse(null);

        Statement statement = new SecondStatement(rolle, firstStatement);
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
