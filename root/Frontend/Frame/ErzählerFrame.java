package root.Frontend.Frame;

import root.Frontend.Factories.ErzählerPageFactory;
import root.Frontend.FrontendControl;
import root.Frontend.Page.Page;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.Utils.PageRefresher.InteractivePages.*;
import root.Frontend.Utils.PageRefresher.Models.InteractivePage;
import root.Frontend.Utils.PageRefresher.Models.LoadMode;
import root.Frontend.Utils.PageRefresher.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Logic.Game;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.DropdownConstants;
import root.Logic.Phases.*;
import root.ResourceManagement.DataManager;
import root.Utils.ListHelper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class ErzählerFrame extends MyFrame implements ActionListener {
    public boolean next = true;

    public SpielerFrame spielerFrame;
    public ÜbersichtsFrame übersichtsFrame;

    public DataManager dataManager;

    private List<InteractivePage> interactivePages = new ArrayList<>();
    private StartPage startPage;
    private PlayerSetupPage playerSetupPage;
    private MainrolePage mainrolePage;
    private BonusrolePage bonusrolePage;
    private SpecifyPage specifyPage;

    public InteractivePage currentInteractivePage;

    public static ErzählerFrameMode mode = ErzählerFrameMode.SETUP;

    public ErzählerPageFactory pageFactory = new ErzählerPageFactory(this);

    public ArrayList<JButton> goBackButtons = new ArrayList<>();
    public ArrayList<JButton> goNextButtons = new ArrayList<>();

    public JComboBox comboBox1;
    public JComboBox comboBox2;

    public String chosenOption1 = "";
    public String chosenOption2 = "";

    public JButton nextJButton;
    public JButton goBackJButton;
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

    private void generateAllPageRefreshers() {
        RefreshedPage.erzählerFrame = this;
        RefreshedPage.spielerFrame = spielerFrame;

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

        if (goNextButtons.contains(ae.getSource())) {
            nextPage();
        } else if (goBackButtons.contains(ae.getSource())) {
            if (gameIsInDaySetupMode()) {
                mode = PhaseManager.parsePhaseMode();
                FrontendControl.showDayPage();
            } else {
                prevPage();
            }
        } else if (ae.getSource() == henkerGoBackButton) {
            next = false;
            triggerNext();
        } else if (ae.getSource() == nextJButton) {
            next = true;
            triggerNext();
        } else if (ae.getSource() == comboBox1 && ((JComboBox) ae.getSource()).hasFocus()) {
            try {
                if (spielerFrame.mode == SpielerFrameMode.dropDownText) {
                    if (spielerFrame.comboBox1Label != null && comboBox1 != null) {
                        spielerFrame.comboBox1Label.setText(comboBox1.getSelectedItem().toString());
                    }
                } else if (spielerFrame.mode == SpielerFrameMode.dropDownImage) {
                    Hauptrolle hauptrolle = Game.game.findHauptrollePerName((String) comboBox1.getSelectedItem());
                    String imagePath = hauptrolle.imagePath;
                    Page imagePage = spielerFrame.pageFactory.generateStaticImagePage(spielerFrame.title, imagePath);
                    spielerFrame.buildScreenFromPage(imagePage);
                    spielerFrame.mode = SpielerFrameMode.dropDownImage;
                } else if (spielerFrame.mode == SpielerFrameMode.freibierPage || spielerFrame.mode == SpielerFrameMode.listMirrorPage) {
                    Page dropDownPage = spielerFrame.pageFactory.generateDropdownPage(spielerFrame.title, 1);
                    spielerFrame.buildScreenFromPage(dropDownPage);
                    spielerFrame.comboBox1Label.setText(comboBox1.getSelectedItem().toString());
                    spielerFrame.mode = SpielerFrameMode.dropDownText;
                }
            } catch (NullPointerException e) {
                System.out.println("Combobox1 might not be initialized.");
            }
        } else if (ae.getSource() == comboBox2 && ((JComboBox) ae.getSource()).hasFocus()) {
            try {
                if (spielerFrame.mode == SpielerFrameMode.dropDownText) {
                    if (spielerFrame.comboBox2Label != null && comboBox2 != null) {
                        spielerFrame.comboBox2Label.setText(comboBox2.getSelectedItem().toString());
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Combobox2 might not be initialized.");
            }
        } else if (ae.getSource() == umbringenJButton) {
            showUmbringenPage();
        } else if (ae.getSource() == priesterJButton) {
            showPriesterPage();
        } else if (ae.getSource() == richterinJButton) {
            if (mode == ErzählerFrameMode.RICHTERIN_SETUP) {
                try {
                    if (comboBox1 != null) {
                        chosenOption1 = (String) comboBox1.getSelectedItem();
                    }

                    if (comboBox2 != null) {
                        chosenOption2 = (String) comboBox2.getSelectedItem();
                    }
                } catch (NullPointerException e) {
                    System.out.println("comboboxes(1,2) might not be initialized.");
                }

                String richterin = chosenOption1;
                String spieler = chosenOption2;
                Game.game.day.verurteilen(richterin, spieler);

                mode = PhaseManager.parsePhaseMode();
                FrontendControl.showDayPage();
            } else {
                mode = ErzählerFrameMode.RICHTERIN_SETUP;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateRichterinPage(Game.game.getLivingSpielerStrings()));
            }
        } else if (ae.getSource() == respawnFramesJButton) {
            respawnFrames();
        }
    }

    private void showUmbringenPage() {
        UmbringenPage umbringenPage = new UmbringenPage(new DropdownOptions(Game.game.getLivingSpielerStrings(), DropdownConstants.EMPTY), übersichtsFrame);
        currentInteractivePage = umbringenPage;
        buildScreenFromPage(umbringenPage.page);
    }

    private void showPriesterPage() {
        PriesterPage priesterPage = new PriesterPage(new DropdownOptions(Game.game.getLivingSpielerStrings(), DropdownConstants.EMPTY));
        currentInteractivePage = priesterPage;
        buildScreenFromPage(priesterPage.page);
    }

    private void triggerNext() {
        try {
            if (comboBox1 != null) {
                chosenOption1 = (String) comboBox1.getSelectedItem();
            }

            if (comboBox2 != null) {
                chosenOption2 = (String) comboBox2.getSelectedItem();
            }
        } catch (NullPointerException e) {
            System.out.println("some comboboxes (1,2,3) might not be initialized.");
        }

        spielerFrame.mode = SpielerFrameMode.blank;
        spielerFrame.buildScreenFromPage(spielerFrame.blankPage);

        continueThreads();

        try {
            if (übersichtsFrame != null) {
                if (mode == ErzählerFrameMode.SETUP_NIGHT) {
                    übersichtsFrame.refresh();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Übersichtsframe seems to be not there. (yet?)");
        }
    }

    public void setupGame(LoadMode loadMode) {
        new Game();
        dataManager = new DataManager();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        spielerFrame = new SpielerFrame(this);

        if (loadMode == LoadMode.COMPOSITION) {
            dataManager.loadLastComposition();
        } else if (loadMode == LoadMode.GAME) {
            dataManager.loadLastGame();
            Game.game.spielerSpecified = ListHelper.cloneList(Game.game.spieler);
        }

        generateAllPageRefreshers();
    }

    private boolean gameIsInDaySetupMode() {
        return mode == ErzählerFrameMode.RICHTERIN_SETUP;
    }

    private void respawnFrames() {
        spielerFrame.dispatchEvent(new WindowEvent(spielerFrame, WindowEvent.WINDOW_CLOSING));
        übersichtsFrame.dispatchEvent(new WindowEvent(übersichtsFrame, WindowEvent.WINDOW_CLOSING));

        int spielerFrameMode = spielerFrame.mode;
        Page savePage = spielerFrame.currentPage;
        spielerFrame = new SpielerFrame(this);
        spielerFrame.mode = spielerFrameMode;
        spielerFrame.buildScreenFromPage(savePage);

        übersichtsFrame = new ÜbersichtsFrame(this.frameJpanel.getHeight() + ÜbersichtsFrame.spaceFromErzählerFrame);

        FrontendControl.spielerFrame = spielerFrame;
        if (PhaseManager.phaseMode == PhaseMode.DAY || PhaseManager.phaseMode == PhaseMode.FREIBIER_DAY) {
            spielerFrame.generateDayPage();
        } else if (PhaseManager.phaseMode == PhaseMode.NORMAL_NIGHT || PhaseManager.phaseMode == PhaseMode.SETUP_NIGHT) {
            spielerFrame.buildScreenFromPage(savePage);
        }
    }

    public static void continueThreads() {
        try {
            if (mode == ErzählerFrameMode.SETUP_NIGHT) {
                synchronized (SetupNight.lock) {
                    SetupNight.lock.notify();
                }
            } else if (mode == ErzählerFrameMode.DAY || mode == ErzählerFrameMode.FREIBIER_DAY) {
                synchronized (Day.lock) {
                    Day.lock.notify();
                }
            } else if (mode == ErzählerFrameMode.NORMAL_NIGHT) {
                synchronized (NormalNight.lock) {
                    NormalNight.lock.notify();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Something went wrong with the Phases. (phasemode might be set wrong)");
        }
    }
}
