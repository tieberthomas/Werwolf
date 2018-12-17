package root.Frontend.Factories;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.Utils.JButtonStyler;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.*;
import root.Logic.Phases.Day;
import root.Logic.Phases.NormalNight;
import root.Logic.Phases.SetupNight;
import root.Logic.Phases.Statement.Statement;
import root.ResourceManagement.ImagePath;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class ErzählerPageFactory {
    private ErzählerFrame erzählerFrame;
    public ErzählerPageElementFactory pageElementFactory;
    private static int continueToGeneratePagePoint = 0;
    private static PageElement continueToGenerateElement = null;
    public static boolean nightGenerationModeSetupNight = true;

    public ErzählerPageFactory(ErzählerFrame frame) {
        erzählerFrame = frame;
        pageElementFactory = new ErzählerPageElementFactory(erzählerFrame);
    }

    public void generateStartPage(Page startPage, StartPageElementsDto pageElements) {
        PageElement werwolfIcon = pageElementFactory.generateCenteredImageLabel(ImagePath.WÖLFE_ICON, 3, 60);

        pageElements.startButton.setText("Neues Spiel");
        PageElement startButton = pageElementFactory.generateCenteredBigButton(pageElements.startButton, werwolfIcon);

        pageElements.lastCompositionButton.setText("Letzte Zusammenstellung laden");
        PageElement loadLastComposition = pageElementFactory.generateCenteredBigButton(pageElements.lastCompositionButton, startButton);

        pageElements.lastGameButton.setText("Letztes Spiel laden");
        PageElement loadLastGame = pageElementFactory.generateCenteredBigButton(pageElements.lastGameButton, loadLastComposition);

        startPage.add(werwolfIcon);
        startPage.add(startButton);
        startPage.add(loadLastComposition);
        startPage.add(loadLastGame);

    }

    public void generatePlayerSetupPage(Page playerSetupPage, PlayerSetupPageElementsDto interactiveElementsDto, int numberOfPlayers) {
        PageElement nameLabel = pageElementFactory.generateLabel(null, "Name");

        PageElement addPlayerTxtField = pageElementFactory.generateAddPlayerTxtField(interactiveElementsDto.addPlayerTxtField, nameLabel);

        JButton addPlayerJButton = interactiveElementsDto.addPlayerButton;
        addPlayerJButton.setText("Hinzufügen");
        PageElement addPlayerButton = pageElementFactory.generateSmallButton(addPlayerJButton, addPlayerTxtField);

        JLabel counterJLabel = interactiveElementsDto.counterLabel;
        counterJLabel.setText(pageElementFactory.generateNumberOfPLayersLabelTitle(numberOfPlayers));
        PageElement counterLabel = pageElementFactory.generateCounterLabel(counterJLabel, addPlayerTxtField);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 2;

        PageTable deleteTable = interactiveElementsDto.deleteTable;
        deleteTable = pageElementFactory.generatePageTable(deleteTable, counterLabel,
                columns, deleteButtonWidth, tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        PageTable labelTable = interactiveElementsDto.labelTable;
        labelTable = pageElementFactory.generatePageTable(labelTable, counterLabel,
                columns, nameLabelWidth, tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        PageElement goBackButton = pageElementFactory.generateLowestButton(interactiveElementsDto.back, "Zurück", false);

        PageElement goNextButton = pageElementFactory.generateLowestButton(interactiveElementsDto.next);

        playerSetupPage.clearPage();

        playerSetupPage.add(nameLabel);
        playerSetupPage.add(addPlayerButton);
        playerSetupPage.add(addPlayerTxtField);
        playerSetupPage.add(goBackButton);
        playerSetupPage.add(goNextButton);
        playerSetupPage.add(counterLabel);
        playerSetupPage.add(deleteTable);
        playerSetupPage.add(labelTable);

    }

    public void generateRollenSetupPage(Page rollenSetupPage, RolePageElementsDto interactiveElementsDto, List<String> roleNames) {
        PageTable roleButtonTable = pageElementFactory.generateButtonTable(interactiveElementsDto.roleButtonTable, null);
        pageElementFactory.generateTableButtons(roleNames, interactiveElementsDto.roleButtons, roleButtonTable);

        JLabel counterJLabel = interactiveElementsDto.counterLabel;
        PageElement counterLabel = pageElementFactory.generateCounterLabel(counterJLabel, roleButtonTable);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columnWidth = deleteButtonWidth + nameLabelWidth + spaceBetween;
        int columns = pageElementFactory.calculateColumns(columnWidth);

        interactiveElementsDto.deleteTable = pageElementFactory.generatePageTable(interactiveElementsDto.deleteTable, counterLabel,
                columns, deleteButtonWidth, tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        interactiveElementsDto.labelTable = pageElementFactory.generatePageTable(interactiveElementsDto.labelTable, counterLabel,
                columns, nameLabelWidth, tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        PageElement goBackButton = pageElementFactory.generateLowestButton(interactiveElementsDto.back, "Zurück", false);

        PageElement goNextButton = pageElementFactory.generateLowestButton(interactiveElementsDto.next);

        PageElement addAllRolesButton = pageElementFactory.generateLowestButton(interactiveElementsDto.addAllRolesButton,
                "Alle Rollen", true, 1);

        PageElement deleteAllRolesButton = pageElementFactory.generateLowestButton(interactiveElementsDto.deleteAllRolesButton,
                "Alle löschen", true, 2);

        rollenSetupPage.clearPage();

        rollenSetupPage.add(goNextButton);
        rollenSetupPage.add(goBackButton);
        rollenSetupPage.add(addAllRolesButton);
        rollenSetupPage.add(deleteAllRolesButton);
        rollenSetupPage.add(roleButtonTable);
        rollenSetupPage.add(counterLabel);
        rollenSetupPage.add(interactiveElementsDto.deleteTable);
        rollenSetupPage.add(interactiveElementsDto.labelTable);

    }

    public void generateSpecifiyPage(Page playerSpecifyPage, SpecifyPageElementsDto pageElements) {
        String title = "Wählen Sie für diesen Spieler Haupt- und Bonusrolle.";
        String HTMLtitle = HTMLStringBuilder.buildHTMLText(title);
        PageElement titleLabel = pageElementFactory.generateLabel(null, HTMLtitle);
        titleLabel.width = 250;
        titleLabel.height = 50;

        PageElement playerLabel = pageElementFactory.generateLabel(titleLabel, "Spieler");
        PageElement choosePlayer = pageElementFactory.generateDropdown(pageElements.comboBox1,
                null, playerLabel, 0, 0);

        PageElement hauptrolleLabel = pageElementFactory.generateLabel(choosePlayer, "Hauptrolle");
        PageElement chooseHauptrolle = pageElementFactory.generateDropdown(pageElements.comboBox2,
                null, hauptrolleLabel, 0, 0);

        PageElement bonusrolleLabel = pageElementFactory.generateLabel(chooseHauptrolle, "Bonusrolle");
        PageElement chooseBonusrolle = pageElementFactory.generateDropdown(pageElements.comboBox3,
                null, bonusrolleLabel, 0, 0);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int hauptrolleLabelWidth = 150;
        int bonusrolleLabelWidth = 150;
        int spaceBetween = 5;
        int columns = 1;

        int headingTextsize = 24;

        JLabel playerHeadingLabel = pageElementFactory.generateTitleJLabel("Spieler", headingTextsize);
        PageElement playerHeading = pageElementFactory.generateXLabel(titleLabel, playerHeadingLabel, deleteButtonWidth + 5);

        pageElements.deleteTable = pageElementFactory.generatePageTable(pageElements.deleteTable, titleLabel, playerHeading, columns, deleteButtonWidth,
                tableElementHeight, nameLabelWidth + hauptrolleLabelWidth + bonusrolleLabelWidth, spaceBetween,
                0, spaceBetween);


        pageElements.playerLabelTable = pageElementFactory.generatePageTable(pageElements.playerLabelTable, titleLabel, playerHeading, columns, nameLabelWidth,
                tableElementHeight, deleteButtonWidth + hauptrolleLabelWidth + bonusrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween, spaceBetween);

        JLabel mainRoleHeadingLabel = pageElementFactory.generateTitleJLabel("Hauptrolle", headingTextsize);
        int mainRoleHeadingSpaceToPredecessor = deleteButtonWidth + 10 + nameLabelWidth;
        PageElement mainRoleHeading = pageElementFactory.generateXLabel(titleLabel, mainRoleHeadingLabel, mainRoleHeadingSpaceToPredecessor);
        pageElements.mainroleLabelTable = pageElementFactory.generatePageTable(pageElements.mainroleLabelTable, titleLabel, playerHeading, columns, hauptrolleLabelWidth,
                tableElementHeight, deleteButtonWidth + nameLabelWidth + bonusrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween * 2 + nameLabelWidth, spaceBetween);

        JLabel bonusRoleHeadingLabel = pageElementFactory.generateTitleJLabel("Bonusrolle", headingTextsize);
        int bonusRoleHeadingSpaceToPredecessor = deleteButtonWidth + 15 + nameLabelWidth + hauptrolleLabelWidth;
        PageElement bonusRoleHeading = pageElementFactory.generateXLabel(titleLabel, bonusRoleHeadingLabel, bonusRoleHeadingSpaceToPredecessor);
        pageElements.bonusroleLabelTable = pageElementFactory.generatePageTable(pageElements.bonusroleLabelTable, titleLabel, playerHeading, columns, bonusrolleLabelWidth,
                tableElementHeight, deleteButtonWidth + nameLabelWidth + hauptrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween * 3 + nameLabelWidth + hauptrolleLabelWidth, spaceBetween);

        PageElement goBackButton = pageElementFactory.generateLowestButton(pageElements.back, "Zurück", false);

        PageElement goNextButton = pageElementFactory.generateLowestButton(pageElements.next);

        playerSpecifyPage.clearPage();

        playerSpecifyPage.add(titleLabel);
        playerSpecifyPage.add(playerLabel);
        playerSpecifyPage.add(choosePlayer);
        playerSpecifyPage.add(hauptrolleLabel);
        playerSpecifyPage.add(chooseHauptrolle);
        playerSpecifyPage.add(bonusrolleLabel);
        playerSpecifyPage.add(chooseBonusrolle);
        playerSpecifyPage.add(playerHeading);
        playerSpecifyPage.add(mainRoleHeading);
        playerSpecifyPage.add(bonusRoleHeading);
        playerSpecifyPage.add(pageElements.deleteTable);
        playerSpecifyPage.add(pageElements.playerLabelTable);
        playerSpecifyPage.add(pageElements.mainroleLabelTable);
        playerSpecifyPage.add(pageElements.bonusroleLabelTable);
        playerSpecifyPage.add(goNextButton);
        playerSpecifyPage.add(goBackButton);

    }

    public Page generateDefaultNightPage(Page nightPage, Statement statement) {
        return generateDefaultNightPage(nightPage, statement, statement.title);
    }

    private Page generateDefaultNightPage(Page nightPage, Statement statement, String title) {
        return generateDefaultNightPage(nightPage, statement, title, false);
    }

    private Page generateDefaultNightPage(Page nightPage, Statement statement, boolean hatZurückButton) {
        return generateDefaultNightPage(nightPage, statement, statement.title, hatZurückButton);
    }

    private Page generateDefaultNightPage(Page nightPage, Statement statement, String title, boolean hatZurückButton) {
        List<Statement> statements = getNightStatements();

        PageElement nightLabel = pageElementFactory.generateNightLabel(statement.id, statements);
        PageElement titleLabel = pageElementFactory.generateTitleLabel(nightLabel, title);
        erzählerFrame.nextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);

        nightPage.add(nightLabel);
        nightPage.add(titleLabel);
        nightPage.add(goNextButton);

        if (hatZurückButton) {
            PageElement zurückButton = pageElementFactory.generateLowestButton(erzählerFrame.henkerGoBackButton, true, 1);
            nightPage.add(zurückButton);
        }

        continueToGeneratePagePoint = nightPage.pageElements.indexOf(titleLabel);

        return nightPage;
    }

    private List<Statement> getNightStatements() {
        List<Statement> statements;

        if (nightGenerationModeSetupNight) {
            statements = SetupNight.statements;
        } else {
            statements = NormalNight.statements;
        }

        return statements;
    }

    public Page generateIconPicturePage(Page iconPage, Statement statement, String imagePath) {
        return generateIconPicturePage(iconPage, statement, statement.title, imagePath);
    }

    public Page generateIconPicturePage(Page picturePage, Statement statement, String title, String imagePath) {
        picturePage = generateDefaultNightPage(picturePage, statement, title);
        PageElement deactivatedIcon = pageElementFactory.generateIcon(getContinueToGeneratePagePoint(picturePage), imagePath);

        picturePage.add(deactivatedIcon);

        return picturePage;
    }

    public Page generateCardPicturePage(Page picturePage, Statement statement, String title, String imagePath) {
        picturePage = generateDefaultNightPage(picturePage, statement, title);
        PageElement deactivatedIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(picturePage), imagePath);

        picturePage.add(deactivatedIcon);

        return picturePage;
    }

    private Page generateDropdownPage(Page dropdownPage, Statement statement, DropdownOptions dropdownOptions) {
        return generateDropdownPage(dropdownPage, statement, dropdownOptions, false);
    }

    public Page generateDropdownPage(Page dropdownPage, Statement statement, DropdownOptions dropdownOptions, boolean hatZurückButton) {
        dropdownPage = generateDefaultNightPage(dropdownPage, statement, hatZurückButton);
        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownOptions.toArray());
        erzählerFrame.comboBox1.setModel(model);
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer1);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(choosePlayer1);

        return dropdownPage;
    }

    public Page generateDropdownPage(Page dropdownPage, Statement statement, DropdownOptions dropdownOptions, String imagePath) {
        dropdownPage = generateDropdownPage(dropdownPage, statement, dropdownOptions);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateDropdownPage(Page dropdownPage, Statement statement, DropdownOptions dropdownOptions, DropdownOptions dropdownOptions2) {
        dropdownPage = generateDropdownPage(dropdownPage, statement, dropdownOptions);
        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownOptions2.toArray());
        erzählerFrame.comboBox2.setModel(model);
        PageElement choosePlayer2 = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer2);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(choosePlayer2);

        return dropdownPage;
    }

    public Page generateDropdownPage(Page dropdownPage, Statement statement, DropdownOptions dropdownOptions, DropdownOptions dropdownOptions2, String imagePath) {
        dropdownPage = generateDropdownPage(dropdownPage, statement, dropdownOptions, dropdownOptions2);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateListPage(Page listPage, Statement statement, List<String> stringsToDisplay) {
        return generateListPage(listPage, statement, statement.title, stringsToDisplay, false);
    }

    public Page generateListPage(Page listPage, Statement statement, String title, List<String> stringsToDisplay, boolean hatZurückButton) {
        listPage = generateDefaultNightPage(listPage, statement, title, hatZurückButton);

        Collections.sort(stringsToDisplay, String.CASE_INSENSITIVE_ORDER);

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

    public Page generateListPage(Page listPage, Statement statement, List<String> stringsToDisplay, String imagePath) {
        return generateListPage(listPage, statement, statement.title, stringsToDisplay, imagePath);
    }

    public Page generateListPage(Page listPage, Statement statement, String title, List<String> stringsToDisplay, String imagePath) {
        listPage = generateListPage(listPage, statement, title, stringsToDisplay, false);

        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(listPage), imagePath);

        modeIcon.addYSpace(20);

        listPage.add(modeIcon);

        continueToGeneratePagePoint = listPage.pageElements.indexOf(modeIcon);

        return listPage;
    }

    public Page generateDayPage(DropdownOptions livingPlayers) {
        Page dayPage = generateDefaultDayPage();

        DefaultComboBoxModel model = new DefaultComboBoxModel(livingPlayers.toArray());
        erzählerFrame.comboBox1.setModel(model);
        PageElement choosePlayer = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, continueToGenerateElement);

        dayPage.add(choosePlayer);

        return dayPage;
    }

    public Page generateAnnounceOpferDayPage(String spieler1, String imagepath) {
        Page dayPage = generateDefaultDayPage();
        JButtonStyler.disableButton(erzählerFrame.priesterJButton);
        JButtonStyler.disableButton(erzählerFrame.richterinJButton);

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

        erzählerFrame.umbringenJButton = new JButton();
        PageElement umbringenButton = pageElementFactory.generateLowestButton(erzählerFrame.umbringenJButton, "Umbringen", false);

        erzählerFrame.priesterJButton = new JButton();
        PageElement priesterButton = pageElementFactory.generateLowestButton(erzählerFrame.priesterJButton, "Priester", false, 1);

        erzählerFrame.richterinJButton = new JButton();
        PageElement richterinButton = pageElementFactory.generateLowestButton(erzählerFrame.richterinJButton, "Richterin", false, 2);

        erzählerFrame.respawnFramesJButton = new JButton();
        PageElement respawnFramesButton = pageElementFactory.generateLowestButton(erzählerFrame.respawnFramesJButton, "Fenster neustart", false, 3);

        Page dayPage = new Page();

        dayPage.add(titleLabel);
        dayPage.add(nextButton);
        dayPage.add(umbringenButton);
        dayPage.add(priesterButton);
        dayPage.add(richterinButton);
        dayPage.add(respawnFramesButton);

        return dayPage;
    }

    public void generateTortenPage(Page tortenPage, OneDropdownDeletePageDto pageElements, DropdownOptions livingPlayers, String title) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, title);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 2;

        DefaultComboBoxModel model = new DefaultComboBoxModel(livingPlayers.toArray());
        pageElements.nameComboBox.setModel(model);
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(pageElements.nameComboBox,
                null, titleLabel, 0, 0);

        PageElement addPlayerButton = pageElementFactory.generateSmallButton(pageElements.addPlayerButton, choosePlayer1);

        pageElements.deleteTable = pageElementFactory.generatePageTable(pageElements.deleteTable, choosePlayer1,
                columns, deleteButtonWidth, tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        pageElements.labelTable = pageElementFactory.generatePageTable(pageElements.labelTable, choosePlayer1,
                columns, nameLabelWidth, tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);

        tortenPage.clearPage();

        tortenPage.add(titleLabel);
        tortenPage.add(choosePlayer1);
        tortenPage.add(addPlayerButton);
        tortenPage.add(pageElements.deleteTable);
        tortenPage.add(pageElements.labelTable);
        tortenPage.add(nextButton);

    }

    public void generateOneDropdownPage(Page page, OneDropdownPageDto pageElements, DropdownOptions dropdownOptions) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, pageElements.title);

        PageElement nameLabel = pageElementFactory.generateLabel(titleLabel, pageElements.comboBoxName);

        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownOptions.toArray());
        pageElements.comboBox.setModel(model);
        PageElement comboBox = pageElementFactory.generateDropdown(pageElements.comboBox,
                null, nameLabel, 0, 0);

        PageElement nextButton = pageElementFactory.generateLowestButton(pageElements.next);

        PageElement goBackButton = pageElementFactory.generateLowestButton(pageElements.back, "Zurück", false);

        page.clearPage();
        page.add(titleLabel);
        page.add(nameLabel);
        page.add(comboBox);
        page.add(nextButton);
        page.add(goBackButton);
    }

    public void generateTwoDropdownPage(Page page, TwoDropdownPageDto pageElements, DropdownOptions dropdownOptions) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, pageElements.title);

        PageElement nameLabel = pageElementFactory.generateLabel(titleLabel, pageElements.comboBoxName);

        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownOptions.toArray());
        pageElements.comboBox.setModel(model);
        PageElement comboBox = pageElementFactory.generateDropdown(pageElements.comboBox,
                null, nameLabel, 0, 0);

        PageElement nameLabel2 = pageElementFactory.generateLabel(comboBox, pageElements.comboBoxName2);

        DefaultComboBoxModel model2 = new DefaultComboBoxModel(dropdownOptions.toArray());
        pageElements.comboBox2.setModel(model2);
        PageElement comboBox2 = pageElementFactory.generateDropdown(pageElements.comboBox2,
                null, nameLabel2, 0, 0);

        PageElement nextButton = pageElementFactory.generateLowestButton(pageElements.next);

        PageElement goBackButton = pageElementFactory.generateLowestButton(pageElements.back, "Zurück", false);

        page.clearPage();
        page.add(titleLabel);
        page.add(nameLabel);
        page.add(comboBox);
        page.add(nameLabel2);
        page.add(comboBox2);
        page.add(nextButton);
        page.add(goBackButton);

    }

    private PageElement getContinueToGeneratePagePoint(Page page) {
        return page.pageElements.get(continueToGeneratePagePoint);
    }

    public void generateIrrlichtDropdownPage(Page irrlichtPage, OneDropdownDeletePageDto pageElements, Statement statement, DropdownOptions dropdownStrings) {
        irrlichtPage = generateDefaultNightPage(irrlichtPage, statement, false);
        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownStrings.toArray());
        pageElements.nameComboBox.setModel(model);
        PageElement choosePlayer = pageElementFactory.generateDropdown(pageElements.nameComboBox,
                null, getContinueToGeneratePagePoint(irrlichtPage));

        PageElement addIrrlichtButton = pageElementFactory.generateSmallButton(pageElements.addPlayerButton, choosePlayer);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 1;

        pageElements.deleteTable = pageElementFactory.generatePageTable(pageElements.deleteTable, choosePlayer,
                columns, deleteButtonWidth, tableElementHeight, nameLabelWidth, spaceBetween, 0, spaceBetween);

        pageElements.labelTable = pageElementFactory.generatePageTable(pageElements.labelTable, choosePlayer,
                columns, nameLabelWidth, tableElementHeight, deleteButtonWidth, spaceBetween, deleteButtonWidth + spaceBetween, spaceBetween);

        irrlichtPage.add(addIrrlichtButton);
        irrlichtPage.add(pageElements.deleteTable);
        irrlichtPage.add(pageElements.labelTable);
        irrlichtPage.add(choosePlayer);

    }
}
