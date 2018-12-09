package root.Logic.Persona.Rollen.Constants.InformationsCluster;

import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Utils.Rand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class InformationsCluster {
    public static Zeigekarte getWrongInformation(List<Zeigekarte> informations, Zeigekarte existingInformation) {
        if (new Tarnumhang_BonusrollenType().equals(existingInformation)) {
            return existingInformation;
        }
        List<Zeigekarte> tmpInformations = new ArrayList<>(informations);
        tmpInformations = tmpInformations.stream().filter(info -> !info.equals(existingInformation)).collect(Collectors.toList());
        return Rand.getRandomElement(tmpInformations);
    }
}
