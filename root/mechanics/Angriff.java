package root.mechanics;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Prostituierte;
import root.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Phases.NormalNight;
import root.Spieler;

public class Angriff {
    public static Game game;

    public Spieler opfer;
    public Spieler täter;
    public Fraktion täterFraktion;
    public boolean fraktionsTäter;

    public boolean defendable;
    public boolean hideable;
    public boolean ressurectable;
    public boolean killVisitors;
    public boolean refreshSchamaninSchutz;

    private Hauptrolle schamanin;
    private Spieler prostituierteSpieler;

    public Angriff(Spieler opfer, boolean defendable, boolean hideable, boolean ressurectable, boolean killVisitors, boolean refreshSchamaninSchutz) {
        this.opfer = opfer;
        this.täter = null;
        this.täterFraktion = null;
        this.fraktionsTäter = false;

        this.defendable = defendable;
        this.hideable = hideable;
        this.ressurectable = ressurectable;
        this.killVisitors = killVisitors;
        this.refreshSchamaninSchutz = refreshSchamaninSchutz;
    }

    public Angriff(Spieler opfer, Spieler täter, boolean defendable, boolean hideable, boolean ressurectable, boolean killVisitors, boolean refreshSchamaninSchutz) {
        this.opfer = opfer;
        this.täter = täter;
        this.täterFraktion = täter.hauptrolle.fraktion;
        this.fraktionsTäter = false;

        this.defendable = defendable;
        this.hideable = hideable;
        this.ressurectable = ressurectable;
        this.killVisitors = killVisitors;
        this.refreshSchamaninSchutz = refreshSchamaninSchutz;
    }

    public Angriff(Spieler opfer, Fraktion täterFraktion, boolean defendable, boolean hideable, boolean ressurectable, boolean killVisitors, boolean refreshSchamaninSchutz) {
        this.opfer = opfer;
        this.täter = null;
        this.täterFraktion = täterFraktion;
        this.fraktionsTäter = true;

        this.defendable = defendable;
        this.hideable = hideable;
        this.ressurectable = ressurectable;
        this.killVisitors = killVisitors;
        this.refreshSchamaninSchutz = refreshSchamaninSchutz;
    }

    public Angriff(Spieler opfer, Spieler täter, Fraktion täterFraktion, boolean fraktionsTäter, boolean defendable, boolean hideable, boolean ressurectable, boolean killVisitors, boolean refreshSchamaninSchutz) {
        this.opfer = opfer;
        this.täter = täter;
        this.täterFraktion = täterFraktion;
        this.fraktionsTäter = fraktionsTäter;

        this.defendable = defendable;
        this.hideable = hideable;
        this.ressurectable = ressurectable;
        this.killVisitors = killVisitors;
        this.refreshSchamaninSchutz = refreshSchamaninSchutz;
    }

    public void execute() {
        schamanin = game.findSpielerPerRolle(Schamanin.NAME).hauptrolle;
        prostituierteSpieler = game.findSpielerPerRolle(Prostituierte.NAME);

        NormalNight.angriffe.add(this);

        if (refreshSchamaninSchutz && opferIsGeschütztSchamanin()) {
            schamanin.abilityCharges++;
        }

        if (isDeadly()) {
            NormalNight.opfer.add(new Opfer(this));

            if (!ressurectable) {
                opfer.ressurectable = false;
            }

            if (killVisitors && opferIsHostProstituierte()) {
                Angriff angriffProstituierte = new Angriff(prostituierteSpieler, täter, täterFraktion, fraktionsTäter,
                        defendable, false, ressurectable, false, false);
                angriffProstituierte.execute();
            }
        }
    }

    private boolean isDeadly() {
        if (defendable) {
            if (opfer.geschützt) {
                return false;
            }
            if (opfer.bonusrolle.equals(Wolfspelz.NAME) && täterFraktion.equals(Werwölfe.NAME)) {
                return false;
            }
            if (opfer.bonusrolle.equals(Vampirumhang.NAME) && täterFraktion.equals(Vampire.NAME)) {
                return false;
            }
        }

        if (hideable && opferIsHiding()) {
            return false;
        }

        return true;
    }

    private boolean opferIsGeschütztSchamanin() {
        return Rolle.rolleLebend(Schamanin.NAME) && opfer.equals(schamanin.besucht);
    }

    private boolean opferIsHiding() {
        return prostituierteSpieler != null && opfer.equals(prostituierteSpieler) && !opfer.equals(Prostituierte.host);
    }

    private boolean opferIsHostProstituierte() {
        return prostituierteSpieler != null && opfer.equals(Prostituierte.host);
    }
}
