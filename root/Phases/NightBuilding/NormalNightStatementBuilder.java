package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.*;
import root.Persona.Rollen.Constants.WölfinState;
import root.Persona.Rollen.Hauptrollen.Bürger.*;
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
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyFraktion;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyRolle;
import root.mechanics.Game;

import java.util.ArrayList;

public class NormalNightStatementBuilder {
    public static Game game;

    public static ArrayList<Statement> normalNightBuildStatements() {
        ArrayList<Statement> statements = new ArrayList<>();

        statements.add(IndieStatements.getAlleSchlafenEinStatement());

        if (Wirt.freibierCharges > 0) {
            addStatementRolle(statements, Wirt.NAME);
        }

        if (Totengräber.getNehmbareBonusrollen().size() > 0) {
            addStatementRolle(statements, Totengräber.NAME);
        }
        addStatementRolle(statements, Dieb.NAME);
        addSecondStatementRolle(statements, Dieb.NAME);

        addStatementRolle(statements, Gefängniswärter.NAME);

        if (game.mitteHauptrollen.size() > 0) {
            addStatementRolle(statements, Überläufer.NAME);
        }

        addStatementRolle(statements, HoldeMaid.NAME);
        addStatementRolle(statements, Irrlicht.NAME);
        addSecondStatementRolle(statements, Irrlicht.NAME);
        //Detektiv erwacht und schätzt die Anzahl der Bürger
        addStatementRolle(statements, Schamanin.NAME);

        statements.add(ProgrammStatements.getSchützeStatement());

        addStatementRolle(statements, LadyAleera.NAME);
        addStatementRolle(statements, Prostituierte.NAME);

        addStatementRolle(statements, Henker.NAME);
        addSecondStatementRolle(statements, Henker.NAME);
        addStatementRolle(statements, Riese.NAME);
        addStatementFraktion(statements, Vampire.NAME);
        addStatementFraktion(statements, Werwölfe.NAME);
        if (Wölfin.state == WölfinState.TÖTEND) {
            addStatementRolle(statements, Wölfin.NAME);
        }

        //Nachtfürst erwacht, schätzt die Anzahl der Opfer dieser Nacht und führt ggf. seine Tötung aus
        addStatementRolle(statements, Schreckenswolf.NAME);

        addStatementFraktion(statements, Schattenpriester_Fraktion.NAME);
        addSecondStatementFraktion(statements, Schattenpriester_Fraktion.NAME);
        addStatementRolle(statements, Chemiker.NAME);
        addSecondStatementRolle(statements, Chemiker.NAME);

        addStatementRolle(statements, MissVerona.NAME);
        addStatementRolle(statements, Analytiker.NAME);
        addStatementRolle(statements, Archivar.NAME);
        addStatementRolle(statements, Schnüffler.NAME);
        addStatementRolle(statements, Tarnumhang.NAME);
        addStatementRolle(statements, Seherin.NAME);
        addStatementRolle(statements, Späher.NAME);
        addStatementRolle(statements, Orakel.NAME);
        addStatementRolle(statements, Medium.NAME);

        addStatementRolle(statements, GrafVladimir.NAME);

        addStatementRolle(statements, Nachbar.NAME);
        addStatementRolle(statements, Spurenleser.NAME);

        addStatementRolle(statements, Wahrsager.NAME);

        if (game.getBonusrolleInGameNames().contains(Konditorlehrling.NAME)) {
            addStatementRolle(statements, Konditorlehrling.NAME);
        } else {
            addStatementRolle(statements, Konditor.NAME);
        }

        statements.add(IndieStatements.getAlleWachenAufStatement());

        statements.add(ProgrammStatements.getOferStatement());
        statements.add(IndieStatements.getOpferStatement());

        addSecondStatementRolle(statements, Schreckenswolf.NAME);

        if (Wölfin.state == WölfinState.TÖTEND) {
            addSecondStatementRolle(statements, Wölfin.NAME);
        }

        statements.add(ProgrammStatements.getTortenProgrammStatement());

        return statements;
    }

    private static void addStatementRolle(ArrayList<Statement> statements, String rollenName) {
        Rolle rolle = Rolle.findRolle(rollenName);
        Statement statement = new Statement(rolle);
        statements.add(statement);
    }

    private static void addSecondStatementRolle(ArrayList<Statement> statements, String rollenName) {
        Rolle rolle = Rolle.findRolle(rollenName);
        //TODO find better solution
        Statement statement = new Statement(rolle.secondStatementIdentifier, rolle.secondStatementTitle, rolle.secondStatementBeschreibung, rolle.secondStatementType, new StatementDependencyRolle(rolle));
        statements.add(statement);
    }

    private static void addStatementFraktion(ArrayList<Statement> statements, String fraktionsName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
        Statement statement = new Statement(fraktion);
        statements.add(statement);
    }

    private static void addSecondStatementFraktion(ArrayList<Statement> statements, String fraktionsName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
        Statement statement = new Statement(fraktion.secondStatementIdentifier, fraktion.secondStatementTitle, fraktion.secondStatementBeschreibung, fraktion.secondStatementType, new StatementDependencyFraktion(fraktion));
        statements.add(statement);
    }
}
