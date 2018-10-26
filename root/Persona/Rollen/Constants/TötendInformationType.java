package root.Persona.Rollen.Constants;

import root.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

public enum TötendInformationType {
    TÖTEND(new Tötend()),
    NICHT_TÖTEND(new Nicht_Tötend()),
    TARNUMHANG(new Tarnumhang_BonusrollenType());

    public Zeigekarte zeigekarte;

    TötendInformationType(Zeigekarte zeigekarte) {
        this.zeigekarte = zeigekarte;
    }

    public static TötendInformationType getTötendInformationsType(boolean tötend) {
        if (tötend) {
            return TÖTEND;
        } else {
            return NICHT_TÖTEND;
        }
    }

    public boolean equals(TötendInformationType type) {
        return type != null && zeigekarte.equals(type.zeigekarte);
    }
}
