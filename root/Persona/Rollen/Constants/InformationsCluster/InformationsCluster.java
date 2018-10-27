package root.Persona.Rollen.Constants.InformationsCluster;

import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.ArrayList;
import java.util.List;

public abstract class InformationsCluster {
    private List<Zeigekarte> informations;

    public Zeigekarte getWrongInformation(Zeigekarte alreadyExistingInformations) {
        List<Zeigekarte> tmpInformations = new ArrayList<>(informations);
        tmpInformations.remove(alreadyExistingInformations);
        return tmpInformations.get(0); //TODO replace with rand call
    }
}
