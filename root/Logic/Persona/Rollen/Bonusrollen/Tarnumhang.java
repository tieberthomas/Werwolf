package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.FrontendObjectType;
import root.Controller.FrontendObject.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;
import root.Utils.Rand;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tarnumhang extends Bonusrolle {
    public static final String ID = "ID_Tarnumhang";
    public static final String NAME = "Tarnumhang";
    public static final String IMAGE_PATH = ImagePath.TARNUMHANG_KARTE;
    public static final BonusrollenType TYPE = new Informativ();
    public static final Color COLOR = Color.BLACK;

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Träger eines Umhangs";
    public static final String STATEMENT_BESCHREIBUNG = "Träger des Tarnumhangs erwacht und erfährt einen Mitspieler, bei dem ein Umhhang liegt";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_INFO;

    private static final String KEINE_UMHÄNGE = "Es sind keine Umhangträger mehr im Spiel";

    private List<String> umhänge = new ArrayList<>(Arrays.asList(
            Schafspelz.ID, Wolfspelz.ID, Vampirumhang.ID, Schattenkutte.ID));

    public List<String> seenSpieler = new ArrayList<>(); //TODO wenn dieb Tarnumhang nimmt dann neu anlegen

    public Tarnumhang() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
        this.color = COLOR;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendObject getInfo() {
        FrontendObject frontendObject = new FrontendObject();

        frontendObject.typeOfContent = FrontendObjectType.LIST;
        frontendObject.title = STATEMENT_TITLE;
        frontendObject.displayedStrings = new ArrayList<>();
        String unseenTräger = getUnseenTräger();
        frontendObject.displayedStrings.add(unseenTräger);

        return frontendObject;
    }

    private String getUnseenTräger() {
        List<String> allTräger = getAllTräger();
        allTräger.removeAll(seenSpieler);

        if (allTräger.size() == 0) {
            seenSpieler = new ArrayList<>();
            allTräger = getAllTräger();
        }

        if (allTräger.size() > 0) {
            return Rand.getRandomElement(allTräger);
        } else {
            return KEINE_UMHÄNGE;
        }
    }

    private List<String> getAllTräger() {
        List<String> allTräger = new ArrayList<>();

        for (Spieler spieler : Game.game.spieler) {
            if (umhänge.contains(spieler.bonusrolle.id)) {
                allTräger.add(spieler.name);
            }
        }

        return allTräger;
    }

    @Override
    public Zeigekarte isTötendInfo() {
        return new Tarnumhang_BonusrollenType();
    }

    @Override
    public Zeigekarte getFraktionInfo() {
        return new Tarnumhang_BonusrollenType();
    }

    @Override
    public BonusrollenType getBonusrollenTypeInfo() {
        return new Tarnumhang_BonusrollenType();
    }
}
