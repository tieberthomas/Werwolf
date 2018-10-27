package root.Persona.Rollen.Constants.InformationsCluster;

import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FraktionsInfo extends InformationsCluster {
    private List<Zeigekarte> informations;

    @Override
    public Zeigekarte getWrongInformation(Zeigekarte existingInformation) {
        generateFraktionsInfos();
        return super.getWrongInformation(existingInformation);
    }

    private void generateFraktionsInfos() {
        ArrayList<Fraktion> fraktionen = Fraktion.getLivingFraktionen();
        informations = fraktionen.stream().map(Fraktion::getZeigeKarte).collect(Collectors.toList());
    }
}
