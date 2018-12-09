package root.Logic.Persona.Rollen.Constants;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.SpäherZeigekarte;

public class SchnüfflerInformation {
    public String spielerName;
    public Fraktion fraktion;
    public SpäherZeigekarte tötend;
    public BonusrollenType bonusrollenType;
    public boolean isTarnumhang = false;

    public SchnüfflerInformation(String spielerName, Fraktion fraktion, SpäherZeigekarte tötend, BonusrollenType bonusrollenType) {
        this.spielerName = spielerName;
        this.fraktion = fraktion;
        this.tötend = tötend;
        this.bonusrollenType = bonusrollenType;
    }

    public SchnüfflerInformation(String spielerName) {
        this.spielerName = spielerName;
        isTarnumhang = true;
    }
}