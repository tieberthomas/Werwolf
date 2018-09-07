package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.awt.*;
import java.util.ArrayList;

public class Tarnumhang extends Nebenrolle {
    public static String title = "Träger eines Umhangs";
    public static final String beschreibung = "Träger des Tarnumhangs erwacht und erfährt den Träger eines Umhangs";
    public static StatementType statementType = StatementType.ROLLE_INFO;

    public static final String name = "Tarnumhang";
    public static final String imagePath = ImagePath.TARNUMHANG_KARTE;
    public static boolean spammable = false;
    public static NebenrollenType type = new Tarnumhang_NebenrollenType();
    public Color farbe = Color.BLACK;
    public ArrayList<String> seenPlayers = new ArrayList<>();

    @Override
    public FrontendControl getInfo() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.LIST;
        frontendControl.title = title;
        frontendControl.strings = new ArrayList<>();
        String unseenTräger = getUnseenTräger();
        frontendControl.strings.add(unseenTräger);

        return frontendControl;
    }

    private String getUnseenTräger() {
        return "Thomas";
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

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
