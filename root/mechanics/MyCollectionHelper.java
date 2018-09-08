package root.mechanics;

import root.Persona.Hauptrolle;
import root.Persona.Nebenrolle;

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

    public static ArrayList<Nebenrolle> removeAllNebenrollen(ArrayList<Nebenrolle> list, ArrayList<Nebenrolle> elementsToRemove) {
        for (Nebenrolle nebenrolle : elementsToRemove) {
            int index = list.indexOf(nebenrolle);
            if (index != (-1))
                list.remove(index);
        }

        return list;
    }
}
