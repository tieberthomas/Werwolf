package root.Logic.Phases.NightBuilding;

import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Fraktionen.Vampire;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Bonusrollen.*;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.*;
import root.Logic.Persona.Rollen.Hauptrollen.Henker.Henker;
import root.Logic.Persona.Rollen.Hauptrollen.Henker.Überläufer;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Alphawolf;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Logic.Phases.PhaseManager;
import root.Logic.Phases.Statement.Constants.IndieStatements;
import root.Logic.Phases.Statement.Constants.ProgramStatements;
import root.Logic.Phases.Statement.DynamicStatement;
import root.Logic.Phases.Statement.SecondStatement;
import root.Logic.Phases.Statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class NormalNightStatementBuilder {
    public static List<Statement> statements;

    public static List<Statement> normalNightBuildStatements() {
        statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());

        if (Schattenmensch.addStatement()) {
            addStatementRolle(Schattenmensch.ID); //TODO generalize the addstatment concept to account for that case
        }

        if (Game.game.mitteBonusrollen.size() > 0) {
            addStatementRolle(Totengräber.ID);
        }

        addStatementRolle(Gefängniswärter.ID);

        if (Game.game.mitteHauptrollen.size() > 0) {
            addStatementRolle(Überläufer.ID);
        }

        addStatementRolle(Irrlicht.ID);
        addSecondStatementRolle(Irrlicht.ID);

        addStatementRolle(Schamanin.ID);

        statements.add(ProgramStatements.getSchützeStatement());

        addStatementRolle(Prostituierte.ID);
        addStatementRolle(Späher.ID);

        //TODO move nacht addstatement speziallogik into rollen/fraktionen
        if (PhaseManager.nightCount != 1) {
            addStatementRolle(Henker.ID);
        }
        addStatementRolle(Riese.ID);
        if (PhaseManager.isFullMoonNight()) {
            addStatementRolle(Wolfsmensch.ID);
        }
        addStatementFraktion(Werwölfe.ID);
        if (PhaseManager.isFullMoonNight()) {
            addStatementRolle(Alphawolf.ID);
        }
        if (Wölfin.stateKilling) {
            addStatementRolle(Wölfin.ID);
        }
        addStatementFraktion(Vampire.ID);

        if (PhaseManager.nightCount != 1) {
            addSecondStatementRolle(Nachtfürst.ID, false);
        }
        addStatementRolle(Nachtfürst.ID);

        addStatementFraktion(SchattenpriesterFraktion.ID);
        addSecondStatementFraktion(SchattenpriesterFraktion.ID);

        addStatementRolle(LadyAleera.ID);
        addStatementRolle(MissVerona.ID);

        addStatementRolle(Analytiker.ID);
        addStatementRolle(Archivar.ID);
        addStatementRolle(Schnüffler.ID);
        addStatementRolle(Seherin.ID);

        addStatementRolle(GrafVladimir.ID);

        addStatementRolle(Nachbar.ID);

        addStatementRolle(Konditor.ID);
        addStatementRolle(Konditorlehrling.ID);

        statements.add(IndieStatements.getAlleWachenAufStatement());
        statements.add(IndieStatements.getOpferStatement());
        addDynamicStatementRolle(Blutwolf.ID);

        statements.add(ProgramStatements.getTortenStatement());

        return statements;
    }

    private static void addStatementRolle(String rolleID) {
        Rolle rolle = Rolle.findRolle(rolleID);
        Statement statement = new Statement(rolle);
        statements.add(statement);
    }

    private static void addSecondStatementRolle(String rolleID) {
        addSecondStatementRolle(rolleID, true);
    }

    private static void addSecondStatementRolle(String rolleID, boolean dependendOnFirstStatement) {
        Rolle rolle = Rolle.findRolle(rolleID);

        Statement statement;

        if (dependendOnFirstStatement) {
            Statement firstStatement = statements.stream()
                    .filter(s -> s.id.equals(rolle.statementID))
                    .findAny().orElse(null);
            statement = new SecondStatement(rolle, firstStatement);
        } else {
            statement = new SecondStatement(rolle);
        }

        statements.add(statement);
    }

    private static void addDynamicStatementRolle(String rolleID) {
        Rolle rolle = Rolle.findRolle(rolleID);
        Statement statement = new DynamicStatement(rolle);
        statements.add(statement);
    }

    private static void addStatementFraktion(String fraktionID) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionID);
        Statement statement = new Statement(fraktion);
        statements.add(statement);
    }

    private static void addSecondStatementFraktion(String fraktionID) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionID);

        Statement firstStatement = statements.stream()
                .filter(statement -> statement.id.equals(fraktion.statementID))
                .findAny().orElse(null);

        Statement statement = new SecondStatement(fraktion, firstStatement);
        statements.add(statement);
    }
}
