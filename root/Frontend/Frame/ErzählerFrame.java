package root.Frontend.Frame;

import root.Frontend.Factories.ErzählerPageFactory;
import root.Frontend.FrontendControl;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Frontend.Utils.PageRefresher.Models.ButtonTable;
import root.Frontend.Utils.PageRefresher.Models.Combobox;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Persona.Bonusrolle;
import root.Persona.Hauptrolle;
import root.Phases.Day;
import root.Phases.FirstNight;
import root.Phases.NormalNight;
import root.Phases.PhaseManager;
import root.Phases.PhaseMode;
import root.ResourceManagement.DataManager;
import root.Spieler;
import root.mechanics.Game;
import root.mechanics.Torte;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ErzählerFrame extends MyFrame implements ActionListener {
    public boolean next = true;

    public static Game game;

    public SpielerFrame spielerFrame;
    public ÜbersichtsFrame übersichtsFrame;
    public Page savePage;
    public ArrayList<Page> setupPages = new ArrayList<>();

    private DataManager dataManager;

    public static Object lock;

    public static ErzählerFrameMode mode = ErzählerFrameMode.SETUP;

    public ErzählerPageFactory pageFactory = new ErzählerPageFactory(this);

    public ArrayList<JButton> goBackButtons = new ArrayList<>();
    public ArrayList<JButton> goNextButtons = new ArrayList<>();
    public ArrayList<JButton> startSetupButtons = new ArrayList<>();

    public Page startPage;
    public JButton startJButton;
    public JButton loadLastCompositionJButton;
    public JButton loadLastGameJButton;

    public Page playerSetupPage;
    public JLabel hauptrolleCounterJLabel;
    public PageTable playerLabelTable;
    public PageTable deletePlayerTable;
    public ArrayList<JButton> deletePlayerButtons = new ArrayList<>();
    public JButton playerGoBackJButton;
    public JButton playerGoNextJButton;
    public JTextField addPlayerTxtField;
    public JButton addPlayerButton;

    public Page hauptrolleSetupPage;
    public JLabel bonusrolleCounterJLabel;
    public JLabel numberOfPlayersJLabel;
    public JButton hauptrolleGoBackJButton;
    public JButton hauptrolleGoNextJButton;
    public PageTable hauptrolleButtonTable;
    public ArrayList<JButton> hauptrolleButtons = new ArrayList<>();
    public PageTable hauptrolleLabelTable;
    public PageTable deleteHauptrolleTable;
    public ArrayList<JButton> deleteHauptrolleButtons = new ArrayList<>();
    public JButton addAllHauptrollenJButton;

    public Page bonusrolleSetupPage;
    public JButton bonusrolleGoBackJButton;
    public JButton bonusrolleGoNextJButton;
    public PageTable bonusrolleButtonTable;
    public ArrayList<JButton> bonusrolleButtons = new ArrayList<>();
    public PageTable bonusrolleLabelTable;
    public PageTable deleteBonusrolleTable;
    public ArrayList<JButton> deleteBonusrolleButtons = new ArrayList<>();
    public JButton addAllBonusrollenJButton;

    public Page playerSpecifiyPage;
    public JButton playerSpecifyGoNextJButton;
    public JButton playerSpecifyGoBackJButton;
    public PageTable playerSpecifyLabelTable;
    public PageTable hauptrolleSpecifyLabelTable;
    public PageTable bonusrolleSpecifyLabelTable;
    public PageTable deleteSpecifyPlayerTable;
    public ArrayList<JButton> deleteSpecifyPlayerButtons = new ArrayList<>();

    public String chosenOption1 = "";
    public String chosenOption2 = "";
    public String chosenOption3 = "";

    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public JComboBox comboBox3;
    public JButton nextJButton;
    public JButton goBackJButton;
    public JButton nachzüglerJButton;
    public JButton umbringenJButton;
    public JButton priesterJButton;
    public JButton richterinJButton;
    public JButton respawnFramesJButton;

    public Page tortenPage;
    public JButton addPlayerTortenButton;
    public PageTable tortenPlayerLabelTable;
    public PageTable deleteTortenPlayerTable;
    public ArrayList<JButton> deleteTortenPlayerButtons = new ArrayList<>();

    private PageRefresher playerPageRefresher;
    private PageRefresher hauptrollePageRefresher;
    private PageRefresher bonusrollePageRefresher;
    private PageRefresher tortenPageRefresher;
    private PageRefresher specifyPageRefresher;
    private PageRefresher irrlichtPageRefresher;

    public Page irrlichtPage;
    public PageTable deleteIrrlichterTable;
    public PageTable irrlichterLableTable;
    public ArrayList<String> flackerndeIrrlichter; //TODO mit strings oder spielern arbeiten?
    public ArrayList<JButton> deleteIrrlichterButtons = new ArrayList<>();
    public JButton addIrrlichtButton = new JButton();

    public JButton henkerGoBackButton = new JButton("Zurück");

    public ErzählerFrame() {
        calcFrameSize();

        WINDOW_TITLE = "Erzähler Fenster";

        frameJpanel = generateDefaultPanel();

        startPage = pageFactory.generateStartPage();

        currentPage = startPage;

        buildScreenFromPage(startPage);

        showFrame();

        initializePages();

        PageRefresher.erzählerFrame = this;
    }

    private void initializePages() {
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
        comboBox3 = new JComboBox();

        playerSetupPage = new Page();
        hauptrolleSetupPage = new Page();
        bonusrolleSetupPage = new Page();
        tortenPage = new Page();
        playerSpecifiyPage = new Page();
        irrlichtPage = new Page();

        hauptrolleButtonTable = new PageTable();
        bonusrolleButtonTable = new PageTable();

        playerLabelTable = new PageTable();
        hauptrolleLabelTable = new PageTable();
        bonusrolleLabelTable = new PageTable();
        tortenPlayerLabelTable = new PageTable();
        playerSpecifyLabelTable = new PageTable();
        hauptrolleSpecifyLabelTable = new PageTable();
        bonusrolleSpecifyLabelTable = new PageTable();
        irrlichterLableTable = new PageTable();

        deletePlayerTable = new PageTable();
        deleteHauptrolleTable = new PageTable();
        deleteBonusrolleTable = new PageTable();
        deleteTortenPlayerTable = new PageTable();
        deleteSpecifyPlayerTable = new PageTable();
        deleteIrrlichterTable = new PageTable();

        flackerndeIrrlichter = new ArrayList<>();
    }

    private void generateAllPageRefreshers() {
        generatePlayerPageRefresher();
        generateHauptrollePageRefresher();
        generateBonusrollePageRefresher();
        generateTortenPageRefresher();
        generateSpecifyPageRefresher();
        generateIrrlichtPageRefresher();
    }

    private void generatePlayerPageRefresher() {
        playerPageRefresher = new PageRefresher(playerSetupPage);
        playerPageRefresher.add(new LabelTable(playerLabelTable, game::getLivingSpielerStrings));
        playerPageRefresher.add(new DeleteButtonTable(deletePlayerTable, deletePlayerButtons, game.spieler::size));
    }

    private void generateHauptrollePageRefresher() {
        hauptrollePageRefresher = new PageRefresher(hauptrolleSetupPage);
        hauptrollePageRefresher.add(new ButtonTable(hauptrolleButtons));
        hauptrollePageRefresher.add(new LabelTable(hauptrolleLabelTable, game::getHauptrolleInGameNames));
        hauptrollePageRefresher.add(new DeleteButtonTable(deleteHauptrolleTable, deleteHauptrolleButtons, game.hauptrollenInGame::size));
    }

    private void generateBonusrollePageRefresher() {
        bonusrollePageRefresher = new PageRefresher(bonusrolleSetupPage);
        bonusrollePageRefresher.add(new ButtonTable(bonusrolleButtons));
        bonusrollePageRefresher.add(new LabelTable(bonusrolleLabelTable, game::getBonusrolleInGameNames));
        bonusrollePageRefresher.add(new DeleteButtonTable(deleteBonusrolleTable, deleteBonusrolleButtons, game.bonusrollenInGame::size));
    }

    private void generateTortenPageRefresher() {
        tortenPageRefresher = new PageRefresher(tortenPage);
        tortenPageRefresher.add(new LabelTable(tortenPlayerLabelTable, Torte::getTortenesserNames));
        tortenPageRefresher.add(new DeleteButtonTable(deleteTortenPlayerTable, deleteTortenPlayerButtons, Torte.tortenEsser::size));
        tortenPageRefresher.add(new Combobox(comboBox1, this::getNichtTortenEsserStrings));
    }

    private void generateSpecifyPageRefresher() {
        specifyPageRefresher = new PageRefresher(playerSpecifiyPage);
        specifyPageRefresher.add(new LabelTable(playerSpecifyLabelTable,
                new Supplier<List<String>>() {
                    public List<String> get() {
                        return game.spielerSpecified.stream()
                                .map(player -> player.name)
                                .collect(Collectors.toList());
                    }
                }));
        specifyPageRefresher.add(new LabelTable(hauptrolleSpecifyLabelTable,
                new Supplier<List<String>>() {
                    public List<String> get() {
                        return game.spielerSpecified.stream()
                                .map(player -> player.hauptrolle.name)
                                .collect(Collectors.toList());
                    }
                }));
        specifyPageRefresher.add(new LabelTable(bonusrolleSpecifyLabelTable,
                new Supplier<List<String>>() {
                    public List<String> get() {
                        return game.spielerSpecified.stream()
                                .map(player -> player.bonusrolle.name)
                                .collect(Collectors.toList());
                    }
                }));
        specifyPageRefresher.add(new DeleteButtonTable(deleteSpecifyPlayerTable, deleteSpecifyPlayerButtons, game.spielerSpecified::size));
        specifyPageRefresher.add(new Combobox(comboBox1, game::getSpielerUnspecifiedStrings));
        specifyPageRefresher.add(new Combobox(comboBox2, game::getHauptrollenUnspecifiedStrings));
        specifyPageRefresher.add(new Combobox(comboBox3, game::getBonusrollenUnspecifiedStrings));
    }

    private void generateIrrlichtPageRefresher() {
        irrlichtPageRefresher = new PageRefresher(irrlichtPage);
        irrlichtPageRefresher.add(new LabelTable(irrlichterLableTable, this::getFlackerndeIrrlichter));
        irrlichtPageRefresher.add(new DeleteButtonTable(deleteIrrlichterTable, deleteIrrlichterButtons, this.flackerndeIrrlichter::size));
        irrlichtPageRefresher.add(new Combobox(comboBox1, this::getNichtFlackerndeIrrlichterStrings));
    }

    private void generateAllPages() {
        setupPages = new ArrayList<>();
        startPage = pageFactory.generateStartPage();

        int numberOfplayers = game.spieler.size();
        int numberOfhauptrollen = game.hauptrollenInGame.size();
        int numberOfbonusrollen = game.bonusrollenInGame.size();
        List<String> hauptrollen = game.getHauptrolleNames();
        List<String> bonusrollen = game.getBonusrolleNames();

        pageFactory.generatePlayerSetupPage(playerSetupPage, numberOfplayers);
        pageFactory.generateHauptrolleSetupPage(hauptrolleSetupPage, numberOfplayers, numberOfhauptrollen, hauptrollen);
        pageFactory.generateBonusrolleSetupPage(bonusrolleSetupPage, numberOfplayers, numberOfbonusrollen, bonusrollen);
        playerSpecifiyPage = generateSpecifyPlayerPage();
    }

    private void refreshPlayerPage() {
        refreshNumberOfPlayersLabel();
        playerPageRefresher.refreshPage();
        addPlayerTxtField.requestFocusInWindow();
    }

    private void refreshHauptrollePage() {
        refreshHauptrolleCounterLabel();
        hauptrollePageRefresher.refreshPage();
    }

    private void refreshBonusrollePage() {
        refreshBonusrolleCounterLabel();
        bonusrollePageRefresher.refreshPage();
    }

    public void refreshNumberOfPlayersLabel() {
        numberOfPlayersJLabel.setText(pageFactory.pageElementFactory.generateNumberOfPLayersLabelTitle(game.spieler.size()));
    }

    public void refreshHauptrolleCounterLabel() {
        hauptrolleCounterJLabel.setText(pageFactory.pageElementFactory.generateCounterLabelTitle(game.spieler.size(), game.hauptrollenInGame.size()));
    }

    public void refreshBonusrolleCounterLabel() {
        bonusrolleCounterJLabel.setText(pageFactory.pageElementFactory.generateCounterLabelTitle(game.spieler.size(), game.bonusrollenInGame.size()));
    }

    private void refreshSpecifyPlayerPage() {
        specifyPageRefresher.refreshPage();
    }

    public void removeSpecifiedPlayer(int index) {
        if (deleteSpecifyPlayerButtons.size() > index) {
            deleteSpecifyPlayerButtons.remove(index);
        }

        game.spielerSpecified.remove(index);
    }

    private void refreshTortenPage() {
        tortenPageRefresher.refreshPage();
    }

    private List<String> getNichtTortenEsserStrings() {
        List<String> nichtTortenEsser = game.getLivingSpielerStrings();
        List<String> tortenEsser = new ArrayList<>();
        for (Spieler spieler : Torte.tortenEsser) {
            tortenEsser.add(spieler.name);
        }
        nichtTortenEsser.removeAll(tortenEsser);
        return nichtTortenEsser;
    }

    private void refreshIrrlichtPage() {
        irrlichtPageRefresher.refreshPage();
    }

    private List<String> getNichtFlackerndeIrrlichterStrings() {
        List<String> nichtFlackernde = game.getIrrlichterStrings();
        List<String> flackernde = flackerndeIrrlichter;
        nichtFlackernde.removeAll(flackernde);
        return nichtFlackernde;
    }

    public void nextPage() {
        if (currentPage != null) {
            if (currentPage != playerSpecifiyPage && setupPages.contains(currentPage)) {
                int index = setupPages.indexOf(currentPage);
                generateAllPages();

                currentPage = setupPages.get(index + 1);
            }
            buildScreenFromPage(currentPage);
            refreshPage(currentPage);
            if (mode == ErzählerFrameMode.SETUP) {
                spielerFrame.refreshSetupPage();
            }
        }
    }

    public void prevPage() {
        int index = setupPages.indexOf(currentPage);
        generateAllPages();
        currentPage = setupPages.get(index - 1);
        buildScreenFromPage(currentPage);
        refreshPage(currentPage);
        if (mode == ErzählerFrameMode.SETUP) {
            spielerFrame.refreshSetupPage();
        }
        if (currentPage.equals(startPage)) {
            spielerFrame.dispatchEvent(new WindowEvent(spielerFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    public void refreshPage(Page page) {
        if (page == playerSetupPage)
            refreshPlayerPage();
        else if (page == hauptrolleSetupPage)
            refreshHauptrollePage();
        else if (page == bonusrolleSetupPage)
            refreshBonusrollePage();
        else if (page == playerSpecifiyPage)
            refreshSpecifyPlayerPage();
    }

    public void actionPerformed(ActionEvent ae) {
        if (startSetupButtons.contains(ae.getSource())) {
            game = new Game();
            dataManager = new DataManager(game);
            this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            spielerFrame = new SpielerFrame(this, game);

            if (ae.getSource() == loadLastCompositionJButton) {
                dataManager.loadLastComposition();
            } else if (ae.getSource() == loadLastGameJButton) {
                dataManager.loadLastGame();
                game.spielerSpecified = (ArrayList) game.spieler.clone();
            }

            generateAllPageRefreshers();
            nextPage();
        } else if (goNextButtons.contains(ae.getSource())) {
            if (ae.getSource() == bonusrolleGoNextJButton) {
                dataManager.writeComposition();
            }

            if (ae.getSource() == playerSpecifyGoNextJButton) {
                if (allPlayersSpecified()) {
                    game.startGame(this);
                    dataManager.writeGame();
                } else {
                    specifyPlayer();
                }
            } else if (ae.getSource() == bonusrolleGoNextJButton) {
                playerSpecifiyPage = generateSpecifyPlayerPage();
            }

            nextPage();
        } else if (goBackButtons.contains(ae.getSource())) {
            if (gameIsInDaySetupMode()) {
                mode = PhaseManager.parsePhaseMode();
                showDayPage();
            } else {
                prevPage();
            }
        } else if (ae.getSource() == addPlayerButton || ae.getSource() == addPlayerTxtField && mode == ErzählerFrameMode.SETUP) {
            if (!addPlayerTxtField.getText().equals("") && !game.spielerExists(addPlayerTxtField.getText())) {
                addNewPlayer();
            }
        } else if (ae.getSource() == addIrrlichtButton) {
            addIrrlicht();
        } else if (ae.getSource() == addPlayerTortenButton) {
            addTortenEsser();
        } else if (ae.getSource() == henkerGoBackButton) {
            next = false;
            triggerNext();
        } else if (deleteTortenPlayerButtons.contains(ae.getSource())) {
            deleteTortenesser(ae);
        } else if (deleteIrrlichterButtons.contains(ae.getSource())) {
            deleteFlackerndesIrrlicht(ae);
        } else if (deletePlayerButtons.contains(ae.getSource())) {
            deletePlayer(ae);
        } else if (addAllHauptrollenJButton == ae.getSource()) {
            game.addAllHauptrollenToGame();
            refreshHauptrollePage();
            spielerFrame.refreshHauptrolleSetupPage();
        } else if (hauptrolleButtons.contains(ae.getSource())) {
            addHauptrolle(ae);
        } else if (deleteHauptrolleButtons.contains(ae.getSource())) {
            deleteHauptrolle(ae);
        } else if (addAllBonusrollenJButton == ae.getSource()) {
            game.addAllBonusrollen();
            refreshBonusrollePage();
            spielerFrame.refreshBonusrolleSetupPage();
        } else if (bonusrolleButtons.contains(ae.getSource())) {
            addBonusrolle(ae);
        } else if (deleteBonusrolleButtons.contains(ae.getSource())) {
            deleteBonusrolle(ae);
        } else if (deleteSpecifyPlayerButtons.contains(ae.getSource())) {
            deleteSpecifiedPlayer(ae);
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
                    Hauptrolle hauptrolle = game.findHauptrolle((String) comboBox1.getSelectedItem());
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
        } else if (ae.getSource() == comboBox3 && ((JComboBox) ae.getSource()).hasFocus()) {
            try {
                if (spielerFrame.mode == SpielerFrameMode.dropDownText) {
                    if (spielerFrame.comboBox3Label != null && comboBox3 != null) {
                        spielerFrame.comboBox3Label.setText(comboBox3.getSelectedItem().toString());
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Combobox3 might not be initialized.");
            }
        } else if (ae.getSource() == nachzüglerJButton || ae.getSource() == addPlayerTxtField && mode == ErzählerFrameMode.NACHZÜGLER_SETUP) {
            if (mode == ErzählerFrameMode.NACHZÜGLER_SETUP) {
                if (!addPlayerTxtField.getText().equals("") && !game.spielerExists(addPlayerTxtField.getText())) {
                    try {
                        if (comboBox1 != null) {
                            chosenOption1 = (String) comboBox1.getSelectedItem();
                        }

                        if (comboBox2 != null) {
                            chosenOption2 = (String) comboBox2.getSelectedItem();
                        }
                    } catch (NullPointerException e) {
                        System.out.println("some comboboxes (1,2) might not be initialized.");
                    }

                    String name = addPlayerTxtField.getText();
                    String hauptrolle = chosenOption1;
                    String bonusrolle = chosenOption2;

                    Spieler newSpieler = new Spieler(name, hauptrolle, bonusrolle);
                    game.bonusrollenInGame.remove(newSpieler.bonusrolle);
                    newSpieler.bonusrolle = newSpieler.bonusrolle.getTauschErgebnis();
                    game.bonusrollenInGame.add(newSpieler.bonusrolle);

                    addPlayerTxtField.setText("");

                    mode = PhaseManager.parsePhaseMode();
                    showDayPage();

                    if (übersichtsFrame != null) {
                        übersichtsFrame.refresh();
                    }
                }
            } else {
                mode = ErzählerFrameMode.NACHZÜGLER_SETUP;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateNachzüglerPage(game.getStillAvailableHauptrolleNames(), game.getStillAvailableBonusrollenNames()));
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
                Spieler spieler = game.findSpieler(chosenOption1);

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
                buildScreenFromPage(pageFactory.generateUmbringenPage(game.getLivingSpielerStrings()));
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
                game.day.bürgen(priester, spieler);

                mode = PhaseManager.parsePhaseMode();
                showDayPage();
            } else {
                mode = ErzählerFrameMode.PRIESTER_SETUP;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generatePriesterPage(game.getLivingSpielerStrings()));
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
                game.day.verurteilen(richterin, spieler);

                mode = PhaseManager.parsePhaseMode();
                showDayPage();
            } else {
                mode = ErzählerFrameMode.RICHTERIN_SETUP;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateRichterinPage(game.getLivingSpielerStrings()));
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

            if (comboBox3 != null) {
                chosenOption3 = (String) comboBox3.getSelectedItem();
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

    private void addIrrlicht() {
        try {
            String spielerName = comboBox1.getSelectedItem().toString();
            flackerndeIrrlichter.add(spielerName);
            refreshIrrlichtPage();
        } catch (NullPointerException e) {
        }
    }

    private void deleteSpecifiedPlayer(ActionEvent ae) {
        for (int i = 0; i < deleteSpecifyPlayerButtons.size(); i++) {
            if (ae.getSource() == deleteSpecifyPlayerButtons.get(i)) {
                removeSpecifiedPlayer(i);

                refreshSpecifyPlayerPage();
            }
        }
    }

    private void deleteFlackerndesIrrlicht(ActionEvent ae) {
        for (int i = 0; i < deleteIrrlichterButtons.size(); i++) {
            if (ae.getSource() == deleteIrrlichterButtons.get(i)) {
                removeFlackerndesIrrlicht(i);

                refreshIrrlichtPage();
            }
        }
    }

    private void removeFlackerndesIrrlicht(int index) {
        if (deleteIrrlichterButtons.size() > index) {
            deleteIrrlichterButtons.remove(index);
        }

        flackerndeIrrlichter.remove(index);
    }

    private void deleteBonusrolle(ActionEvent ae) {
        for (int i = 0; i < deleteBonusrolleButtons.size(); i++) {
            if (ae.getSource() == deleteBonusrolleButtons.get(i)) {
                deleteBonusrolleButtons.remove(i);
                String bonusrolleName = game.bonusrollenInGame.get(i).name;

                List<String> bonusrollenSpecified = game.getHauptrollenSpecifiedStrings();

                if (bonusrollenSpecified.contains(bonusrolleName)) {
                    int index = bonusrollenSpecified.indexOf(bonusrolleName);
                    removeSpecifiedPlayer(index);
                }

                game.bonusrollenInGame.remove(i);

                refreshBonusrollePage();
                spielerFrame.refreshBonusrolleSetupPage();
            }
        }
    }

    private void addBonusrolle(ActionEvent ae) {
        for (int i = 0; i < bonusrolleButtons.size(); i++) {
            if (ae.getSource() == bonusrolleButtons.get(i)) {
                String bonusrolleName = bonusrolleButtons.get(i).getText();
                Bonusrolle newBonusrolle = game.findBonusrolle(bonusrolleName);
                game.bonusrollenInGame.add(newBonusrolle);

                refreshBonusrollePage();
                spielerFrame.refreshBonusrolleSetupPage();
            }
        }
    }

    private void deleteHauptrolle(ActionEvent ae) {
        for (int i = 0; i < deleteHauptrolleButtons.size(); i++) {
            if (ae.getSource() == deleteHauptrolleButtons.get(i)) {
                deleteHauptrolleButtons.remove(i);
                String hauptrolleName = game.hauptrollenInGame.get(i).name;

                List<String> hauptrollenSpecified = game.getHauptrollenSpecifiedStrings();

                if (hauptrollenSpecified.contains(hauptrolleName)) {
                    int index = hauptrollenSpecified.indexOf(hauptrolleName);
                    removeSpecifiedPlayer(index);
                }

                game.hauptrollenInGame.remove(i);

                refreshHauptrollePage();
                spielerFrame.refreshHauptrolleSetupPage();
            }
        }
    }

    private void addHauptrolle(ActionEvent ae) {
        for (int i = 0; i < hauptrolleButtons.size(); i++) {
            if (ae.getSource() == hauptrolleButtons.get(i)) {
                String hauptrolleName = hauptrolleButtons.get(i).getText();
                Hauptrolle newHauptrolle = game.findHauptrolle(hauptrolleName);
                game.hauptrollenInGame.add(newHauptrolle);

                refreshHauptrollePage();
                spielerFrame.refreshHauptrolleSetupPage();
            }
        }
    }

    private void deletePlayer(ActionEvent ae) {
        for (int i = 0; i < deletePlayerButtons.size(); i++) {
            if (ae.getSource() == deletePlayerButtons.get(i)) {
                deletePlayerButtons.remove(i);
                Spieler spieler = game.spieler.get(i);

                if (game.spielerSpecified.contains(spieler)) {
                    int index = game.spielerSpecified.indexOf(spieler);
                    removeSpecifiedPlayer(index);
                }

                game.spieler.remove(i);

                refreshPlayerPage();
                spielerFrame.refreshPlayerSetupPage();
            }
        }
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
            Spieler spieler = game.findSpieler(spielerName);
            Torte.tortenEsser.add(spieler);
            refreshTortenPage();
        } catch (NullPointerException e) {
        }
    }

    private void addNewPlayer() {
        String newPlayerName = addPlayerTxtField.getText();
        Spieler newPlayer = new Spieler(newPlayerName);
        game.spieler.add(newPlayer);
        addPlayerTxtField.setText("");

        refreshPlayerPage();
        spielerFrame.refreshPlayerSetupPage();
    }

    private boolean gameIsInDaySetupMode() {
        return mode == ErzählerFrameMode.NACHZÜGLER_SETUP || mode == ErzählerFrameMode.UMBRINGEN_SETUP ||
                mode == ErzählerFrameMode.PRIESTER_SETUP || mode == ErzählerFrameMode.RICHTERIN_SETUP;
    }

    private boolean allPlayersSpecified() {
        return game.spielerSpecified.size() == game.spieler.size();
    }

    private void specifyPlayer() {
        try {
            String spielerName = (String) comboBox1.getSelectedItem();
            Spieler spieler = game.findSpieler(spielerName);
            game.spielerSpecified.add(spieler);

            String hauptrolle = (String) comboBox2.getSelectedItem();
            spieler.hauptrolle = game.findHauptrolle(hauptrolle);
            if (spieler.hauptrolle == null) {
                spieler.hauptrolle = Hauptrolle.DEFAULT_HAUPTROLLE;
            }

            String bonusrolle = (String) comboBox3.getSelectedItem();
            spieler.bonusrolle = game.findBonusrolle(bonusrolle);
            if (spieler.bonusrolle == null) {
                spieler.bonusrolle = Bonusrolle.DEFAULT_BONUSROLLE;
            }

            refreshSpecifyPlayerPage();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No player in game.");
        }
    }

    public void respawnFrames() {
        spielerFrame.dispatchEvent(new WindowEvent(spielerFrame, WindowEvent.WINDOW_CLOSING));
        übersichtsFrame.dispatchEvent(new WindowEvent(übersichtsFrame, WindowEvent.WINDOW_CLOSING));

        int spielerFrameMode = spielerFrame.mode;
        savePage = spielerFrame.currentPage;
        spielerFrame = new SpielerFrame(this, game);
        spielerFrame.mode = spielerFrameMode;
        spielerFrame.buildScreenFromPage(savePage);

        übersichtsFrame = new ÜbersichtsFrame(this.frameJpanel.getHeight()+50, game);

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

    private Page generateSpecifyPlayerPage() {
        return pageFactory.generatePlayerSpecifiyPage(playerSpecifiyPage, game.getSpielerUnspecifiedStrings(), game.getHauptrollenUnspecifiedStrings(), game.getBonusrollenUnspecifiedStrings());
    }

    public ArrayList<String> getFlackerndeIrrlichter() {
        return flackerndeIrrlichter;
    }
}
