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
import root.Persona.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Phases.NightBuilding.Constants.IndieStatements;
import root.Phases.NightBuilding.Constants.ProgrammStatements;
import root.mechanics.Game;

import java.util.ArrayList;

public class NormalNightStatementBuilder {
    public static Game game;

    public static ArrayList<Statement> normaleNachtBuildStatements() {
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
        //Detektiv erwacht und schätzt die Anzahl der Bürger
        addStatementRolle(statements, Schamanin.NAME);

        statements.add(ProgrammStatements.getSchützeStatement());

        addStatementRolle(statements, LadyAleera.NAME);
        addStatementRolle(statements, Prostituierte.NAME);

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

        addStatementRolle(statements, GrafVladimir.NAME);

        addStatementRolle(statements, Nachbar.NAME);
        addStatementRolle(statements, Spurenleser.NAME);

        addStatementRolle(statements, Wahrsager.NAME);

        if (game.getSecondaryRoleInGameNames().contains(Konditorlehrling.NAME)) {
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
        Statement statement = new StatementRolle(rolle);
        statements.add(statement);
    }

    private static void addSecondStatementRolle(ArrayList<Statement> statements, String rollenName) {
        Rolle rolle = Rolle.findRolle(rollenName);
        if (rolle != null) {
            Statement statement = new StatementRolle(rolle.secondStatementBeschreibung, rolle.secondStatementTitle, rolle.name, rolle.secondStatementType);
            statements.add(statement);
        }
    }

    private static void addStatementFraktion(ArrayList<Statement> statements, String fraktionsName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
        Statement statement = new StatementFraktion(fraktion);
        statements.add(statement);
    }

    private static void addSecondStatementFraktion(ArrayList<Statement> statements, String fraktionsName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
        if (fraktion != null) {
            Statement statement = new StatementFraktion(fraktion.secondStatementBeschreibung, fraktion.secondStatementTitle, fraktion.name, fraktion.secondStatementType);
            statements.add(statement);
        }
    }
}
