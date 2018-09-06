package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Hauptrollen.Bürger.Bestienmeister;
import root.Persona.Nebenrolle;
import root.Spieler;

public class Archivar extends Nebenrolle {
    public static String title = "Spieler wählen";
    public static final String beschreibung = "Archivar erwacht und lässt sich Auskunft über die Bonusrolle eines Mitspielers geben";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE_INFO;
    public static final String name = "Archivar";
    public static final String imagePath = ImagePath.ARCHIVAR_KARTE;
    public static boolean spammable = true;
    public NebenrollenType type = new Informativ();

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            NebenrollenType type = chosenPlayer.nebenrolle.getType();

            if (playerIsBestienmeister(chosenPlayer) && archivarIsNotBuerger()) {
                type = new Tarnumhang_NebenrollenType();
            }


            return new FrontendControl(FrontendControlType.IMAGE, type.title, type.imagePath);
        }

        return new FrontendControl();
    }

    private boolean playerIsBestienmeister(Spieler player) {
        return player.hauptrolle.getName().equals(Bestienmeister.name);
    }

    private boolean archivarIsNotBuerger() {
        Spieler spieler = game.findSpielerPerRolle(name);

        return !spieler.hauptrolle.getFraktion().getName().equals(Bürger.name);
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
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }
}
