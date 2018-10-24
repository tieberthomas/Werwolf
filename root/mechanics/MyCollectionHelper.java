package root.mechanics;

import root.Persona.Bonusrolle;
import root.Persona.Hauptrolle;

import java.util.ArrayList;

public class MyCollectionHelper {

    public static ArrayList<Hauptrolle> removeAllHauptrollen(ArrayList<Hauptrolle> list, ArrayList<Hauptrolle> elementsToRemove) {
        for (Hauptrolle hauptrolle : elementsToRemove) {
            int index = list.indexOf(hauptrolle);
            if (index != (-1))
                list.remove(index);
        }

        return list;
    }

    public static ArrayList<Bonusrolle> removeAllBonusrollen(ArrayList<Bonusrolle> list, ArrayList<Bonusrolle> elementsToRemove) {
        for (Bonusrolle bonusrolle : elementsToRemove) {
            int index = list.indexOf(bonusrolle);
            if (index != (-1))
                list.remove(index);
        }

        return list;
    }
}
