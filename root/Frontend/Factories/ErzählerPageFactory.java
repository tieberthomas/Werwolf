package root.Frontend.Factories;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.Utils.JButtonStyler;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.*;
import root.Phases.Day;
import root.Phases.NightBuilding.Statement;
import root.ResourceManagement.ImagePath;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class ErzählerPageFactory {
    private ErzählerFrame erzählerFrame;
    public ErzählerPageElementFactory pageElementFactory;
    private static int continueToGeneratePagePoint = 0;
    private static PageElement continueToGenerateElement = null;

    public ErzählerPageFactory(ErzählerFrame frame) {
        erzählerFrame = frame;
        pageElementFactory = new ErzählerPageElementFactory(erzählerFrame);
    }

    public Page generateStartPage(Page startPage, StartPageElementsDto pageElements) {
        //TODO listengenerierung so machen dass buttons aus dem dto verwendet werden
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

        return startPage;
    }

    public Page generatePlayerSetupPage(Page playerSetupPage, PlayerSetupPageElementsDto interactiveElementsDto, int numberOfPlayers) {
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

        return playerSetupPage;
    }

    public Page generateRollenSetupPage(Page rollenSetupPage, RolePageElementsDto interactiveElementsDto, int numberOfPlayers, int numberOfRoles, List<String> roleNames) {
        PageTable roleButtonTable = pageElementFactory.generateButtonTable(interactiveElementsDto.roleButtonTable, null);
        pageElementFactory.generateTableButtons(roleNames, interactiveElementsDto.roleButtons, roleButtonTable);

        JLabel counterJLabel = interactiveElementsDto.counterLabel;
        counterJLabel.setText(pageElementFactory.generateCounterLabelString(numberOfPlayers, numberOfRoles));
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

        rollenSetupPage.clearPage();

        rollenSetupPage.add(goNextButton);
        rollenSetupPage.add(goBackButton);
        rollenSetupPage.add(addAllRolesButton);
        rollenSetupPage.add(roleButtonTable);
        rollenSetupPage.add(counterLabel);
        rollenSetupPage.add(interactiveElementsDto.deleteTable);
        rollenSetupPage.add(interactiveElementsDto.labelTable);

        return rollenSetupPage;
    }

    public Page generateSpecifiyPage(Page playerSpecifyPage, SpecifyPageElementsDto pageElements) {
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

        //TODO überschriften für tables hinzufügen
        pageElements.deleteTable = pageElementFactory.generatePageTable(pageElements.deleteTable, titleLabel, null, columns, deleteButtonWidth,
                tableElementHeight, nameLabelWidth + hauptrolleLabelWidth + bonusrolleLabelWidth, spaceBetween,
                0, spaceBetween);

        pageElements.playerLabelTable = pageElementFactory.generatePageTable(pageElements.playerLabelTable, titleLabel, null, columns, nameLabelWidth,
                tableElementHeight, deleteButtonWidth + hauptrolleLabelWidth + bonusrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween, spaceBetween);

        pageElements.mainroleLabelTable = pageElementFactory.generatePageTable(pageElements.mainroleLabelTable, titleLabel, null, columns, hauptrolleLabelWidth,
                tableElementHeight, deleteButtonWidth + nameLabelWidth + bonusrolleLabelWidth, spaceBetween,
                deleteButtonWidth + spaceBetween * 2 + nameLabelWidth, spaceBetween);

        pageElements.bonusroleLabelTable = pageElementFactory.generatePageTable(pageElements.bonusroleLabelTable, titleLabel, null, columns, bonusrolleLabelWidth,
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
        playerSpecifyPage.add(pageElements.deleteTable);
        playerSpecifyPage.add(pageElements.playerLabelTable);
        playerSpecifyPage.add(pageElements.mainroleLabelTable);
        playerSpecifyPage.add(pageElements.bonusroleLabelTable);
        playerSpecifyPage.add(goNextButton);
        playerSpecifyPage.add(goBackButton);

        return playerSpecifyPage;
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

    public Page generateDefaultNightPage(Page nightPage, Statement statement, String title, boolean hatZurückButton) {
        PageElement nightLabel = pageElementFactory.generateNightLabel(statement.id);
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

    public Page generateDropdownPage(Page dropdownPage, Statement statement, DropdownOptions dropdownOptions) {
        return generateDropdownPage(dropdownPage, statement, dropdownOptions, false);
    }

    public Page generateDropdownPage(Page dropdownPage, Statement statement, DropdownOptions dropdownOptions, boolean hatZurückButton) {
        dropdownPage = generateDefaultNightPage(dropdownPage, statement, hatZurückButton);
        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownOptions.strings.toArray());
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
        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownOptions2.strings.toArray());
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

        DefaultComboBoxModel model = new DefaultComboBoxModel(livingPlayers.strings.toArray());
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

    public Page generateUmbringenPage(List<String> livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Umbringen");

        PageElement nameLabel = pageElementFactory.generateLabel(titleLabel, "Name");

        List<String> comboBoxOptions = livingPlayers;
        comboBoxOptions.add("");
        DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxOptions.toArray());
        erzählerFrame.comboBox1.setModel(model);
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

    public Page generatePriesterPage(List<String> livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Bürgen");

        PageElement priesterLabel = pageElementFactory.generateLabel(titleLabel, "Priester");

        DefaultComboBoxModel model = new DefaultComboBoxModel(livingPlayers.toArray());
        erzählerFrame.comboBox1.setModel(model);
        PageElement choosePriester = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, priesterLabel, 0, 0);

        PageElement spielerLabel = pageElementFactory.generateLabel(choosePriester, "Spieler");

        DefaultComboBoxModel model2 = new DefaultComboBoxModel(livingPlayers.toArray());
        erzählerFrame.comboBox2.setModel(model2);
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

    public Page generateRichterinPage(List<String> livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Verurteilen");

        PageElement richterinLabel = pageElementFactory.generateLabel(titleLabel, "Richterin");

        DefaultComboBoxModel model = new DefaultComboBoxModel(livingPlayers.toArray());
        erzählerFrame.comboBox1.setModel(model);
        PageElement chooseRichterin = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, richterinLabel, 0, 0);

        PageElement spielerLabel = pageElementFactory.generateLabel(chooseRichterin, "Spieler");

        DefaultComboBoxModel model2 = new DefaultComboBoxModel(livingPlayers.toArray());
        erzählerFrame.comboBox2.setModel(model2);
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

    public Page generateTortenPage(Page tortenPage, OneDropdownPageElementsDto pageElements, DropdownOptions livingPlayers) {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Torte");

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 2;

        DefaultComboBoxModel model = new DefaultComboBoxModel(livingPlayers.strings.toArray());
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

        return tortenPage;
    }

    private PageElement getContinueToGeneratePagePoint(Page page) {
        return page.pageElements.get(continueToGeneratePagePoint);
    }

    public Page generateIrrlichtDropdownPage(Page irrlichtPage, OneDropdownPageElementsDto pageElements, Statement statement, DropdownOptions dropdownStrings) {
        irrlichtPage = generateDefaultNightPage(irrlichtPage, statement, false);
        DefaultComboBoxModel model = new DefaultComboBoxModel(dropdownStrings.strings.toArray());
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

        return irrlichtPage;
    }
}
