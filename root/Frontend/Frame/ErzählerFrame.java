package root.Frontend.Frame;

import root.Frontend.Factories.ErzählerPageFactory;
import root.Frontend.FrontendControl;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Frontend.Utils.PageRefresher.Models.ButtonTable;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Persona.Bonusrolle;
import root.Persona.Hauptrolle;
import root.Phases.ErsteNacht;
import root.Phases.Nacht;
import root.Phases.PhaseMode;
import root.Phases.Tag;
import root.ResourceManagement.DataManager;
import root.Spieler;
import root.mechanics.Game;
import root.mechanics.Torte;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ErzählerFrame extends MyFrame implements ActionListener {
    public static Game game;

    public SpielerFrame spielerFrame;
    public ÜbersichtsFrame übersichtsFrame;
    public Page savePage;
    public ArrayList<Page> setupPages = new ArrayList<>();

    private DataManager dataManager;

    public static Object lock;

    public ErzählerFrameMode mode = ErzählerFrameMode.setup;

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
    public JLabel secondaryRoleCounterJLabel;
    public JLabel numberOfPlayersJLabel;
    public JButton hauptrolleGoBackJButton;
    public JButton hauptrolleGoNextJButton;
    public PageTable hauptrolleButtonTable;
    public ArrayList<JButton> hauptrolleButtons = new ArrayList<>();
    public PageTable hauptrolleLabelTable;
    public PageTable deleteHauptrolleTable;
    public ArrayList<JButton> deleteHauptrolleButtons = new ArrayList<>();
    public JButton addAllHauptrollenJButton;

    public Page secondaryRoleSetupPage;
    public JButton secondaryRoleGoBackJButton;
    public JButton secondaryRoleGoNextJButton;
    public PageTable secondaryRoleButtonTable;
    public ArrayList<JButton> secondaryRoleButtons = new ArrayList<>();
    public PageTable secondaryRoleLabelTable;
    public PageTable deleteSecondaryRoleTable;
    public ArrayList<JButton> deleteSecondaryRoleButtons = new ArrayList<>();
    public JButton addAllSecondaryRolesJButton;

    public Page playerSpecifiyPage;
    public JButton playerSpecifyGoNextJButton;
    public JButton playerSpecifyGoBackJButton;
    public PageTable playerSpecifyLabelTable;
    public PageTable hauptrolleSpecifyLabelTable;
    public PageTable secondaryRoleSpecifyLabelTable;
    public PageTable deleteSpecifyPlayerTable;
    public ArrayList<JButton> deleteSpecifyPlayerButtons = new ArrayList<>();

    public String chosenOption1 = "";
    public String chosenOption2 = "";
    public String chosenOption3 = "";

    public JComboBox comboBox1 = new JComboBox();
    public JComboBox comboBox2 = new JComboBox();
    public JComboBox comboBox3 = new JComboBox();
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
    private PageRefresher secondaryRolePageRefresher;
    private PageRefresher tortenPageRefresher;
    private PageRefresher specifyPageRefresher;

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
        playerSetupPage = new Page();
        hauptrolleSetupPage = new Page();
        secondaryRoleSetupPage = new Page();
        tortenPage = new Page();
        playerSpecifiyPage = new Page();

        hauptrolleButtonTable = new PageTable();
        secondaryRoleButtonTable = new PageTable();

        playerLabelTable = new PageTable();
        hauptrolleLabelTable = new PageTable();
        secondaryRoleLabelTable = new PageTable();
        tortenPlayerLabelTable = new PageTable();
        playerSpecifyLabelTable = new PageTable();
        hauptrolleSpecifyLabelTable = new PageTable();
        secondaryRoleSpecifyLabelTable = new PageTable();

        deletePlayerTable = new PageTable();
        deleteHauptrolleTable = new PageTable();
        deleteSecondaryRoleTable = new PageTable();
        deleteTortenPlayerTable = new PageTable();
        deleteSpecifyPlayerTable = new PageTable();
    }

    private void generateAllPageRefreshers() {
        generatePlayerPageRefresher();
        generateHauptrollePageRefresher();
        generateSecondaryRolePageRefresher();
        generateTortenPageRefresher();
        generateSpecifyPageRefresher();
    }

    private void generatePlayerPageRefresher() {
        playerPageRefresher = new PageRefresher(playerSetupPage);
        playerPageRefresher.add(new LabelTable(playerLabelTable, game::getLivingPlayerStrings));
        playerPageRefresher.add(new DeleteButtonTable(deletePlayerTable, deletePlayerButtons, game.spieler::size));
    }

    private void generateHauptrollePageRefresher() {
        hauptrollePageRefresher = new PageRefresher(hauptrolleSetupPage);
        hauptrollePageRefresher.add(new ButtonTable(hauptrolleButtons));
        hauptrollePageRefresher.add(new LabelTable(hauptrolleLabelTable, game::getHauptrolleInGameNames));
        hauptrollePageRefresher.add(new DeleteButtonTable(deleteHauptrolleTable, deleteHauptrolleButtons, game.hauptrollenInGame::size));
    }

    private void generateSecondaryRolePageRefresher() {
        secondaryRolePageRefresher = new PageRefresher(secondaryRoleSetupPage);
        secondaryRolePageRefresher.add(new ButtonTable(secondaryRoleButtons));
        secondaryRolePageRefresher.add(new LabelTable(secondaryRoleLabelTable, game::getSecondaryRoleInGameNames));
        secondaryRolePageRefresher.add(new DeleteButtonTable(deleteSecondaryRoleTable, deleteSecondaryRoleButtons, game.secondaryRolesInGame::size));
    }

    private void generateTortenPageRefresher() {
        tortenPageRefresher = new PageRefresher(tortenPage);
        tortenPageRefresher.add(new LabelTable(tortenPlayerLabelTable, Torte::getTortenesserNames));
        tortenPageRefresher.add(new DeleteButtonTable(deleteTortenPlayerTable, deleteTortenPlayerButtons, Torte.tortenEsser::size));
    }

    private void generateSpecifyPageRefresher() {
        specifyPageRefresher = new PageRefresher(playerSpecifiyPage);
        specifyPageRefresher.add(new LabelTable(playerSpecifyLabelTable,
                new Supplier<ArrayList<String>>() {
                    public ArrayList<String> get() {
                        return (ArrayList<String>) game.playersSpecified.stream()
                                .map(player -> player.name)
                                .collect(Collectors.toList());
                    }
                }));
        specifyPageRefresher.add(new LabelTable(hauptrolleSpecifyLabelTable,
                new Supplier<ArrayList<String>>() {
                    public ArrayList<String> get() {
                        return (ArrayList<String>) game.playersSpecified.stream()
                                .map(player -> player.hauptrolle.name)
                                .collect(Collectors.toList());
                    }
                }));
        specifyPageRefresher.add(new LabelTable(secondaryRoleSpecifyLabelTable,
                new Supplier<ArrayList<String>>() {
                    public ArrayList<String> get() {
                        return (ArrayList<String>) game.playersSpecified.stream()
                                .map(player -> player.bonusrolle.name)
                                .collect(Collectors.toList());
                    }
                }));
        specifyPageRefresher.add(new DeleteButtonTable(deleteSpecifyPlayerTable, deleteSpecifyPlayerButtons, game.playersSpecified::size));
    }

    private void generateAllPages() {
        setupPages = new ArrayList<>();
        startPage = pageFactory.generateStartPage();

        int numberOfplayers = game.spieler.size();
        int numberOfhauptrollen = game.hauptrollenInGame.size();
        int numberOfbonusRoles = game.secondaryRolesInGame.size();
        ArrayList<String> hauptrollen = game.getHauptrolleNames();
        ArrayList<String> secondaryRoles = game.getSecondaryRoleNames();

        pageFactory.generatePlayerSetupPage(playerSetupPage, numberOfplayers);
        pageFactory.generateHauptrolleSetupPage(hauptrolleSetupPage, numberOfplayers, numberOfhauptrollen, hauptrollen);
        pageFactory.generateSecondaryRoleSetupPage(secondaryRoleSetupPage, numberOfplayers, numberOfbonusRoles, secondaryRoles);
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

    private void refreshSecondaryRolePage() {
        refreshSecondaryRoleCounterLabel();
        secondaryRolePageRefresher.refreshPage();
    }

    public void refreshNumberOfPlayersLabel() {
        numberOfPlayersJLabel.setText(pageFactory.pageElementFactory.generateNumberOfPLayersLabelTitle(game.spieler.size()));
    }

    public void refreshHauptrolleCounterLabel() {
        hauptrolleCounterJLabel.setText(pageFactory.pageElementFactory.generateCounterLabelTitle(game.spieler.size(), game.hauptrollenInGame.size()));
    }

    public void refreshSecondaryRoleCounterLabel() {
        secondaryRoleCounterJLabel.setText(pageFactory.pageElementFactory.generateCounterLabelTitle(game.spieler.size(), game.secondaryRolesInGame.size()));
    }

    private void refreshSpecifyPlayerPage() {
        refreshSpecifyComboBoxes();
        specifyPageRefresher.refreshPage();
    }

    public void refreshSpecifyComboBoxes() {
        DefaultComboBoxModel model1 = new DefaultComboBoxModel(game.getPlayersUnspecifiedStrings().toArray());
        comboBox1.setModel(model1);
        DefaultComboBoxModel model2 = new DefaultComboBoxModel(game.getHauptrollenUnspecifiedStrings().toArray());
        comboBox2.setModel(model2);
        DefaultComboBoxModel model3 = new DefaultComboBoxModel(game.getSecondaryRolesUnspecifiedStrings().toArray());
        comboBox3.setModel(model3);
    }

    public void removeSpecifiedPlayer(int index) {
        if (deleteSpecifyPlayerButtons.size() > index) {
            deleteSpecifyPlayerButtons.remove(index);
        }

        game.playersSpecified.remove(index);
    }

    private void refreshTortenPage() {
        refreshTortenComboBoxes();
        tortenPageRefresher.refreshPage();
    }

    public void refreshTortenComboBoxes() {
        ArrayList<String> nichtTortenEsser = game.getLivingPlayerStrings();
        ArrayList<String> tortenEsser = new ArrayList<>();
        for (Spieler spieler : Torte.tortenEsser) {
            tortenEsser.add(spieler.name);
        }
        nichtTortenEsser.removeAll(tortenEsser);
        DefaultComboBoxModel model1 = new DefaultComboBoxModel(nichtTortenEsser.toArray());
        comboBox1.setModel(model1);
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
            if (mode == ErzählerFrameMode.setup) {
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
        if (mode == ErzählerFrameMode.setup) {
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
        else if (page == secondaryRoleSetupPage)
            refreshSecondaryRolePage();
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
                game.playersSpecified = (ArrayList) game.spieler.clone();
            }

            generateAllPageRefreshers();
            nextPage();
        } else if (goNextButtons.contains(ae.getSource())) {
            if (ae.getSource() == secondaryRoleGoNextJButton) {
                dataManager.writeComposition();
            }

            if (ae.getSource() == playerSpecifyGoNextJButton) {
                if (allPlayersSpecified()) {
                    game.startGame(this);
                    dataManager.writeGame();
                } else {
                    specifyPlayer();
                }
            } else if (ae.getSource() == secondaryRoleGoNextJButton) {
                playerSpecifiyPage = generateSpecifyPlayerPage();
            }

            nextPage();
        } else if (goBackButtons.contains(ae.getSource())) {
            if (gameIsInDaySetupMode()) {
                mode = game.parsePhaseMode();
                showDayPage();
            } else {
                prevPage();
            }
        } else if (ae.getSource() == addPlayerButton || ae.getSource() == addPlayerTxtField && mode == ErzählerFrameMode.setup) {
            if (!addPlayerTxtField.getText().equals("") && !game.spielerExists(addPlayerTxtField.getText())) {
                addNewPlayer();
            }
        } else if (ae.getSource() == addPlayerTortenButton) {
            addTortenEsser();
        } else if (deleteTortenPlayerButtons.contains(ae.getSource())) {
            deleteTortenesser(ae);
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
        } else if (addAllSecondaryRolesJButton == ae.getSource()) {
            game.addAllSecondaryRoles();
            refreshSecondaryRolePage();
            spielerFrame.refreshSecondaryRoleSetupPage();
        } else if (secondaryRoleButtons.contains(ae.getSource())) {
            addSecondaryRole(ae);
        } else if (deleteSecondaryRoleButtons.contains(ae.getSource())) {
            deleteSecondaryRole(ae);
        } else if (deleteSpecifyPlayerButtons.contains(ae.getSource())) {
            deleteSpecifiedPlayer(ae);
        } else if (ae.getSource() == nextJButton) {
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
                    if (mode == ErzählerFrameMode.ersteNacht) {
                        übersichtsFrame.refresh();
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Übersichtsframe seems to be not there. (yet?)");
            }

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
        } else if (ae.getSource() == nachzüglerJButton || ae.getSource() == addPlayerTxtField && mode == ErzählerFrameMode.nachzüglerSetup) {
            if (mode == ErzählerFrameMode.nachzüglerSetup) {
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
                    game.secondaryRolesInGame.remove(newSpieler.bonusrolle);
                    newSpieler.bonusrolle = newSpieler.bonusrolle.getTauschErgebnis();
                    game.secondaryRolesInGame.add(newSpieler.bonusrolle);

                    addPlayerTxtField.setText("");

                    mode = game.parsePhaseMode();
                    showDayPage();

                    if (übersichtsFrame != null) {
                        übersichtsFrame.refresh();
                    }
                }
            } else {
                mode = ErzählerFrameMode.nachzüglerSetup;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateNachzüglerPage(game.getStillAvailableHauptrolleNames(), game.getStillAvailableBonusRoleNames()));
            }
        } else if (ae.getSource() == umbringenJButton) {
            if (mode == ErzählerFrameMode.umbringenSetup) {
                try {
                    if (comboBox1 != null) {
                        chosenOption1 = (String) comboBox1.getSelectedItem();
                    }
                } catch (NullPointerException e) {
                    System.out.println("combobox1 might not be initialized.");
                }
                Spieler spieler = game.findSpieler(chosenOption1);

                if (spieler != null) {
                    Tag.umbringenSpieler = spieler;
                    Tag.umbringenButton = true;
                } else {
                    showDayPage();
                }

                mode = game.parsePhaseMode();

                if (übersichtsFrame != null) {
                    übersichtsFrame.refresh();
                }

                if (spieler != null) {
                    continueThreads();
                }
            } else {
                mode = ErzählerFrameMode.umbringenSetup;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateUmbringenPage(game.getLivingPlayerStrings()));
            }
        } else if (ae.getSource() == priesterJButton) {
            if (mode == ErzählerFrameMode.priesterSetup) {
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
                game.tag.bürgen(priester, spieler);

                mode = game.parsePhaseMode();
                showDayPage();
            } else {
                mode = ErzählerFrameMode.priesterSetup;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generatePriesterPage(game.getLivingPlayerStrings()));
            }
        } else if (ae.getSource() == richterinJButton) {
            if (mode == ErzählerFrameMode.richterinSetup) {
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
                game.tag.verurteilen(richterin, spieler);

                mode = game.parsePhaseMode();
                showDayPage();
            } else {
                mode = ErzählerFrameMode.richterinSetup;

                spielerFrame.mode = SpielerFrameMode.blank;
                buildScreenFromPage(pageFactory.generateRichterinPage(game.getLivingPlayerStrings()));
            }
        } else if (ae.getSource() == respawnFramesJButton) {
            respawnFrames();
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

    private void deleteSecondaryRole(ActionEvent ae) {
        for (int i = 0; i < deleteSecondaryRoleButtons.size(); i++) {
            if (ae.getSource() == deleteSecondaryRoleButtons.get(i)) {
                deleteSecondaryRoleButtons.remove(i);
                String secondaryRoleName = game.secondaryRolesInGame.get(i).name;

                ArrayList<String> secondaryRolesSpecified = game.getHauptrollenSpecifiedStrings();

                if (secondaryRolesSpecified.contains(secondaryRoleName)) {
                    int index = secondaryRolesSpecified.indexOf(secondaryRoleName);
                    removeSpecifiedPlayer(index);
                }

                game.secondaryRolesInGame.remove(i);

                refreshSecondaryRolePage();
                spielerFrame.refreshSecondaryRoleSetupPage();
            }
        }
    }

    private void addSecondaryRole(ActionEvent ae) {
        for (int i = 0; i < secondaryRoleButtons.size(); i++) {
            if (ae.getSource() == secondaryRoleButtons.get(i)) {
                String secondaryRoleName = secondaryRoleButtons.get(i).getText();
                Bonusrolle newSecondaryRole = game.findBonusrolle(secondaryRoleName);
                game.secondaryRolesInGame.add(newSecondaryRole);

                refreshSecondaryRolePage();
                spielerFrame.refreshSecondaryRoleSetupPage();
            }
        }
    }

    private void deleteHauptrolle(ActionEvent ae) {
        for (int i = 0; i < deleteHauptrolleButtons.size(); i++) {
            if (ae.getSource() == deleteHauptrolleButtons.get(i)) {
                deleteHauptrolleButtons.remove(i);
                String hauptrolleName = game.hauptrollenInGame.get(i).name;

                ArrayList<String> hauptrollenSpecified = game.getHauptrollenSpecifiedStrings();

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
                String spielerName = game.spieler.get(i).name;

                if (game.playersSpecified.contains(spielerName)) {
                    int index = game.playersSpecified.indexOf(spielerName);
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
        return mode == ErzählerFrameMode.nachzüglerSetup || mode == ErzählerFrameMode.umbringenSetup ||
                mode == ErzählerFrameMode.priesterSetup || mode == ErzählerFrameMode.richterinSetup;
    }

    private boolean allPlayersSpecified() {
        return game.playersSpecified.size() == game.spieler.size();
    }

    private void specifyPlayer() {
        try {
            String spielerName = (String) comboBox1.getSelectedItem();
            Spieler spieler = game.findSpieler(spielerName);
            game.playersSpecified.add(spieler);

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

        übersichtsFrame = new ÜbersichtsFrame(this, game);

        FrontendControl.spielerFrame = spielerFrame;
        if (game.phaseMode == PhaseMode.tag || game.phaseMode == PhaseMode.freibierTag) {
            spielerFrame.generateDayPage();
        } else if (game.phaseMode == PhaseMode.nacht || game.phaseMode == PhaseMode.ersteNacht) {
            spielerFrame.buildScreenFromPage(savePage);
        }
    }

    public void continueThreads() {
        try {
            if (mode == ErzählerFrameMode.ersteNacht) {
                synchronized (ErsteNacht.lock) {
                    ErsteNacht.lock.notify();
                }
            } else if (mode == ErzählerFrameMode.tag || mode == ErzählerFrameMode.freibierTag || mode == ErzählerFrameMode.umbringenSetup) {
                synchronized (Tag.lock) {
                    Tag.lock.notify();
                }
            } else if (mode == ErzählerFrameMode.nacht) {
                synchronized (Nacht.lock) {
                    Nacht.lock.notify();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Somehting went wrong with the Phases. (phasemode might be set wrong)");
        }
    }

    public void showDayPage() {
        //buildScreenFromPage(pageFactory.generateDayPage(game.getLivingPlayerOrNoneStrings()));
        FrontendControl.erzählerDefaultDayPage();
        FrontendControl.spielerDayPage();
    }

    private Page generateSpecifyPlayerPage() {
        return pageFactory.generatePlayerSpecifiyPage(playerSpecifiyPage, game.getPlayersUnspecifiedStrings(), game.getHauptrollenUnspecifiedStrings(), game.getSecondaryRolesUnspecifiedStrings());
    }
}
