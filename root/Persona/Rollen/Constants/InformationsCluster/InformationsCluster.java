package root.Persona.Rollen.Constants.InformationsCluster;

import root.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.mechanics.Rand;

import java.util.ArrayList;
import java.util.List;

public abstract class InformationsCluster {
    public static Zeigekarte getWrongInformation(List<Zeigekarte> informations, Zeigekarte existingInformation) {
        if(new Tarnumhang_BonusrollenType().equals(existingInformation)) {
            return existingInformation;
        }
        List<Zeigekarte> tmpInformations = new ArrayList<>(informations);
        tmpInformations.remove(existingInformation);
        return Rand.getRandomElement(tmpInformations);
    }
}
