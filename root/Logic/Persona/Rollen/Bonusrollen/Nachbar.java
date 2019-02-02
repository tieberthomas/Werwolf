package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.FrontendObject;
import root.Controller.FrontendObject.ImageFrontendObject;
import root.Controller.FrontendObject.ListFrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Nachbar extends Bonusrolle {
    public static final String ID = "ID_Nachbar";
    public static final String NAME = "Nachbar";
    public static final String IMAGE_PATH = ImagePath.NACHBAR_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Nachbar erwacht, wählt einen Spieler und erfährt wer diesen Spieler besucht hat";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE_INFO;

    public static final String INFO_TITLE = "Besucher von ";

    public Nachbar() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.selfuseable = true;
        this.spammable = true;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public FrontendObject processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            if (showTarnumhang(this, chosenSpieler)) {
                return new ImageFrontendObject(new Tarnumhang_BonusrollenType());
            }

            Spieler nachbarSpieler = Game.game.findSpielerPerRolle(Nachbar.ID);
            FrontendObject info = new ListFrontendObject(getBesucherStrings(chosenSpieler, nachbarSpieler));
            info.title = INFO_TITLE + chosenSpieler.name;

            return info;
        }

        return new FrontendObject();
    }

    public static List<String> getBesucherStrings(Spieler beobachteterSpieler, Spieler beobachter) {
        List<String> besucher = new ArrayList<>();

        if (beobachteterSpieler != null) {
            for (Spieler spieler : Game.game.getLivingSpieler()) {
                if (spieler.hauptrolle.besucht != null && spieler.hauptrolle.besucht.equals(beobachteterSpieler) ||
                        (spieler.bonusrolle.besucht != null && spieler.bonusrolle.besucht.equals(beobachteterSpieler))) {
                    besucher.add(spieler.name);
                }

                if (!besucher.contains(spieler.name) && spieler.bonusrolle.equals(Analytiker.ID)) {
                    Analytiker analytiker = (Analytiker) spieler.bonusrolle;
                    if (analytiker.besuchtAnalysieren != null && analytiker.besuchtAnalysieren.equals(beobachteterSpieler)) {
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
