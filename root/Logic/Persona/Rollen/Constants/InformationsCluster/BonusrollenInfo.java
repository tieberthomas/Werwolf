package root.Logic.Persona.Rollen.Constants.InformationsCluster;

import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BonusrollenInfo extends InformationsCluster {
    public static List<Zeigekarte> informations = new ArrayList<>(Arrays.asList(
            new Aktiv(), new Passiv(), new Informativ()));

    public static BonusrollenType getWrongInformation(Zeigekarte existingInformation) {
        return (BonusrollenType) InformationsCluster.getWrongInformation(informations, existingInformation);
    }
}
