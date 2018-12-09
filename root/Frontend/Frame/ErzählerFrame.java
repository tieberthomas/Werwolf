package root.Frontend.Frame;

import root.Frontend.Factories.ErzählerPageFactory;
import root.Frontend.FrontendControl;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Frontend.Utils.PageRefresher.InteractivePages.*;
import root.Frontend.Utils.PageRefresher.Models.*;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Persona.Hauptrolle;
import root.Phases.*;
import root.ResourceManagement.DataManager;
import root.Spieler;
import root.Utils.ListHelper;
import root.mechanics.Game;
import root.mechanics.Torte;

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
    public Page savePage;

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

    public String chosenOption1 = "";
    public String chosenOption2 = "";

    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public JButton nextJButton;
    public JButton goBackJButton;
    public JButton umbringenJButton;
    public JButton priesterJButton;
    public JButton richterinJButton;
    public JButton respawnFramesJButton;

    public Page tortenPage;
    public JButton addPlayerTortenButton;
    public PageTable tortenPlayerLabelTable;
    public PageTable deleteTortenPlayerTable;
    private ArrayList<JButton> deleteTortenPlayerButtons = new ArrayList<>();

    private PageRefresher tortenPageRefresher;

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

        tortenPage = new Page();
        tortenPlayerLabelTable = new PageTable();
        deleteTortenPlayerTable = new PageTable();
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

        generateTortenPageRefresher();
    }

    private void generateTortenPageRefresher() {
        tortenPageRefresher = new PageRefresher(tortenPage);
        tortenPageRefresher.add(new LabelTable(tortenPlayerLabelTable, Torte::getTortenesserNames));
        tortenPageRefresher.add(new DeleteButtonTable(deleteTortenPlayerTable, deleteTortenPlayerButtons, Torte.tortenEsser::size));
        tortenPageRefresher.add(new Combobox(comboBox1, this::getNichtTortenEsserStrings));
    }

    private void refreshTortenPage() {
        tortenPageRefresher.refreshPage();
    }

    private List<String> getNichtTortenEsserStrings() {
        List<String> nichtTortenEsser = Game.game.getLivingSpielerStrings();
        List<String> tortenEsser = new ArrayList<>();
        for (Spieler spieler : Torte.tortenEsser) {
            tortenEsser.add(spieler.name);
        }
        nichtTortenEsser.removeAll(tortenEsser);
        return nichtTortenEsser;
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
                showDayPage();
            } else {
                prevPage();
            }
        } else if (ae.getSource() == addPlayerTortenButton) {
            addTortenEsser();
        } else if (ae.getSource() == henkerGoBackButton) {
            next = false;
            triggerNext();
        } else if (deleteTortenPlayerButtons.contains(ae.getSource())) {
            deleteTortenesser(ae);
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
            if (mode == ErzählerFrameMode.UMBRINGEN_SETUP) {
                try {
                    if (comboBox1 != null) {
                        chosenOption1 = (String) comboBox1.getSelectedItem();
                    }
                } catch (NullPointerException e) {
                    System.out.println("combobox1 might not be initialized.");
                }
                Spieler spieler = Game.game.findSpieler(chosenOption1);

                if (spieler != null) {
                    Day.umbringenSpieler = spieler;
                    Day.umbringenButton = true;
                } else {
                    showDayPage();
                }

                mode = PhaseManager.parsePhaseMode();

                if (übersichtsFrame != null) {
                    übersichtsFrame.refresh();
                }

                if (spieler != null) {
                    continueThreads();
                }
            } else {
                mode = ErzählerFrameMode.UMBRINGEN_SETUP;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateUmbringenPage(Game.game.getLivingSpielerStrings()));
            }
        } else if (ae.getSource() == priesterJButton) {
            if (mode == ErzählerFrameMode.PRIESTER_SETUP) {
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
                String priester = chosenOption1;
                String spieler = chosenOption2;
                Game.game.day.bürgen(priester, spieler);

                mode = PhaseManager.parsePhaseMode();
                showDayPage();
            } else {
                mode = ErzählerFrameMode.PRIESTER_SETUP;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generatePriesterPage(Game.game.getLivingSpielerStrings()));
            }
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
                showDayPage();
            } else {
                mode = ErzählerFrameMode.RICHTERIN_SETUP;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateRichterinPage(Game.game.getLivingSpielerStrings()));
            }
        } else if (ae.getSource() == respawnFramesJButton) {
            respawnFrames();
        }
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
                if (mode == ErzählerFrameMode.FIRST_NIGHT) {
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

    private void deleteTortenesser(ActionEvent ae) {
        for (int i = 0; i < deleteTortenPlayerButtons.size(); i++) {
            if (ae.getSource() == deleteTortenPlayerButtons.get(i)) {
                deleteTortenPlayerButtons.remove(i);

                Torte.tortenEsser.remove(i);

                refreshTortenPage();

                return;
            }
        }
    }

    private void addTortenEsser() {
        try {
            String spielerName = comboBox1.getSelectedItem().toString();
            Spieler spieler = Game.game.findSpieler(spielerName);
            Torte.tortenEsser.add(spieler);
            refreshTortenPage();
        } catch (NullPointerException e) {
        }
    }

    private boolean gameIsInDaySetupMode() {
        return mode == ErzählerFrameMode.UMBRINGEN_SETUP || mode == ErzählerFrameMode.PRIESTER_SETUP
                || mode == ErzählerFrameMode.RICHTERIN_SETUP;
    }

    public void respawnFrames() {
        spielerFrame.dispatchEvent(new WindowEvent(spielerFrame, WindowEvent.WINDOW_CLOSING));
        übersichtsFrame.dispatchEvent(new WindowEvent(übersichtsFrame, WindowEvent.WINDOW_CLOSING));

        int spielerFrameMode = spielerFrame.mode;
        savePage = spielerFrame.currentPage;
        spielerFrame = new SpielerFrame(this);
        spielerFrame.mode = spielerFrameMode;
        spielerFrame.buildScreenFromPage(savePage);

        übersichtsFrame = new ÜbersichtsFrame(this.frameJpanel.getHeight() + 50);

        FrontendControl.spielerFrame = spielerFrame;
        if (PhaseManager.phaseMode == PhaseMode.DAY || PhaseManager.phaseMode == PhaseMode.FREIBIER_DAY) {
            spielerFrame.generateDayPage();
        } else if (PhaseManager.phaseMode == PhaseMode.NORMAL_NIGHT || PhaseManager.phaseMode == PhaseMode.FIRST_NIGHT) {
            spielerFrame.buildScreenFromPage(savePage);
        }
    }

    public void continueThreads() {
        try {
            if (mode == ErzählerFrameMode.FIRST_NIGHT) {
                synchronized (FirstNight.lock) {
                    FirstNight.lock.notify();
                }
            } else if (mode == ErzählerFrameMode.DAY || mode == ErzählerFrameMode.FREIBIER_DAY || mode == ErzählerFrameMode.UMBRINGEN_SETUP) {
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

    public void showDayPage() {
        FrontendControl.erzählerDefaultDayPage();
        FrontendControl.spielerDayPage();
    }
}
