package root.Frontend.Frame;

import root.Frontend.Factories.SpielerPageElementFactory;
import root.Frontend.Factories.SpielerPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Utils.TimeUpdater;
import root.Logic.Game;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Day;
import root.Logic.Phases.PhaseManager;
import root.Logic.Phases.PhaseMode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SpielerFrame extends MyFrame {
    private ErzählerFrame erzählerFrame;
    public SpielerPageFactory pageFactory;
    public SpielerPageElementFactory pageElementFactory;

    public JLabel comboBox1Label;
    public JLabel comboBox2Label;
    public JLabel comboBox3Label;

    public JLabel clockLabel;

    public Page blankPage;
    public Page dropDownPage;

    public int mode = SpielerFrameMode.blank;
    public String title = "";

    public SpielerFrame(ErzählerFrame erzählerFrame) {
        WINDOW_TITLE = "Spieler Fenster";
        this.erzählerFrame = erzählerFrame;

        pageFactory = new SpielerPageFactory(this);
        pageElementFactory = new SpielerPageElementFactory(this);

        comboBox1Label = new JLabel("");
        comboBox2Label = new JLabel("");
        comboBox3Label = new JLabel("");
        clockLabel = new JLabel();
        TimeUpdater.spielerframe = this;
        TimeUpdater.startTimeUpdateThread();

        frameJpanel = generateDefaultPanel();

        generateAllPages();

        this.setLocation(erzählerFrame.frameJpanel.getWidth() + 20, 0);

        showFrame();
    }

    private void generateAllPages() {
        blankPage = new Page();
        dropDownPage = pageFactory.generateDropdownPage("", 1);
    }

    public void refreshPlayerSetupPage(List<String> playersInGame) {
        buildScreenFromPage(pageFactory.generateListPage("Spieler", playersInGame));
    }

    public void refreshHauptrolleSetupPage(List<String> mainrolesInGame) {
        buildScreenFromPage(pageFactory.generateListPage("Hauptrollen", mainrolesInGame));
    }

    public void refreshBonusrolleSetupPage(List<String> bonusrolesInGame) {
        buildScreenFromPage(pageFactory.generateListPage("Bonusrollen", bonusrolesInGame));
    }

    public void refreshSecondarySpecifySetupPage() {
        List<String> hauptrollen = new ArrayList<>();
        hauptrollen.addAll(Game.game.getHauptrolleInGameNames());

        List<String> bonusrollen = new ArrayList<>();
        bonusrollen.addAll(Game.game.getBonusrolleInGameNames());
        buildScreenFromPage(pageFactory.generateDoubleListPage(hauptrollen, bonusrollen, "Hauptrollen", "Bonusrollen"));
    }

    public void generateDayPage() {
        boolean freibierDay = false;
        title = Day.dayTitle;
        if (PhaseManager.phaseMode == PhaseMode.FREIBIER_DAY) {
            mode = SpielerFrameMode.freibierPage;
            freibierDay = true;
        }

        currentPage = pageFactory.generateDayPage(Game.game.getPossibleInGameHauptrolleNames(), Game.game.getPossibleInGameBonusrolleNames(), freibierDay);
        buildScreenFromPage(currentPage);
    }

    public void combobox1Changed(String newText) {
        if (mode == SpielerFrameMode.dropDownText) {
            if (comboBox1Label != null && newText != null) {
                comboBox1Label.setText(newText);
            }
        } else if (mode == SpielerFrameMode.dropDownImage) {
            Hauptrolle hauptrolle = Game.game.findHauptrollePerName(newText);
            String imagePath = hauptrolle.imagePath;
            Page imagePage = pageFactory.generateStaticImagePage(title, imagePath);
            buildScreenFromPage(imagePage);
            mode = SpielerFrameMode.dropDownImage;
        } else if (mode == SpielerFrameMode.freibierPage || mode == SpielerFrameMode.listMirrorPage) {
            Page dropDownPage = pageFactory.generateDropdownPage(title, 1);
            buildScreenFromPage(dropDownPage);
            comboBox1Label.setText(newText);
            mode = SpielerFrameMode.dropDownText;
        }
    }

    public void combobox2Changed(String newText) {
        if (mode == SpielerFrameMode.dropDownText) {
            if (comboBox2Label != null && newText != null) {
                comboBox2Label.setText(newText);
            }
        }
    }
}
