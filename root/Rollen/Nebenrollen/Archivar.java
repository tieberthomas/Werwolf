package root.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrollen.Bürger.Bestienmeister;
import root.Rollen.Nebenrolle;
import root.Rollen.Constants.NebenrollenTyp;
import root.Spieler;

public class Archivar extends Nebenrolle {
    private static final String PASSIV_TITLE = "Passiv";
    private static final String AKTIV_TITLE = "Aktiv";
    private static final String INFORMATIV_TITLE = "Informativ";
    private static final String TARNUMHANG_TITLE = "Tarnumhang";

    public static String title = "Spieler wählen";
    public static final String beschreibung = "Archivar erwacht und lässt sich Auskunft über die Bonusrolle eines Mitspielers geben";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE_INFO;
    public static final String name = "Archivar";
    public static final String imagePath = ImagePath.ARCHIVAR_KARTE;
    public static boolean spammable = true;
    public NebenrollenTyp type = NebenrollenTyp.INFORMATIV;

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            if (chosenPlayer.hauptrolle.getName().equals(Bestienmeister.name)) {
                Spieler spieler = game.findSpielerPerRolle(name);

                if (!spieler.hauptrolle.getFraktion().getName().equals(Bürger.name)) {
                    return new FrontendControl(FrontendControlType.IMAGE, TARNUMHANG_TITLE, ImagePath.TARNUMHANG);
                }
            }

            switch (chosenPlayer.nebenrolle.getType()) {
                case AKTIV:
                    return new FrontendControl(FrontendControlType.IMAGE, AKTIV_TITLE, ImagePath.AKTIV);

                case PASSIV:
                    return new FrontendControl(FrontendControlType.IMAGE, PASSIV_TITLE, ImagePath.PASSIV);

                case INFORMATIV:
                    return new FrontendControl(FrontendControlType.IMAGE, INFORMATIV_TITLE, ImagePath.INFORMATIV);

                case TARNUMHANG:
                    return new FrontendControl(FrontendControlType.IMAGE, TARNUMHANG_TITLE, ImagePath.TARNUMHANG);
            }
        }

        return new FrontendControl();
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
    public NebenrollenTyp getType() {
        return type;
    }
}
