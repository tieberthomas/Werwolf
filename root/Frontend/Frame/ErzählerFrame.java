package root.Frontend.Frame;

import root.Controller.FrontendControl;
import root.Frontend.Factories.ErzählerPageFactory;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.InteractivePages.*;
import root.Frontend.Utils.PageRefresherFramework.Models.InteractivePage;
import root.Frontend.Utils.PageRefresherFramework.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresherFramework.PageRefresher;
import root.GameController;
import root.Logic.Game;
import root.Logic.Persona.Rollen.Constants.DropdownConstants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ErzählerFrame extends MyFrame implements ActionListener {
    public boolean next = true;

    private List<InteractivePage> interactivePages = new ArrayList<>();
    private StartPage startPage;
    private PlayerSetupPage playerSetupPage;
    private MainrolePage mainrolePage;
    private BonusrolePage bonusrolePage;
    private SpecifyPage specifyPage;

    public InteractivePage currentInteractivePage;

    public ErzählerPageFactory pageFactory = new ErzählerPageFactory(this);

    public JComboBox comboBox1;
    public JComboBox comboBox2;

    public String chosenOption1 = "";
    public String chosenOption2 = "";

    public JButton nextJButton;
    public JButton umbringenJButton;
    public JButton priesterJButton;
    public JButton richterinJButton;
    public JButton respawnFramesJButton;
    public JButton henkerGoBackButton = new JButton("Zurück");

    public ErzählerFrame() {
        calcFrameSize();

        WINDOW_TITLE = "Erzähler Fenster";

        frameJpanel = generateDefaultPanel();

        setupInteractivePages();
        initializePageElements();

        startPage.showPage();

        showFrame();

        PageRefresher.erzählerFrame = this;
    }

    private void setupInteractivePages() {
        InteractivePage.pageFactory = pageFactory;
        InteractivePage.erzählerFrame = this;
        startPage = new StartPage();
        playerSetupPage = new PlayerSetupPage();
        mainrolePage = new MainrolePage();
        bonusrolePage = new BonusrolePage();
        specifyPage = new SpecifyPage();
        interactivePages.add(startPage);
        interactivePages.add(playerSetupPage);
        interactivePages.add(mainrolePage);
        interactivePages.add(bonusrolePage);
        interactivePages.add(specifyPage);
        startPage.generatePage();
    }

    private void initializePageElements() {
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
    }

    public void generateAllPageRefreshers() {
        RefreshedPage.erzählerFrame = this;
        RefreshedPage.spielerFrame = GameController.spielerFrame;

        playerSetupPage.generatePage();
        playerSetupPage.setupPageRefresher();
        mainrolePage.generatePage();
        mainrolePage.setupPageRefresher();
        bonusrolePage.generatePage();
        bonusrolePage.setupPageRefresher();
        specifyPage.generatePage();
        specifyPage.setupPageRefresher();
    }

    public void nextPage() {
        changePage(1);
    }

    public void prevPage() {
        changePage(-1);
    }

    private void changePage(int indexChange) {
        int currentIndex = getIndexOfPage(currentInteractivePage);

        InteractivePage page = interactivePages.get(currentIndex + indexChange);

        page.showPage();
    }

    private int getIndexOfPage(InteractivePage currentInteractivePage) {
        if (currentInteractivePage == null) {
            return -1;
        }

        return interactivePages.indexOf(currentInteractivePage);
    }

    public void actionPerformed(ActionEvent ae) {
        if (currentInteractivePage != null) {
            currentInteractivePage.processActionEvent(ae);
        }

        if (ae.getSource() == henkerGoBackButton) {
            next = false;
            triggerNext();
        } else if (ae.getSource() == nextJButton) {
            next = true;
            triggerNext();
        } else if (ae.getSource() == comboBox1 && ((JComboBox) ae.getSource()).hasFocus()) {
            FrontendControl.combobox1Changed(comboBox1.getSelectedItem().toString());
        } else if (ae.getSource() == comboBox2 && ((JComboBox) ae.getSource()).hasFocus()) {
            FrontendControl.combobox2Changed(comboBox2.getSelectedItem().toString());
        } else if (ae.getSource() == umbringenJButton) {
            showUmbringenPage();
        } else if (ae.getSource() == priesterJButton) {
            showPriesterPage();
        } else if (ae.getSource() == richterinJButton) {
            showRichterinPage();
        } else if (ae.getSource() == respawnFramesJButton) {
            GameController.respawnFrames();
        }
    }

    private void showUmbringenPage() {
        UmbringenPage umbringenPage = new UmbringenPage(new DropdownOptions(Game.game.getLivingSpielerStrings(), DropdownConstants.EMPTY));
        currentInteractivePage = umbringenPage;
        buildScreenFromPage(umbringenPage.page);
    }

    private void showPriesterPage() {
        PriesterPage priesterPage = new PriesterPage(new DropdownOptions(Game.game.getLivingSpielerStrings(), DropdownConstants.EMPTY));
        currentInteractivePage = priesterPage;
        buildScreenFromPage(priesterPage.page);
    }

    private void showRichterinPage() {
        RichterinPage richterinPage = new RichterinPage(new DropdownOptions(Game.game.getLivingSpielerStrings(), DropdownConstants.EMPTY));
        currentInteractivePage = richterinPage;
        buildScreenFromPage(richterinPage.page);
    }

    private void triggerNext() {
        if (comboBox1 != null) {
            chosenOption1 = (String) comboBox1.getSelectedItem();
        }

        if (comboBox2 != null) {
            chosenOption2 = (String) comboBox2.getSelectedItem();
        }

        FrontendControl.ereaseSpielerFrame();

        GameController.continueThreads();

        if (GameController.mode == GameMode.SETUP_NIGHT) {
            FrontendControl.refreshÜbersichtsFrame();
        }
    }
}
