package root.Persona.Rollen.Constants.BonusrollenType;

import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

public class BonusrollenType extends Zeigekarte {
    public static BonusrollenType getBonusrollenType(String name) {
        Aktiv aktiv = new Aktiv();
        Passiv passiv = new Passiv();
        Informativ informativ = new Informativ();

        if (name.equals(aktiv.name)) {
            return aktiv;
        } else if (name.equals(passiv.name)) {
            return passiv;
        } else if (name.equals(informativ.name)) {
            return informativ;
        } else {
            System.out.println("Zeigekarte.getBonusrollenType wurde mit ungültigem Namen gecalled.");
            return null;
        }
    }
}
