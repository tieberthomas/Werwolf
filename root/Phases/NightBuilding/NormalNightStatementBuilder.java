package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rolle;
import root.Persona.Rollen.Constants.WölfinState;
import root.Persona.Rollen.Hauptrollen.Bürger.*;
import root.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Schreckenswolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Persona.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Persona.Rollen.Nebenrollen.*;
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
            addStatementRolle(statements, Wirt.name);
        }

        if (Totengräber.getNehmbareNebenrollen().size() > 0) {
            addStatementRolle(statements, Totengräber.name);
        }
        //Dieb erwacht und entscheidet ob er jemandes Bonusrolle stehlen möchte
        //Der Bestohlene erwacht und erhält eine neue Bonusrolle

        addStatementRolle(statements, Gefängniswärter.name);

        if (game.mitteHauptrollen.size() > 0) {
            addStatementRolle(statements, Überläufer.name);
        }

        addStatementRolle(statements, HoldeMaid.name);
        //Detektiv erwacht und schätzt die Anzahl der Bürger
        addStatementRolle(statements, Schamanin.name);

        statements.add(ProgrammStatements.getSchützeStatement());

        addStatementRolle(statements, LadyAleera.name);
        addStatementRolle(statements, Prostituierte.name);

        addStatementRolle(statements, Riese.name);
        addStatementFraktion(statements, Vampire.name);
        addStatementRolle(statements, GrafVladimir.name);
        addStatementFraktion(statements, Werwölfe.name);
        if (Wölfin.state == WölfinState.TÖTEND) {
            addStatementRolle(statements, Wölfin.name);
        }
        addStatementRolle(statements, Schreckenswolf.name);

        //Nachtfürst erwacht, schätzt die Anzahl der Opfer dieser Nacht und führt ggf. seine Tötung aus

        addStatementFraktion(statements, Schattenpriester_Fraktion.name);
        statements.add(getSecondStatementFraktion(Schattenpriester_Fraktion.name));
        addStatementRolle(statements, Chemiker.name);
        statements.add(getSecondStatement(Chemiker.name));

        addStatementRolle(statements, MissVerona.name);
        addStatementRolle(statements, Analytiker.name);
        addStatementRolle(statements, Archivar.name);
        addStatementRolle(statements, Schnüffler.name);
        addStatementRolle(statements, Seherin.name);
        addStatementRolle(statements, Orakel.name);
        addStatementRolle(statements, Späher.name);
        addStatementRolle(statements, Tarnumhang.name);

        addStatementRolle(statements, Nachbar.name);
        addStatementRolle(statements, Spurenleser.name);

        //zu einzelnen statements mergen
        statements.add(getSecondStatement(Nachbar.name));
        statements.add(getSecondStatement(Spurenleser.name));

        statements.add(ProgrammStatements.getWahrsagerProgrammStatement());
        if (game.getLivingPlayer().size() > 4) {
            addStatementRolle(statements, Wahrsager.name);
        }

        if (game.getSecondaryRoleInGameNames().contains(Konditorlehrling.name)) {
            addStatementRolle(statements, Konditorlehrling.name);
        } else {
            addStatementRolle(statements, Konditor.name);
        }

        statements.add(IndieStatements.getAlleWachenAufStatement());

        statements.add(ProgrammStatements.getOferStatement());
        statements.add(IndieStatements.getOpferStatement());

        statements.add(getSecondStatement(Schreckenswolf.name));

        if (Wölfin.state == WölfinState.TÖTEND) {
            statements.add(getSecondStatement(Wölfin.name));
        }

        statements.add(ProgrammStatements.getTortenProgrammStatement());

        return statements;
    }

    private static void addStatementRolle(ArrayList<Statement> statements, String rolle) {
        statements.add(getStatement(rolle));
    }

    private static void addStatementFraktion(ArrayList<Statement> statements, String fraktion) {
        statements.add(getStatementFraktion(fraktion));
    }

    private static Statement getStatement(String rollenName) {
        Rolle rolle = Rolle.findRolle(rollenName);
        return new StatementRolle(rolle);
    }

    private static Statement getSecondStatement(String rollenName) {
        Rolle rolle = Rolle.findRolle(rollenName);
        if(rolle!=null) {
            return new StatementRolle(rolle.getSecondBeschreibung(), rolle.getSecondTitle(), rolle.getName(), rolle.getSecondStatementType());
        } else {
            return new Statement();
        }
    }

    private static Statement getStatementFraktion(String fraktionsName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
        return new StatementFraktion(fraktion);
    }

    private static Statement getSecondStatementFraktion(String fraktionsName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionsName);
        if(fraktion!=null) {
            return new StatementFraktion(fraktion.getSecondBeschreibung(), fraktion.getSecondTitle(), fraktion.getName(), fraktion.getSecondStatementType());
        } else {
            return new Statement();
        }
    }
}
