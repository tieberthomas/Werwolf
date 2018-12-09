package root.Logic.Persona.Rollen.Constants.InformationsCluster;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.List;
import java.util.stream.Collectors;

public class FraktionsInfo extends InformationsCluster {
    public static List<Zeigekarte> informations;

    public static Zeigekarte getWrongInformation(Zeigekarte existingInformation) {
        generateFraktionsInfos();
        return InformationsCluster.getWrongInformation(informations, existingInformation);
    }

    public static void generateFraktionsInfos() {
        List<Fraktion> fraktionen = Fraktion.getLivingFraktionen();
        informations = fraktionen.stream().map(fraktion -> fraktion.zeigekarte).collect(Collectors.toList());
    }
}
