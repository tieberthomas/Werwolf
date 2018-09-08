package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Nachbar extends Nebenrolle {
    public static String title = "Spieler wählen";
    public static final String beschreibung = "Nachbar erwacht, wählt einen Spieler und erfährt wer diesen Spieler besucht hat";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String infoTitle = "Besucher von ";

    public static final String name = "Nachbar";
    public static final String imagePath = ImagePath.NACHBAR_KARTE;
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

            if(showTarnumhang(this, chosenPlayer)) {
                return new FrontendControl(new Tarnumhang_NebenrollenType());
            }

            Spieler nachbarSpieler = game.findSpielerPerRolle(Nachbar.name);
            FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesucherStrings(chosenPlayer, nachbarSpieler));
            info.title = infoTitle + chosenPlayer.name;

            return info;
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
    public NebenrollenType getType() {
        return type;
    }

    public static ArrayList<String> getBesucherStrings(Spieler beobachteterSpieler, Spieler beobachter) {
        ArrayList<String> besucher = new ArrayList<>();

        if (beobachteterSpieler != null) {
            for (Spieler spieler : game.getLivingPlayer()) {
                if (spieler.hauptrolle.besucht != null && spieler.hauptrolle.besucht.name.equals(beobachteterSpieler.name) ||
                        (spieler.nebenrolle.besucht != null && spieler.nebenrolle.besucht.name.equals(beobachteterSpieler.name))) {
                    besucher.add(spieler.name);
                }

                if (!besucher.contains(spieler.name) && spieler.nebenrolle.getName().equals(Analytiker.name)) {
                    Analytiker analytiker = (Analytiker) spieler.nebenrolle;
                    if (analytiker.besuchtAnalysieren != null && analytiker.besuchtAnalysieren.name.equals(beobachteterSpieler.name)) {
                        besucher.add(spieler.name);
                    }
                }
            }

            if (beobachter != null) {
                besucher.remove(beobachter.name);
            }
        }

        return besucher;
    }
}
