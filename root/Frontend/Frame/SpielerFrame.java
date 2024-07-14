package root.Frontend.Frame;

import root.Frontend.Factories.SpielerListPageFactory;
import root.Frontend.Factories.SpielerPageElementFactory;
import root.Frontend.Factories.SpielerPageFactory;
import root.Frontend.Factories.SpielerSchnüfflerPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Utils.TimeController;
import root.Logic.Game;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Day;
import root.Logic.Phases.PhaseManager;

import javax.swing.*;
import java.util.List;

public class SpielerFrame extends MyFrame {
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

        SpielerPageFactory.spielerFrame = this;
        SpielerListPageFactory.spielerFrame = this;
        SpielerSchnüfflerPageFactory.spielerFrame = this;
        SpielerPageElementFactory.spielerFrame = this;

        comboBox1Label = new JLabel("");
        comboBox2Label = new JLabel("");
        comboBox3Label = new JLabel("");

        clockLabel = new JLabel();
        TimeController.spielerframe = this;
        TimeController.startTimeUpdateThread();

        frameJpanel = generateDefaultPanel();

        generateAllPages();

        this.setLocation(erzählerFrame.frameJpanel.getWidth() + 20, 0);

        showFrame();
    }

    private void generateAllPages() {
        blankPage = new Page();
        dropDownPage = SpielerPageFactory.generateDropdownPage("", 1);
    }

    public void refreshPlayerSetupPage(List<String> playersInGame) {
        buildScreenFromPage(SpielerListPageFactory.generateListPage("Spieler", playersInGame));
    }

    public void refreshHauptrolleSetupPage(List<String> mainrolesInGame) {
        buildScreenFromPage(SpielerListPageFactory.generateListPage("Hauptrollen", mainrolesInGame));
    }

    public void refreshBonusrolleSetupPage(List<String> bonusrolesInGame) {
        buildScreenFromPage(SpielerListPageFactory.generateListPage("Bonusrollen", bonusrolesInGame));
    }

    public void refreshSpecifySetupPage() {
        buildScreenFromPage(SpielerListPageFactory.generateDoubleListPage(Game.game.getHauptrollenInGameSortedByFraktion(), Game.game.getBonusrolleInGameNames(), "Hauptrollen", "Bonusrollen"));
    }

    public void generateDayPage() {
        title = Day.dayTitle;
        currentPage = SpielerPageFactory.generateDayPage(Game.game.getHauptrollenInGameSortedByFraktion(), Game.game.getPossibleInGameBonusrolleNames(), PhaseManager.getDaysUntilFullMoon());
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
            Page imagePage = SpielerPageFactory.generateStaticImagePage(title, imagePath);
            buildScreenFromPage(imagePage);
            mode = SpielerFrameMode.dropDownImage;
        } else if (mode == SpielerFrameMode.listMirrorPage) {
            Page dropDownPage = SpielerPageFactory.generateDropdownPage(title, 1);
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
