package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tarnumhang extends Nebenrolle {
    public static String title = "Träger eines Umhangs";
    public static final String beschreibung = "Träger des Tarnumhangs erwacht und erfährt einen Mitspieler, bei dem ein Umhhang liegt";
    public static StatementType statementType = StatementType.ROLLE_INFO;
    public static String KEINE_UMHÄNGE = "Es sind keine Umhangträger mehr im Spiel";

    public static final String NAME = "Tarnumhang";
    public static final String IMAGE_PATH = ImagePath.TARNUMHANG_KARTE;
    public static boolean spammable = false;
    public static NebenrollenType type = new Tarnumhang_NebenrollenType();
    public Color farbe = Color.BLACK;
    private ArrayList<String> umhänge = new ArrayList<>(Arrays.asList(
            Lamm.NAME, Wolfspelz.NAME, Vampirumhang.NAME, Schattenkutte.NAME));

    public ArrayList<String> seenPlayers = new ArrayList<>(); //TODO wenn dieb Tarnumhang nimmt dann neu anlegen

    public Tarnumhang() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public FrontendControl getInfo() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.LIST;
        frontendControl.title = title;
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
            if (umhänge.contains(spieler.nebenrolle.name)) {
                allTräger.add(spieler.name);
            }
        }

        return allTräger;
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
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
