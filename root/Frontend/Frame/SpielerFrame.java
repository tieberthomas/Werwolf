package root.Frontend.Frame;

import root.Frontend.Factories.SpielerPageElementFactory;
import root.Frontend.Factories.SpielerPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Utils.TimeUpdater;
import root.Phases.PhaseMode;
import root.Phases.Tag;
import root.mechanics.Game;

import javax.swing.*;
import java.util.ArrayList;

public class SpielerFrame extends MyFrame {
    Game game;
    ErzählerFrame erzählerFrame;
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

    public SpielerFrame(ErzählerFrame erzählerFrame, Game game) {
        this.game = game;
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

        refreshPlayerSetupPage();
    }

    public void generateAllPages() {
        blankPage = new Page();
        dropDownPage = pageFactory.generateDropdownPage("", 1);
    }

    public void refreshSetupPage() {
        if (erzählerFrame.currentPage.equals(erzählerFrame.playerSetupPage)) {
            refreshPlayerSetupPage();
        }

        if (erzählerFrame.currentPage.equals(erzählerFrame.hauptrolleSetupPage)) {
            refreshHauptrolleSetupPage();
        }

        if (erzählerFrame.currentPage.equals(erzählerFrame.bonusrolleSetupPage)) {
            refreshBonusrolleSetupPage();
        }

        if (erzählerFrame.currentPage.equals(erzählerFrame.playerSpecifiyPage)) {
            refreshSecondarySpecifySetupPage();
        }
    }

    public void refreshPlayerSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(game.getLivingPlayerStrings()));
    }

    public void refreshHauptrolleSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(game.getHauptrolleInGameNames()));
    }

    public void refreshBonusrolleSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(game.getBonusrolleInGameNames()));
    }

    public void refreshSecondarySpecifySetupPage() {
        ArrayList<String> hauptrollen = new ArrayList<>();
        hauptrollen.addAll(game.getHauptrolleInGameNames());

        ArrayList<String> bonusrollen = new ArrayList<>();
        bonusrollen.addAll(game.getBonusrolleInGameNames());
        buildScreenFromPage(pageFactory.generateDoubleListPage(hauptrollen, bonusrollen, "Hauptrollen", "Bonusrollen"));
    }

    public void generateDayPage() {
        boolean freibierTag = false;
        title = Tag.dayTitle;
        if (game.phaseMode == PhaseMode.FREIBIER_DAY) {
            mode = SpielerFrameMode.freibierPage;
            freibierTag = true;
        }

        currentPage = pageFactory.generateDayPage(game.getPossibleInGameHauptrolleNames(), game.getPossibleInGameBonusrolleNames(), freibierTag);
        buildScreenFromPage(currentPage);
    }
}
