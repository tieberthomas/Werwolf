package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Analytiker;
import root.Persona.Rollen.Bonusrollen.Archivar;
import root.Persona.Rollen.Bonusrollen.Dieb;
import root.Persona.Rollen.Bonusrollen.Gefängniswärter;
import root.Persona.Rollen.Bonusrollen.Konditor;
import root.Persona.Rollen.Bonusrollen.Konditorlehrling;
import root.Persona.Rollen.Bonusrollen.Medium;
import root.Persona.Rollen.Bonusrollen.Nachbar;
import root.Persona.Rollen.Bonusrollen.Nachtfürst;
import root.Persona.Rollen.Bonusrollen.Prostituierte;
import root.Persona.Rollen.Bonusrollen.Schnüffler;
import root.Persona.Rollen.Bonusrollen.Spurenleser;
import root.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Persona.Rollen.Bonusrollen.Totengräber;
import root.Persona.Rollen.Bonusrollen.Wahrsager;
import root.Persona.Rollen.Constants.WölfinState;
import root.Persona.Rollen.Hauptrollen.Bürger.HoldeMaid;
import root.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Persona.Rollen.Hauptrollen.Bürger.Orakel;
import root.Persona.Rollen.Hauptrollen.Bürger.Riese;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Persona.Rollen.Hauptrollen.Bürger.Schattenmensch;
import root.Persona.Rollen.Hauptrollen.Bürger.Seherin;
import root.Persona.Rollen.Hauptrollen.Bürger.Späher;
import root.Persona.Rollen.Hauptrollen.Bürger.Wirt;
import root.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Schreckenswolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Persona.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Phases.NightBuilding.Constants.IndieStatements;
import root.Phases.NightBuilding.Constants.ProgrammStatements;
import root.Phases.PhaseManager;
import root.Spieler;
import root.mechanics.Game;

import java.util.ArrayList;
import java.util.List;

public class NormalNightStatementBuilder {
    public static List<Statement> statements;

    public static List<Statement> normalNightBuildStatements() {
        statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());
        if (Schattenmensch.shallBeTransformed) {
            Schattenmensch.transform();
        }

        if (PhaseManager.nightCount != 1 && Wirt.freibierCharges > 0) {
            addStatementRolle(Wirt.ID);
        }

        if (Game.game.mitteBonusrollen.size() > 0) {
            addStatementRolle(Totengräber.ID);
        }
        addStatementRolle(Dieb.ID);
        addSecondStatementRolle(Dieb.ID);

        addStatementRolle(Gefängniswärter.ID);

        if (Game.game.mitteHauptrollen.size() > 0) {
            addStatementRolle(Überläufer.ID);
        }

        addStatementRolle(HoldeMaid.ID);
        addStatementRolle(Irrlicht.ID);
        addSecondStatementRolle(Irrlicht.ID);
        //TODO comment entfernen wenn detektiv wirklich gerext wird
        //Detektiv erwacht, schätzt die Anzahl der Bürger und bekommt ggf. eine Bürgerhauptrolle die im Spiel ist gezeigt
        addStatementRolle(Schamanin.ID);

        statements.add(ProgrammStatements.getSchützeStatement());

        addStatementRolle(Prostituierte.ID);

        //TODO move nacht add speziallogik into rollen/fraktionen
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
        if (Wölfin.state == WölfinState.TÖTEND) {
            addStatementRolle(Wölfin.ID);
        }
        addStatementRolle(Schreckenswolf.ID);
        addStatementFraktion(Vampire.ID);

        if (PhaseManager.nightCount != 1) {
            addSecondStatementRolle(Nachtfürst.ID, false);
        }
        addStatementRolle(Nachtfürst.ID);

        addStatementFraktion(SchattenpriesterFraktion.ID);
        addSecondStatementFraktion(SchattenpriesterFraktion.ID);
        addStatementRolle(Chemiker.ID);
        addSecondStatementRolle(Chemiker.ID);

        addStatementRolle(LadyAleera.ID);
        addStatementRolle(MissVerona.ID);

        addStatementRolle(Analytiker.ID);
        addStatementRolle(Archivar.ID);
        addStatementRolle(Schnüffler.ID);
        addStatementRolle(Tarnumhang.ID);
        addStatementRolle(Seherin.ID);
        addStatementRolle(Späher.ID);
        addStatementRolle(Orakel.ID);
        addStatementRolle(Medium.ID);

        addStatementRolle(GrafVladimir.ID);

        addStatementRolle(Nachbar.ID);
        addStatementRolle(Spurenleser.ID);

        addStatementRolle(Wahrsager.ID);

        if (Game.game.getBonusrolleInGameIDs().contains(Konditorlehrling.ID)) {
            addStatementRolle(Konditorlehrling.ID);
        } else {
            addStatementRolle(Konditor.ID);
        }

        statements.add(IndieStatements.getAlleWachenAufStatement());
        statements.add(IndieStatements.getOpferStatement());

        addSecondStatementRolle(Schreckenswolf.ID); //TODO verstummungsstatement nicht adden wenn schreckenswolf nur bamboozelrolle ist

        if (Wölfin.state == WölfinState.TÖTEND) {
            addSecondStatementRolle(Wölfin.ID);
        }

        statements.add(ProgrammStatements.getTortenProgrammStatement());

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
            statement = new Statement(rolle, firstStatement);
        } else {
            statement = new Statement(rolle, true);
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

        Statement statement = new Statement(fraktion, firstStatement);
        statements.add(statement);
    }
}
