package root.Phases.NightBuilding;

import root.Rollen.Constants.WölfinState;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrollen.Bürger.*;
import root.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Rollen.Hauptrollen.Werwölfe.Schreckenswolf;
import root.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Rollen.Nebenrollen.*;
import root.Rollen.Rolle;
import root.mechanics.Game;

import java.util.ArrayList;

public class NormalNightStatementBuilder {
    public static Game game;

    public static ArrayList<Statement> normaleNachtBuildStatements() {
        ArrayList<Statement> statements = new ArrayList<>();

        //addStatementIndie(ALLE_SCHLAFEN_EIN, ALLE_SCHLAFEN_EIN_TITLE, StatementType.SHOW_TITLE);

        if (Wirt.freibierCharges > 0) {
            statements.add(getStatement(Wirt.name));
        }

        if (Totengräber.getNehmbareNebenrollen().size() > 0) {
            statements.add(getStatement(Totengräber.name));
        }
        statements.add(getStatement(Gefängniswärter.name));

        if (game.mitteHauptrollen.size() > 0) {
            statements.add(getStatement(Überläufer.name));
        }
        statements.add(getStatement(HoldeMaid.name));
        statements.add(getStatement(Nachbar.name));
        statements.add(getStatement(Spurenleser.name));

        //addProgrammStatement(PROGRAMM_SCHÜTZE);

        statements.add(getStatement(LadyAleera.name));
        statements.add(getStatement(Prostituierte.name));

        statements.add(getStatement(Riese.name));
        statements.add(getStatementFraktion(Vampire.name));
        statements.add(getStatement(GrafVladimir.name));
        statements.add(getStatementFraktion(Werwölfe.name));
        if (Wölfin.state == WölfinState.TÖTEND) {
            statements.add(getStatement(Wölfin.name));
        }
        statements.add(getStatement(Schreckenswolf.name));

        statements.add(getStatementFraktion(Schattenpriester_Fraktion.name));
        statements.add(getSecondStatementFraktion(Schattenpriester_Fraktion.name));
        statements.add(getStatement(Chemiker.name));
        statements.add(getSecondStatement(Chemiker.name));

        statements.add(getStatement(MissVerona.name));
        statements.add(getStatement(Analytiker.name));
        statements.add(getStatement(Archivar.name));
        statements.add(getStatement(Seherin.name));
        statements.add(getStatement(Orakel.name));
        statements.add(getStatement(Späher.name));

        statements.add(getSecondStatement(Nachbar.name));
        statements.add(getSecondStatement(Spurenleser.name));

        //addProgrammStatement(PROGRAMM_WAHRSAGER);
        if (game.getLivingPlayer().size() > 4) {
            statements.add(getStatement(Wahrsager.name));
        }

        if (game.getSecondaryRoleInGameNames().contains(Konditorlehrling.name)) {
            statements.add(getStatement(Konditorlehrling.name));
        } else {
            statements.add(getStatement(Konditor.name));
        }

        //addStatementIndie(ALLE_WACHEN_AUF, ALLE_WACHEN_AUF_TITLE, StatementType.SHOW_TITLE);

        //addProgrammStatement(PROGRAMM_OPFER);
        //addStatementIndie(OPFER, OPFER_TITLE, StatementType.INDIE);

        statements.add(getSecondStatement(Schreckenswolf.name));

        if (Wölfin.state == WölfinState.TÖTEND) {
            statements.add(getSecondStatement(Wölfin.name));
        }

        //addProgrammStatement(PROGRAMM_TORTE);

        return statements;
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
            return new StatementFraktion(fraktion.secondBeschreibung, fraktion.secondTitle, fraktion.getName(), fraktion.secondStatementType);
        } else {
            return new Statement();
        }
    }
}
