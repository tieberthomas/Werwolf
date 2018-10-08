package root.Frontend.Frame;

import root.Frontend.Factories.SpielerPageElementFactory;
import root.Frontend.Factories.SpielerPageFactory;
import root.Frontend.Page.Page;
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
        erzählerFrame.timer.spielerframe = this;
        erzählerFrame.timer.startTimeUpdateThread();

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

        if (erzählerFrame.currentPage.equals(erzählerFrame.mainRoleSetupPage)) {
            refreshMainRoleSetupPage();
        }

        if (erzählerFrame.currentPage.equals(erzählerFrame.secondaryRoleSetupPage)) {
            refreshSecondaryRoleSetupPage();
        }

        if (erzählerFrame.currentPage.equals(erzählerFrame.playerSpecifiyPage)) {
            refreshSecondarySpecifySetupPage();
        }
    }

    public void refreshPlayerSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(game.getLivingPlayerStrings()));
    }

    public void refreshMainRoleSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(game.getMainRoleInGameNames()));
    }

    public void refreshSecondaryRoleSetupPage() {
        buildScreenFromPage(pageFactory.generateListPage(game.getSecondaryRoleInGameNames()));
    }

    public void refreshSecondarySpecifySetupPage() {
        ArrayList<String> mainRoles = new ArrayList<>();
        mainRoles.addAll(game.getMainRoleInGameNames());

        ArrayList<String> secondaryRoles = new ArrayList<>();
        secondaryRoles.addAll(game.getSecondaryRoleInGameNames());
        buildScreenFromPage(pageFactory.generateDoubleListPage(mainRoles, secondaryRoles, "Hauptrollen", "Bonusrollen"));
    }

    public void generateDayPage() {
        boolean freibierTag = false;
        title = Tag.dayTitle;
        if (game.phaseMode == PhaseMode.freibierTag) {
            mode = SpielerFrameMode.freibierPage;
            freibierTag = true;
        }

        currentPage = pageFactory.generateDayPage(game.getPossibleInGameMainRoleNames(), game.getPossibleInGameSecondaryRoleNames(), freibierTag);
        buildScreenFromPage(currentPage);
    }
}
