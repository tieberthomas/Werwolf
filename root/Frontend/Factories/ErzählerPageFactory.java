package root.Frontend.Factories;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Phases.NightBuilding.Statement;
import root.Phases.Day;
import root.ResourceManagement.ImagePath;

import javax.swing.*;
import java.util.ArrayList;

public class ErzählerPageFactory {
    private ErzählerFrame erzählerFrame;
    public ErzählerPageElementFactory pageElementFactory;
    private static int continueToGeneratePagePoint = 0;
    private static PageElement continueToGenerateElement = null;

    public ErzählerPageFactory(ErzählerFrame frame) {
        erzählerFrame = frame;
        pageElementFactory = new ErzählerPageElementFactory(erzählerFrame);
    }

    public Page generateStartPage() {
        PageElement werwolfIcon = pageElementFactory.generateCenteredImageLabel(ImagePath.WÖLFE_ICON, 3, 60);

        erzählerFrame.startJButton = new JButton("Neues Spiel");
        PageElement startButton = pageElementFactory.generateCenteredBigButton(erzählerFrame.startJButton, werwolfIcon);
        erzählerFrame.startSetupButtons.add(erzählerFrame.startJButton);

        erzählerFrame.loadLastCompositionJButton = new JButton("Letzte Zusammenstellung laden");
        PageElement loadLastComposition = pageElementFactory.generateCenteredBigButton(erzählerFrame.loadLastCompositionJButton, startButton);
        erzählerFrame.startSetupButtons.add(erzählerFrame.loadLastCompositionJButton);

        erzählerFrame.loadLastGameJButton = new JButton("Letztes Spiel laden");
        PageElement loadLastGame = pageElementFactory.generateCenteredBigButton(erzählerFrame.loadLastGameJButton, loadLastComposition);
        erzählerFrame.startSetupButtons.add(erzählerFrame.loadLastGameJButton);


        Page startPage = new Page(0, 0);

        startPage.add(werwolfIcon);
        startPage.add(startButton);
        startPage.add(loadLastComposition);
        startPage.add(loadLastGame);

        erzählerFrame.setupPages.add(startPage);

        return startPage;
    }

    public Page generatePlayerSetupPage(Page playerSetupPage, int numberOfPlayers) {
        PageElement nameLabel = pageElementFactory.generateLabel(null, "Name");

        PageElement addPlayerTxtField = pageElementFactory.generateAddPlayerTxtField(nameLabel);

        erzählerFrame.addPlayerButton = new JButton("Hinzufügen");
        PageElement addPlayerButton = pageElementFactory.generateSmallButton(erzählerFrame.addPlayerButton, addPlayerTxtField);

        erzählerFrame.numberOfPlayersJLabel = new JLabel(pageElementFactory.generateNumberOfPLayersLabelTitle(numberOfPlayers));
        PageElement numberOfPlayersLabel = pageElementFactory.generateCounterLabel(erzählerFrame.numberOfPlayersJLabel, addPlayerTxtField);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 2;

        erzählerFrame.deletePlayerTable = pageElementFactory.generatePageTable(erzählerFrame.deletePlayerTable, numberOfPlayersLabel,
                columns, deleteButtonWidth, tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        erzählerFrame.playerLabelTable = pageElementFactory.generatePageTable(erzählerFrame.playerLabelTable, numberOfPlayersLabel,
                columns, nameLabelWidth, tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        erzählerFrame.playerGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.playerGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.playerGoBackJButton);

        erzählerFrame.playerGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.playerGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.playerGoNextJButton);

        playerSetupPage.clearPage();

        playerSetupPage.add(nameLabel);
        playerSetupPage.add(addPlayerButton);
        playerSetupPage.add(addPlayerTxtField);
        playerSetupPage.add(goBackButton);
        playerSetupPage.add(goNextButton);
        playerSetupPage.add(numberOfPlayersLabel);
        playerSetupPage.addTable(erzählerFrame.deletePlayerTable);
        playerSetupPage.addTable(erzählerFrame.playerLabelTable);

        erzählerFrame.setupPages.add(playerSetupPage);

        return playerSetupPage;
    }

    public Page generateHauptrolleSetupPage(Page hauptrolleSetupPage, int numberOfPlayers, int numberOfHauptrollen, ArrayList<String> hauptrollen) {
        PageTable hauptrolleButtonTable = pageElementFactory.generateButtonTable(erzählerFrame.hauptrolleButtonTable, null);
        pageElementFactory.generateTableButtons(hauptrollen, erzählerFrame.hauptrolleButtons, hauptrolleButtonTable);

        erzählerFrame.hauptrolleCounterJLabel = new JLabel(pageElementFactory.generateCounterLabelTitle(numberOfPlayers, numberOfHauptrollen));
        PageElement counterLabel = pageElementFactory.generateCounterLabel(erzählerFrame.hauptrolleCounterJLabel, hauptrolleButtonTable);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columnWidth = deleteButtonWidth + nameLabelWidth + spaceBetween;
        int columns = pageElementFactory.calculateColumns(columnWidth);

        erzählerFrame.deleteHauptrolleTable = pageElementFactory.generatePageTable(erzählerFrame.deleteHauptrolleTable, counterLabel,
                columns, deleteButtonWidth, tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        erzählerFrame.hauptrolleLabelTable = pageElementFactory.generatePageTable(erzählerFrame.hauptrolleLabelTable, counterLabel,
                columns, nameLabelWidth, tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        erzählerFrame.hauptrolleGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.hauptrolleGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.hauptrolleGoBackJButton);

        erzählerFrame.hauptrolleGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.hauptrolleGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.hauptrolleGoNextJButton);

        erzählerFrame.addAllHauptrollenJButton = new JButton();
        PageElement addAllHauptrollenButton = pageElementFactory.generateLowestButton(erzählerFrame.addAllHauptrollenJButton,
                "Alle Rollen", true, 1);

        hauptrolleSetupPage.clearPage();

        hauptrolleSetupPage.add(goNextButton);
        hauptrolleSetupPage.add(goBackButton);
        hauptrolleSetupPage.add(addAllHauptrollenButton);
        hauptrolleSetupPage.addTable(hauptrolleButtonTable);
        hauptrolleSetupPage.add(counterLabel);
        hauptrolleSetupPage.addTable(erzählerFrame.deleteHauptrolleTable);
        hauptrolleSetupPage.addTable(erzählerFrame.hauptrolleLabelTable);

        erzählerFrame.setupPages.add(hauptrolleSetupPage);

        return hauptrolleSetupPage;
    }

    public Page generateBonusrolleSetupPage(Page bonusrolleSetupPage, int numberOfPlayers, int numberOfBonusrollen, ArrayList<String> bonusrollen) {
        PageTable bonusrolleButtonTable = pageElementFactory.generateButtonTable(erzählerFrame.bonusrolleButtonTable, null);
        pageElementFactory.generateTableButtons(bonusrollen, erzählerFrame.bonusrolleButtons, bonusrolleButtonTable);

        erzählerFrame.bonusrolleCounterJLabel = new JLabel(pageElementFactory.generateCounterLabelTitle(numberOfPlayers, numberOfBonusrollen));
        PageElement counterLabel = pageElementFactory.generateCounterLabel(erzählerFrame.bonusrolleCounterJLabel, bonusrolleButtonTable);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columnWidth = deleteButtonWidth + nameLabelWidth + spaceBetween;
        int columns = pageElementFactory.calculateColumns(columnWidth);

        erzählerFrame.deleteBonusrolleTable = pageElementFactory.generatePageTable(erzählerFrame.deleteBonusrolleTable, counterLabel, columns, deleteButtonWidth,
                tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        erzählerFrame.bonusrolleLabelTable = pageElementFactory.generatePageTable(erzählerFrame.bonusrolleLabelTable, counterLabel, columns, nameLabelWidth,
                tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        erzählerFrame.bonusrolleGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.bonusrolleGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.bonusrolleGoBackJButton);

        erzählerFrame.bonusrolleGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.bonusrolleGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.bonusrolleGoNextJButton);

        erzählerFrame.addAllBonusrollenJButton = new JButton();
        PageElement addAllBonusrollenButton = pageElementFactory.generateLowestButton(erzählerFrame.addAllBonusrollenJButton,
                "Alle Rollen", true, 1);

        bonusrolleSetupPage.clearPage();

        bonusrolleSetupPage.add(goBackButton);
        bonusrolleSetupPage.add(goNextButton);
        bonusrolleSetupPage.add(addAllBonusrollenButton);
        bonusrolleSetupPage.addTable(bonusrolleButtonTable);
        bonusrolleSetupPage.add(counterLabel);
        bonusrolleSetupPage.addTable(erzählerFrame.deleteBonusrolleTable);
        bonusrolleSetupPage.addTable(erzählerFrame.bonusrolleLabelTable);

        erzählerFrame.setupPages.add(bonusrolleSetupPage);

        return bonusrolleSetupPage;
    }

    public Page generatePlayerSpecifiyPage(Page playerSpecifyPage, ArrayList<String> playersUnspecified, ArrayList<String> hauptrollenUnspecified, ArrayList<String> bonusrollenUnspecified) {
        String title = "Wählen Sie für diesen Spieler Haupt- und Bonusrolle.";
        String HTMLtitle = HTMLStringBuilder.buildHTMLText(title);
        PageElement titleLabel = pageElementFactory.generateLabel(null, HTMLtitle);
        titleLabel.width = 250;
        titleLabel.height = 50;

        PageElement playerLabel = pageElementFactory.generateLabel(titleLabel, "Spieler");
        erzählerFrame.comboBox1 = new JComboBox(playersUnspecified.toArray());
        PageElement choosePlayer = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, playerLabel, 0, 0);

        PageElement hauptrolleLabel = pageElementFactory.generateLabel(choosePlayer, "Hauptrolle");
        erzählerFrame.comboBox2 = new JComboBox(hauptrollenUnspecified.toArray());
        PageElement chooseHauptrolle = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, hauptrolleLabel, 0, 0);

        PageElement bonusrolleLabel = pageElementFactory.generateLabel(chooseHauptrolle, "Bonusrolle");
        erzählerFrame.comboBox3 = new JComboBox(bonusrollenUnspecified.toArray());
        PageElement chooseBonusrolle = pageElementFactory.generateDropdown(erzählerFrame.comboBox3,
                null, bonusrolleLabel, 0, 0);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int hauptrolleLabelWidth = 150;
        int bonusrolleLabelWidth = 150;
        int spaceBetween = 5;
        int columns = 1;

        erzählerFrame.deleteSpecifyPlayerTable = pageElementFactory.generatePageTable(erzählerFrame.deleteSpecifyPlayerTable, titleLabel, null, columns, deleteButtonWidth,
                tableElementHeight, nameLabelWidth + hauptrolleLabelWidth + bonusrolleLabelWidth, spaceBetween,
                0, spaceBetween);

        erzählerFrame.playerSpecifyLabelTable = pageElementFactory.generatePageTable(erzählerFrame.playerSpecifyLabelTable, titleLabel, null, columns, nameLabelWidth,
                tableElementHeight, deleteButtonWidth + hauptrolleLabelWidth + bonusrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween, spaceBetween);

        erzählerFrame.hauptrolleSpecifyLabelTable = pageElementFactory.generatePageTable(erzählerFrame.hauptrolleSpecifyLabelTable, titleLabel, null, columns, hauptrolleLabelWidth,
                tableElementHeight, deleteButtonWidth + nameLabelWidth + bonusrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween * 2 + nameLabelWidth, spaceBetween);

        erzählerFrame.bonusrolleSpecifyLabelTable = pageElementFactory.generatePageTable(erzählerFrame.bonusrolleSpecifyLabelTable, titleLabel, null, columns, bonusrolleLabelWidth,
                tableElementHeight, deleteButtonWidth + nameLabelWidth + hauptrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween * 3 + nameLabelWidth + hauptrolleLabelWidth, spaceBetween);

        erzählerFrame.playerSpecifyGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.playerSpecifyGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.playerSpecifyGoNextJButton);

        erzählerFrame.playerSpecifyGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.playerSpecifyGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.playerSpecifyGoBackJButton);

        playerSpecifyPage.clearPage();

        playerSpecifyPage.add(titleLabel);
        playerSpecifyPage.add(playerLabel);
        playerSpecifyPage.add(choosePlayer);
        playerSpecifyPage.add(hauptrolleLabel);
        playerSpecifyPage.add(chooseHauptrolle);
        playerSpecifyPage.add(bonusrolleLabel);
        playerSpecifyPage.add(chooseBonusrolle);
        playerSpecifyPage.addTable(erzählerFrame.playerSpecifyLabelTable);
        playerSpecifyPage.addTable(erzählerFrame.hauptrolleSpecifyLabelTable);
        playerSpecifyPage.addTable(erzählerFrame.bonusrolleSpecifyLabelTable);
        playerSpecifyPage.addTable(erzählerFrame.deleteSpecifyPlayerTable);
        playerSpecifyPage.add(goNextButton);
        playerSpecifyPage.add(goBackButton);

        erzählerFrame.setupPages.add(playerSpecifyPage);

        return playerSpecifyPage;
    }

    public Page generateDefaultNightPage(Statement statement) {
        return generateDefaultNightPage(statement, statement.title);
    }

    public Page generateDefaultNightPage(Statement statement, String title) {
        PageElement nightLabel = pageElementFactory.generateNightLabel(statement.identifier);
        PageElement titleLabel = pageElementFactory.generateTitleLabel(nightLabel, title);
        erzählerFrame.nextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);
        Page defaultNightPage = new Page();

        defaultNightPage.add(nightLabel);
        defaultNightPage.add(titleLabel);
        defaultNightPage.add(goNextButton);

        continueToGeneratePagePoint = defaultNightPage.pageElements.indexOf(titleLabel);

        return defaultNightPage;
    }

    public Page generateDeactivatedPage(Statement statement) {
        return generateIconPicturePage(statement, ImagePath.DEAKTIVIERT);
    }

    public Page generateAufgebrauchtPage(Statement statement) {
        return generateIconPicturePage(statement, ImagePath.AUFGEBRAUCHT);
    }

    public Page generateIconPicturePage(Statement statement, String imagePath) {
        return generateIconPicturePage(statement, statement.title, imagePath);
    }

    public Page generateIconPicturePage(Statement statement, String title, String imagePath) {
        Page picturePage = generateDefaultNightPage(statement, title);
        PageElement deactivatedIcon = pageElementFactory.generateIcon(getContinueToGeneratePagePoint(picturePage), imagePath);

        picturePage.add(deactivatedIcon);

        return picturePage;
    }

    public Page generateCardPicturePage(Statement statement, String title, String imagePath) {
        Page picturePage = generateDefaultNightPage(statement, title);
        PageElement deactivatedIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(picturePage), imagePath);

        picturePage.add(deactivatedIcon);

        return picturePage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions) {
        Page dropdownPage = generateDefaultNightPage(statement);
        erzählerFrame.comboBox1 = new JComboBox(dropdownOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer1);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(choosePlayer1);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, String imagePath) {
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2) {
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions);
        erzählerFrame.comboBox2 = new JComboBox(dropdownOptions2.toArray());
        PageElement choosePlayer2 = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer2);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(choosePlayer2);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2, String imagePath) {
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions, dropdownOptions2);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2, ArrayList<String> dropdownOptions3) {
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions, dropdownOptions2);
        erzählerFrame.comboBox3 = new JComboBox(dropdownOptions3.toArray());
        PageElement choosePlayer3 = pageElementFactory.generateDropdown(erzählerFrame.comboBox3,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer3);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2, ArrayList<String> dropdownOptions3, String imagePath) {
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions, dropdownOptions2, dropdownOptions3);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateListPage(Statement statement, ArrayList<String> stringsToDisplay) {
        return generateListPage(statement, statement.title, stringsToDisplay);
    }

    public Page generateListPage(Statement statement, String title, ArrayList<String> stringsToDisplay) {
        Page listPage = generateDefaultNightPage(statement, title);

        if (stringsToDisplay.size() > 0) {
            PageElement label = pageElementFactory.generateLabel(getContinueToGeneratePagePoint(listPage), stringsToDisplay.get(0));
            listPage.add(label);
            int i = 0;
            for (String string : stringsToDisplay) {
                if (i != 0) {
                    label = pageElementFactory.generateLabel(label, string);
                    listPage.add(label);
                    continueToGeneratePagePoint = listPage.pageElements.indexOf(label);
                }

                i++;
            }
        }

        return listPage;
    }

    public Page generateListPage(Statement statement, ArrayList<String> stringsToDisplay, String imagePath) {
        return generateListPage(statement, statement.title, stringsToDisplay, imagePath);
    }

    public Page generateListPage(Statement statement, String title, ArrayList<String> stringsToDisplay, String imagePath) {
        Page listPage = generateListPage(statement, title, stringsToDisplay);

        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(listPage), imagePath);

        modeIcon.addYSpace(20);

        listPage.add(modeIcon);

        continueToGeneratePagePoint = listPage.pageElements.indexOf(modeIcon);

        return listPage;
    }

    public Page generateDayPage(ArrayList<String> livingPlayers) {
        Page dayPage = generateDefaultDayPage();

        erzählerFrame.comboBox1 = new JComboBox(livingPlayers.toArray());
        PageElement choosePlayer = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, continueToGenerateElement);

        dayPage.add(choosePlayer);

        return dayPage;
    }

    public Page generateAnnounceVictimsDayPage(String spieler1, String imagepath) {
        Page dayPage = generateDefaultDayPage();

        PageElement nameLabel = pageElementFactory.generateLeftCenteredLabel(new JLabel(spieler1));
        PageElement deadImage = pageElementFactory.generateRightCenteredImage(imagepath);

        dayPage.add(nameLabel);
        dayPage.add(deadImage);

        return dayPage;
    }

    private Page generateDefaultDayPage() {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, Day.dayTitle);
        continueToGenerateElement = titleLabel;

        erzählerFrame.nextJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);

        erzählerFrame.nachzüglerJButton = new JButton();
        PageElement nachzüglerButton = pageElementFactory.generateLowestButton(erzählerFrame.nachzüglerJButton, "Nachzügler", false);

        erzählerFrame.umbringenJButton = new JButton();
        PageElement umbringenButton = pageElementFactory.generateLowestButton(erzählerFrame.umbringenJButton, "Umbringen", false, 1);

        erzählerFrame.priesterJButton = new JButton();
        PageElement priesterButton = pageElementFactory.generateLowestButton(erzählerFrame.priesterJButton, "Priester", false, 2);

        erzählerFrame.richterinJButton = new JButton();
        PageElement richterinButton = pageElementFactory.generateLowestButton(erzählerFrame.richterinJButton, "Richterin", false, 3);

        erzählerFrame.respawnFramesJButton = new JButton();
        PageElement respawnFramesButton = pageElementFactory.generateLowestButton(erzählerFrame.respawnFramesJButton, "Fenster neustart", false, 4);

        Page dayPage = new Page();

        dayPage.add(titleLabel);
        dayPage.add(nextButton);
        dayPage.add(nachzüglerButton);
        dayPage.add(umbringenButton);
        dayPage.add(priesterButton);
        dayPage.add(richterinButton);
        dayPage.add(respawnFramesButton);

        return dayPage;
    }

    public Page generateNachzüglerPage(ArrayList<String> hauptrollen, ArrayList<String> bonusrollen) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Nachzügler");

        PageElement nameLabel = pageElementFactory.generateLabel(titleLabel, "Name");

        PageElement nameTxtField = pageElementFactory.generateAddPlayerTxtField(nameLabel);

        PageElement hauptRolleLabel = pageElementFactory.generateLabel(nameTxtField, "Hauptrolle");

        erzählerFrame.comboBox1 = new JComboBox(hauptrollen.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, hauptRolleLabel, 0, 0);

        PageElement bonusRolleLabel = pageElementFactory.generateLabel(choosePlayer1, "Hauptrolle");

        erzählerFrame.comboBox2 = new JComboBox(bonusrollen.toArray());
        PageElement choosePlayer2 = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, bonusRolleLabel, 0, 0);


        erzählerFrame.nachzüglerJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.nachzüglerJButton);

        erzählerFrame.goBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.goBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.goBackJButton);

        Page nachzüglerPage = new Page();

        nachzüglerPage.add(titleLabel);
        nachzüglerPage.add(nameLabel);
        nachzüglerPage.add(nameTxtField);
        nachzüglerPage.add(hauptRolleLabel);
        nachzüglerPage.add(choosePlayer1);
        nachzüglerPage.add(bonusRolleLabel);
        nachzüglerPage.add(choosePlayer2);
        nachzüglerPage.add(nextButton);
        nachzüglerPage.add(goBackButton);

        return nachzüglerPage;
    }

    public Page generateUmbringenPage(ArrayList<String> livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Umbringen");

        PageElement nameLabel = pageElementFactory.generateLabel(titleLabel, "Name");

        ArrayList<String> comboBoxOptions = livingPlayers;
        comboBoxOptions.add("");
        erzählerFrame.comboBox1 = new JComboBox(comboBoxOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, nameLabel, 0, 0);

        erzählerFrame.umbringenJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.umbringenJButton);

        erzählerFrame.goBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.goBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.goBackJButton);

        Page umbringenPage = new Page();

        umbringenPage.add(titleLabel);
        umbringenPage.add(nameLabel);
        umbringenPage.add(choosePlayer1);
        umbringenPage.add(nextButton);
        umbringenPage.add(goBackButton);

        return umbringenPage;
    }

    public Page generatePriesterPage(ArrayList<String> livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Bürgen");

        PageElement priesterLabel = pageElementFactory.generateLabel(titleLabel, "Priester");

        ArrayList<String> comboBoxOptions = livingPlayers;
        erzählerFrame.comboBox1 = new JComboBox(comboBoxOptions.toArray());
        PageElement choosePriester = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, priesterLabel, 0, 0);

        PageElement spielerLabel = pageElementFactory.generateLabel(choosePriester, "Spieler");

        ArrayList<String> comboBox2Options = livingPlayers;
        erzählerFrame.comboBox2 = new JComboBox(comboBox2Options.toArray());
        PageElement choosePlayer = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, spielerLabel, 0, 0);


        erzählerFrame.priesterJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.priesterJButton);

        erzählerFrame.goBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.goBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.goBackJButton);

        Page priesterPage = new Page();

        priesterPage.add(titleLabel);
        priesterPage.add(priesterLabel);
        priesterPage.add(choosePriester);
        priesterPage.add(spielerLabel);
        priesterPage.add(choosePlayer);
        priesterPage.add(nextButton);
        priesterPage.add(goBackButton);

        return priesterPage;
    }

    public Page generateRichterinPage(ArrayList<String> livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Verurteilen");

        PageElement richterinLabel = pageElementFactory.generateLabel(titleLabel, "Richterin");

        ArrayList<String> comboBoxOptions = livingPlayers;
        erzählerFrame.comboBox1 = new JComboBox(comboBoxOptions.toArray());
        PageElement chooseRichterin = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, richterinLabel, 0, 0);

        PageElement spielerLabel = pageElementFactory.generateLabel(chooseRichterin, "Spieler");

        ArrayList<String> comboBox2Options = livingPlayers;
        erzählerFrame.comboBox2 = new JComboBox(comboBox2Options.toArray());
        PageElement choosePlayer = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, spielerLabel, 0, 0);


        erzählerFrame.richterinJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.richterinJButton);

        erzählerFrame.goBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.goBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.goBackJButton);

        Page richterinPage = new Page();

        richterinPage.add(titleLabel);
        richterinPage.add(richterinLabel);
        richterinPage.add(chooseRichterin);
        richterinPage.add(spielerLabel);
        richterinPage.add(choosePlayer);
        richterinPage.add(nextButton);
        richterinPage.add(goBackButton);

        return richterinPage;
    }

    public Page generateTortenPage(Page tortenPage, ArrayList<String> livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Torte");

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 2;

        ArrayList<String> comboBoxOptions = livingPlayers;
        erzählerFrame.comboBox1 = new JComboBox(comboBoxOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, titleLabel, 0, 0);

        erzählerFrame.addPlayerTortenButton = new JButton("Hinzufügen");
        PageElement addPlayerButton = pageElementFactory.generateSmallButton(erzählerFrame.addPlayerTortenButton, choosePlayer1);

        erzählerFrame.deleteTortenPlayerTable = pageElementFactory.generatePageTable(erzählerFrame.deleteTortenPlayerTable, choosePlayer1,
                columns, deleteButtonWidth, tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        erzählerFrame.tortenPlayerLabelTable = pageElementFactory.generatePageTable(erzählerFrame.tortenPlayerLabelTable, choosePlayer1,
                columns, nameLabelWidth, tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        erzählerFrame.nextJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);

        tortenPage.clearPage();

        tortenPage.add(titleLabel);
        tortenPage.add(choosePlayer1);
        tortenPage.add(addPlayerButton);
        tortenPage.addTable(erzählerFrame.deleteTortenPlayerTable);
        tortenPage.addTable(erzählerFrame.tortenPlayerLabelTable);
        tortenPage.add(nextButton);

        return tortenPage;
    }

    /*public Page generateVictoryPage(String victoryMessage) {
        Page victoryPage = new Page();
        JLabel victoryJLabel = pageElementFactory.generateBigJLabel(new JLabel(victoryMessage));
        int width = victoryJLabel.getPreferredSize().width;
        int height = victoryJLabel.getPreferredSize().height;
        int spaceToPredecessorX = erzählerFrame.frameJPanel.getWidth()/2 - width/2;
        int spaceToPredecessorY = erzählerFrame.frameJPanel.getWidth()/2 - height/2;

        PageElement victoryLabel = new PageElement(victoryJLabel, width, height, null, null, spaceToPredecessorX, spaceToPredecessorY);

        victoryPage.add(victoryLabel);

        return victoryPage;
    }*/

    public PageElement getContinueToGeneratePagePoint(Page page) {
        return page.pageElements.get(continueToGeneratePagePoint);
    }

}
