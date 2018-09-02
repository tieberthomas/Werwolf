package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.mechanics.Opfer;

import java.util.ArrayList;

public class Chemiker extends Hauptrolle {
    public static String title = "Opfer wiederbeleben";
    public static final String beschreibung = "Chemiker erwacht und kann ein Wolfsopfer dieser Nacht wiederbeleben und zum Wolfsrudel hinzufügen";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;
    public static final String name = "Chemiker";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ImagePath.CHEMIKER_KARTE;
    public static boolean spammable = true;

    @Override
    public FrontendControl getDropdownOptions() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN_LIST;
        frontendControl.strings = findResurrectableOpfer();
        frontendControl.strings.add("");

        return frontendControl;
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Opfer chosenOpfer = Opfer.findOpfer(chosenOption);
        if (chosenOpfer != null) {
            besucht = chosenOpfer.opfer;

            Opfer.deadVictims.remove(chosenOpfer);
            chosenOpfer.opfer.hauptrolle = new Werwolf();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public StatementType getStatementType() {
        return statementType;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    public ArrayList<String> findResurrectableOpfer() {
        ArrayList<String> resurrectableOpfer = new ArrayList<>();

        for (Opfer currentOpfer : Opfer.deadVictims) {
            String fraktionOpfer = currentOpfer.opfer.hauptrolle.getFraktion().getName();
            String fraktionTäter = currentOpfer.täter.hauptrolle.getFraktion().getName();
            String hauptrolleTäter = currentOpfer.täter.hauptrolle.getName();

            if (currentOpfer.opfer.ressurectable) {
                if (currentOpfer.fraktionsTäter && fraktionTäter.equals(Werwölfe.name) || hauptrolleTäter.equals(Wölfin.name)) {
                    if (!fraktionOpfer.equals(Werwölfe.name)) {
                        if (!resurrectableOpfer.contains(currentOpfer.opfer.name)) {
                            resurrectableOpfer.add(currentOpfer.opfer.name);
                        }
                    }
                }
            }
        }

        return resurrectableOpfer;
    }
}