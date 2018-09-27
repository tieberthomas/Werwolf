package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tarnumhang extends Bonusrolle {
    public static final String STATEMENT_TITLE = "Träger eines Umhangs";
    public static final String STATEMENT_BESCHREIBUNG = "Träger des Tarnumhangs erwacht und erfährt einen Mitspieler, bei dem ein Umhhang liegt";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;
    public static final String KEINE_UMHÄNGE = "Es sind keine Umhangträger mehr im Spiel";

    public static final String NAME = "Tarnumhang";
    public static final String IMAGE_PATH = ImagePath.TARNUMHANG_KARTE;
    public static final NebenrollenType TYPE = new Tarnumhang_NebenrollenType();
    public static final Color COLOR = Color.BLACK;
    private ArrayList<String> umhänge = new ArrayList<>(Arrays.asList(
            Lamm.NAME, Wolfspelz.NAME, Vampirumhang.NAME, Schattenkutte.NAME));

    public ArrayList<String> seenPlayers = new ArrayList<>(); //TODO wenn dieb Tarnumhang nimmt dann neu anlegen

    public Tarnumhang() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getInfo() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.LIST;
        frontendControl.title = STATEMENT_TITLE;
        frontendControl.dropdownStrings = new ArrayList<>();
        String unseenTräger = getUnseenTräger();
        frontendControl.dropdownStrings.add(unseenTräger);

        return frontendControl;
    }

    private String getUnseenTräger() {
        ArrayList<String> allTräger = getAllTräger();
        allTräger.removeAll(seenPlayers);

        if (allTräger.size() == 0) {
            seenPlayers = new ArrayList<>();
            allTräger = getAllTräger();
        }

        if (allTräger.size() > 0) {

            int numberOfTräger = allTräger.size();
            Random random = new Random();
            int decision = random.nextInt(numberOfTräger);

            return allTräger.get(decision);
        } else {
            return KEINE_UMHÄNGE;
        }
    }

    private ArrayList<String> getAllTräger() {
        ArrayList<String> allTräger = new ArrayList<>();

        for (Spieler spieler : game.spieler) {
            if (umhänge.contains(spieler.bonusrolle.name)) {
                allTräger.add(spieler.name);
            }
        }

        return allTräger;
    }
}
