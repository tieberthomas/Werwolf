package root.Logic.KillLogic;

import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Vampire;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Bonusrollen.*;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Logic.Phases.NormalNight;
import root.Logic.Spieler;

public class Angriff {
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
        schamaninSpieler = Game.game.findSpielerPerRolle(Schamanin.ID);
        prostituierteSpieler = Game.game.findSpielerPerRolle(Prostituierte.ID);

        NormalNight.angriffe.add(this);

        if (refreshSchamaninSchutz && opferIsGeschütztSchamanin()) {
            schamaninSpieler.hauptrolle.abilityCharges++;
        }

        if (killIsSuccessfull()) {
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

    private boolean killIsSuccessfull() {
        if (defendable) {
            if (opfer.geschützt) {
                return false;
            }
            //TODO make framework for one-time-Schutz
            if (opfer.bonusrolle.equals(DunklesLicht.ID)) {
                DunklesLicht dunklesLicht = (DunklesLicht) opfer.bonusrolle;
                if (dunklesLicht.schutzAktiv) {
                    dunklesLicht.consumeSchutz();
                    return false;
                }
            }
            if (opfer.bonusrolle.equals(Tarnumhang.ID)) {
                Tarnumhang tarnumhang = (Tarnumhang) opfer.bonusrolle;
                if (tarnumhang.schutzAktiv) {
                    tarnumhang.consumeSchutz();
                    return false;
                }
            }
            if (opfer.bonusrolle.equals(Wolfspelz.ID) && täterFraktion.equals(Werwölfe.ID)) {
                Wolfspelz wolfspelz = (Wolfspelz) opfer.bonusrolle;
                if (wolfspelz.schutzAktiv) {
                    wolfspelz.consumeSchutz();
                    return false;
                }
            }
            if (opfer.bonusrolle.equals(Vampirumhang.ID) && täterFraktion.equals(Vampire.ID)) {
                Vampirumhang vampirumhang = (Vampirumhang) opfer.bonusrolle;
                if (vampirumhang.schutzAktiv) {
                    vampirumhang.consumeSchutz();
                    return false;
                }
            }
        }

        return !(hideable && opferIsHiding());
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
