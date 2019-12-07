package root.Logic.Phases.NightBuilding;

import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Fraktionen.Vampire;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Bonusrollen.Analytiker;
import root.Logic.Persona.Rollen.Bonusrollen.Archivar;
import root.Logic.Persona.Rollen.Bonusrollen.Gefängniswärter;
import root.Logic.Persona.Rollen.Bonusrollen.Konditor;
import root.Logic.Persona.Rollen.Bonusrollen.Konditorlehrling;
import root.Logic.Persona.Rollen.Bonusrollen.Medium;
import root.Logic.Persona.Rollen.Bonusrollen.Nachbar;
import root.Logic.Persona.Rollen.Bonusrollen.Nachtfürst;
import root.Logic.Persona.Rollen.Bonusrollen.Prostituierte;
import root.Logic.Persona.Rollen.Bonusrollen.Schnüffler;
import root.Logic.Persona.Rollen.Bonusrollen.Späher;
import root.Logic.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Logic.Persona.Rollen.Bonusrollen.Totengräber;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Riese;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Schattenmensch;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Seherin;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Alphawolf;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Logic.Phases.PhaseManager;
import root.Logic.Phases.Statement.Constants.IndieStatements;
import root.Logic.Phases.Statement.Constants.ProgramStatements;
import root.Logic.Phases.Statement.SecondStatement;
import root.Logic.Phases.Statement.Statement;
import root.Logic.Spieler;

import java.util.ArrayList;
import java.util.List;

public class NormalNightStatementBuilder {
    public static List<Statement> statements;

    public static List<Statement> normalNightBuildStatements() {
        statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());
        if (Schattenmensch.shallBeTransformed) {
            Schattenmensch.transform(); //TODO program statement
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
        //TODO addstatementRolle sollte nicht immer statements mitbekommen, statements sollte eine public klassenvariable sein.
        if (PhaseManager.nightCount == 1) {
            Spieler henkerSpieler = Game.game.findSpielerPerRolle(Henker.ID);
            if (henkerSpieler != null) {
                henkerSpieler.geschützt = true;
            }
        } else {
            addStatementRolle(Henker.ID);
        }
        addStatementRolle(Riese.ID);
        addStatementFraktion(Werwölfe.ID);
        if (PhaseManager.isVollmondNacht()) {
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
        addStatementRolle(Tarnumhang.ID);
        addStatementRolle(Seherin.ID);
        addStatementRolle(Medium.ID);

        addStatementRolle(GrafVladimir.ID);

        addStatementRolle(Nachbar.ID);

        addStatementRolle(Konditor.ID);
        addStatementRolle(Konditorlehrling.ID);

        statements.add(IndieStatements.getAlleWachenAufStatement());
        statements.add(IndieStatements.getOpferStatement());

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
