package root.Frontend.Frame;

import root.Frontend.Factories.SpielerPageElementFactory;
import root.Frontend.Factories.SpielerPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Utils.TimeUpdater;
import root.Logic.Phases.Day;
import root.Logic.Phases.PhaseManager;
import root.Logic.Phases.PhaseMode;
import root.Logic.Game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SpielerFrame extends MyFrame {
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

        refreshPlayerSetupPage();
    }

    public void generateAllPages() {
        blankPage = new Page();
        dropDownPage = pageFactory.generateDropdownPage("", 1);
    }

    public void refreshPlayerSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(Game.game.getLivingSpielerStrings()));
    }

    public void refreshHauptrolleSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(Game.game.getHauptrolleInGameNames()));
    }

    public void refreshBonusrolleSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(Game.game.getBonusrolleInGameNames()));
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
}
