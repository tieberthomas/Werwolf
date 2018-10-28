package root.Persona.Rollen.Constants.InformationsCluster;

import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.ArrayList;
import java.util.Arrays;

public class BonusrollenInfo extends InformationsCluster {
    public static ArrayList<Zeigekarte> informations = new ArrayList<>(Arrays.asList(
            new Aktiv(), new Passiv(), new Informativ()));

    public static BonusrollenType getWrongInformation(Zeigekarte existingInformation) {
        return (BonusrollenType) InformationsCluster.getWrongInformation(informations, existingInformation);
    }
}
