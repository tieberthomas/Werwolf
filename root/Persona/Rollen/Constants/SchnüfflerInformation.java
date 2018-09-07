package root.Persona.Rollen.Constants;

import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;

public class SchnüfflerInformation {
    public Fraktion fraktion;
    public boolean tötend;
    public NebenrollenType nebenrollenType;

    public SchnüfflerInformation(Fraktion fraktion, boolean tötend, NebenrollenType nebenrollenType) {
        this.fraktion = fraktion;
        this.tötend = tötend;
        this.nebenrollenType = nebenrollenType;
    }
}