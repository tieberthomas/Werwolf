package root.Persona.Rollen.Constants;

import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.SpäherZeigekarte;

public class SchnüfflerInformation {
    public String spielerName;
    public Fraktion fraktion;
    public SpäherZeigekarte tötend;
    public NebenrollenType nebenrollenType;
    public boolean isTarnumhang = false;

    public SchnüfflerInformation(String spielerName, Fraktion fraktion, SpäherZeigekarte tötend, NebenrollenType nebenrollenType) {
        this.spielerName = spielerName;
        this.fraktion = fraktion;
        this.tötend = tötend;
        this.nebenrollenType = nebenrollenType;
    }

    public SchnüfflerInformation(String spielerName) {
        this.spielerName = spielerName;
        isTarnumhang = true;
    }
}