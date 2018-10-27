package root.Persona.Rollen.Constants.InformationsCluster;

import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.ArrayList;
import java.util.Arrays;

public class NebenrollenInfo extends InformationsCluster {
    private ArrayList<Zeigekarte> informations = new ArrayList<>(Arrays.asList(
            new Aktiv(), new Passiv(), new Informativ()));
}
