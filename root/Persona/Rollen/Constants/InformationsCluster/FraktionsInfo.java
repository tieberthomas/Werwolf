package root.Persona.Rollen.Constants.InformationsCluster;

import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FraktionsInfo extends InformationsCluster {
    public static List<Zeigekarte> informations;

    public static Zeigekarte getWrongInformation(Zeigekarte existingInformation) {
        generateFraktionsInfos();
        return InformationsCluster.getWrongInformation(informations, existingInformation);
    }

    public static void generateFraktionsInfos() {
        ArrayList<Fraktion> fraktionen = Fraktion.getLivingFraktionen();
        informations = fraktionen.stream().map(fraktion -> fraktion.zeigekarte).collect(Collectors.toList());
    }
}
