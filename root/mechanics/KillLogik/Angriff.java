package root.mechanics.KillLogik;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Prostituierte;
import root.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Phases.NormalNight;
import root.Spieler;
import root.mechanics.Game;

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

    private Spieler schamaninSpieler;
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
        schamaninSpieler = game.findSpielerPerRolle(Schamanin.ID);
        prostituierteSpieler = game.findSpielerPerRolle(Prostituierte.ID);

        NormalNight.angriffe.add(this);

        if (refreshSchamaninSchutz && opferIsGeschütztSchamanin()) {
            schamaninSpieler.hauptrolle.abilityCharges++;
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
            if (opfer.bonusrolle.equals(Wolfspelz.ID) && täterFraktion.equals(Werwölfe.ID)) {
                return false;
            }
            if (opfer.bonusrolle.equals(Vampirumhang.ID) && täterFraktion.equals(Vampire.ID)) {
                return false;
            }
        }

        if (hideable && opferIsHiding()) {
            return false;
        }

        return true;
    }

    private boolean opferIsGeschütztSchamanin() {
        return schamaninSpieler != null && Rolle.rolleLebend(Schamanin.ID) && opfer.equals(schamaninSpieler.hauptrolle.besucht);
    }

    private boolean opferIsHiding() {
        return prostituierteSpieler != null && opfer.equals(prostituierteSpieler) && !opfer.equals(Prostituierte.host);
    }

    private boolean opferIsHostProstituierte() {
        return prostituierteSpieler != null && opfer.equals(Prostituierte.host);
    }
}
