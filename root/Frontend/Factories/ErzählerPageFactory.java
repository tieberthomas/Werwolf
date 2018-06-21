package root.Frontend.Factories;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Phases.Statement;
import root.Phases.StatementIndie;
import root.Phases.Tag;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrolle;
import root.Spieler;
import root.mechanics.Torte;

import javax.swing.*;
import java.util.ArrayList;

public class ErzählerPageFactory {
    ErzählerFrame erzählerFrame;
    public ErzählerPageElementFactory pageElementFactory;
    static int continueToGeneratePagePoint = 0;

    public ErzählerPageFactory(ErzählerFrame frame) {
        erzählerFrame = frame;
        pageElementFactory = new ErzählerPageElementFactory(erzählerFrame);
    }

    public Page generateStartPage() {
        PageElement werwolfIcon = pageElementFactory.generateCenteredImageLabel(ResourcePath.WÖLFE_ICON, 2, 60);

        erzählerFrame.startJButton = new JButton("Neues Spiel");
        PageElement startButton = pageElementFactory.generateCenteredBigButton(erzählerFrame.startJButton, werwolfIcon);
        erzählerFrame.goNextButtons.add(erzählerFrame.startJButton);

        erzählerFrame.loadLastGameJButton = new JButton("Letzes Spiel laden");
        PageElement loadLastGame = pageElementFactory.generateCenteredBigButton(erzählerFrame.loadLastGameJButton, startButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.loadLastGameJButton);


        Page startPage = new Page(0,0);

        startPage.add(werwolfIcon);
        startPage.add(startButton);
        startPage.add(loadLastGame);

        erzählerFrame.setupPages.add(startPage);

        return startPage;
    }

    public Page generatePlayerSetupPage() {
        PageElement nameLabel = pageElementFactory.generateLabel(null, "Name");

        PageElement addPlayerTxtField = pageElementFactory.generateAddPlayerTxtField(nameLabel);

        erzählerFrame.addPlayerButton = new JButton("Hinzufügen");
        PageElement addPlayerButton = pageElementFactory.generateSmallButton(erzählerFrame.addPlayerButton, addPlayerTxtField);

        erzählerFrame.numberOfPlayersJLabel = new JLabel(pageElementFactory.generateNumberOfPLayersLabelTitle());
        PageElement numberOfPlayersLabel = pageElementFactory.generateCounterLabel(erzählerFrame.numberOfPlayersJLabel, addPlayerTxtField);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 2;

        erzählerFrame.deletePlayerTable = pageElementFactory.generatePageTable(numberOfPlayersLabel,
                columns, deleteButtonWidth, tableElementHeight,nameLabelWidth,spaceBetween,0,spaceBetween);

        erzählerFrame.playerTable = pageElementFactory.generatePageTable(numberOfPlayersLabel,
                columns, nameLabelWidth, tableElementHeight,deleteButtonWidth,spaceBetween,deleteButtonWidth+spaceBetween,spaceBetween);

        erzählerFrame.playerGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.playerGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.playerGoBackJButton);

        erzählerFrame.playerGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.playerGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.playerGoNextJButton);


        Page playerSetupPage = new Page();

        playerSetupPage.add(nameLabel);
        playerSetupPage.add(addPlayerButton);
        playerSetupPage.add(addPlayerTxtField);
        playerSetupPage.add(goBackButton);
        playerSetupPage.add(goNextButton);
        playerSetupPage.add(numberOfPlayersLabel);
        playerSetupPage.addTable(erzählerFrame.deletePlayerTable);
        playerSetupPage.addTable(erzählerFrame.playerTable);

        erzählerFrame.setupPages.add(playerSetupPage);

        return playerSetupPage;
    }

    public Page generateMainRoleSetupPage() {
        PageTable mainRoleButtonTable = pageElementFactory.generateButtonTable(null);
        pageElementFactory.generateTableButtons(Hauptrolle.getMainRoleNames(), erzählerFrame.mainRoleButtons, mainRoleButtonTable);

        erzählerFrame.mainRoleCounterJLabel = new JLabel(pageElementFactory.generateMainRoleCounterLabelTitle());
        PageElement counterLabel = pageElementFactory.generateCounterLabel(erzählerFrame.mainRoleCounterJLabel, mainRoleButtonTable);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columnWidth = deleteButtonWidth + nameLabelWidth + spaceBetween;
        int columns = pageElementFactory.calculateColumns(columnWidth);

        erzählerFrame.deleteMainRoleTable = pageElementFactory.generatePageTable(counterLabel,
                columns, deleteButtonWidth, tableElementHeight,nameLabelWidth,spaceBetween,0,spaceBetween);

        erzählerFrame.mainRoleLabelTable = pageElementFactory.generatePageTable(counterLabel,
                columns, nameLabelWidth, tableElementHeight,deleteButtonWidth,spaceBetween,deleteButtonWidth+spaceBetween,spaceBetween);

        erzählerFrame.mainRoleGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.mainRoleGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.mainRoleGoBackJButton);

        erzählerFrame.mainRoleGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.mainRoleGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.mainRoleGoNextJButton);

        Page mainRoleSetupPage = new Page();

        mainRoleSetupPage.add(goNextButton);
        mainRoleSetupPage.add(goBackButton);
        mainRoleSetupPage.addTable(mainRoleButtonTable);
        mainRoleSetupPage.add(counterLabel);
        mainRoleSetupPage.addTable(erzählerFrame.deleteMainRoleTable);
        mainRoleSetupPage.addTable(erzählerFrame.mainRoleLabelTable);

        erzählerFrame.setupPages.add(mainRoleSetupPage);

        return mainRoleSetupPage;
    }

    public Page generateSecondaryRoleSetupPage() {
        PageTable secondaryRoleButtonTable = pageElementFactory.generateButtonTable(null);
        pageElementFactory.generateTableButtons(Nebenrolle.getSecondaryRoleNames(), erzählerFrame.secondaryRoleButtons, secondaryRoleButtonTable);

        erzählerFrame.secondaryRoleCounterJLabel = new JLabel(pageElementFactory.generateSecondaryRoleCounterLabelTitle());
        PageElement counterLabel = pageElementFactory.generateCounterLabel(erzählerFrame.secondaryRoleCounterJLabel, secondaryRoleButtonTable);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columnWidth = deleteButtonWidth + nameLabelWidth + spaceBetween;
        int columns = pageElementFactory.calculateColumns(columnWidth);

        erzählerFrame.deleteSecondaryRoleTable = pageElementFactory.generatePageTable(counterLabel, columns, deleteButtonWidth,
                tableElementHeight,nameLabelWidth,spaceBetween,0,spaceBetween);

        erzählerFrame.secondaryRoleLabelTable = pageElementFactory.generatePageTable(counterLabel, columns, nameLabelWidth,
                tableElementHeight,deleteButtonWidth,spaceBetween,deleteButtonWidth+spaceBetween,spaceBetween);

        erzählerFrame.secondaryRoleGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.secondaryRoleGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.secondaryRoleGoBackJButton);

        erzählerFrame.secondaryRoleGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.secondaryRoleGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.secondaryRoleGoNextJButton);

        Page secondaryRoleSetupPage = new Page();

        secondaryRoleSetupPage.add(goBackButton);
        secondaryRoleSetupPage.add(goNextButton);
        secondaryRoleSetupPage.addTable(secondaryRoleButtonTable);
        secondaryRoleSetupPage.add(counterLabel);
        secondaryRoleSetupPage.addTable(erzählerFrame.deleteSecondaryRoleTable);
        secondaryRoleSetupPage.addTable(erzählerFrame.secondaryRoleLabelTable);

        erzählerFrame.setupPages.add(secondaryRoleSetupPage);

        return secondaryRoleSetupPage;
    }

    public Page generatePlayerSpecifiyPage(){
        String title = "Wählen Sie für diesen Spieler Haupt- und Nebenrolle.";
        PageElement titleLabel = pageElementFactory.generateLabel(null, title);
        titleLabel.width = 330;

        PageElement playerLabel = pageElementFactory.generateLabel(titleLabel, "Spieler");
        erzählerFrame.comboBox1 = new JComboBox(erzählerFrame.playersLeft.toArray());
        PageElement choosePlayer = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, playerLabel, 0,0);

        PageElement mainRoleLabel = pageElementFactory.generateLabel(choosePlayer, "Hauptrolle");
        erzählerFrame.comboBox2 = new JComboBox(erzählerFrame.mainRolesLeft.toArray());
        PageElement chooseMainRole = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, mainRoleLabel, 0,0);

        PageElement secondaryRoleLabel = pageElementFactory.generateLabel(chooseMainRole, "Nebenrolle");
        erzählerFrame.comboBox3 = new JComboBox(erzählerFrame.secondaryRolesLeft.toArray());
        PageElement chooseSecondaryRole = pageElementFactory.generateDropdown(erzählerFrame.comboBox3,
                null, secondaryRoleLabel, 0, 0);

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int mainRoleLabelWidth = 150;
        int secondaryRoleLabelWidth = 150;
        int spaceBetween = 5;
        int columns = 1;

        erzählerFrame.deleteSpecifyPlayerTable = pageElementFactory.generatePageTable(titleLabel, null, columns, deleteButtonWidth,
                tableElementHeight,nameLabelWidth+mainRoleLabelWidth+secondaryRoleLabelWidth,spaceBetween,
                0,spaceBetween);

        erzählerFrame.playerSpecifyTable = pageElementFactory.generatePageTable(titleLabel, null, columns, nameLabelWidth,
                tableElementHeight,deleteButtonWidth+mainRoleLabelWidth+secondaryRoleLabelWidth,spaceBetween,
                deleteButtonWidth+spaceBetween,spaceBetween);

        erzählerFrame.mainRoleSpecifyTable = pageElementFactory.generatePageTable(titleLabel, null, columns, mainRoleLabelWidth,
                tableElementHeight,deleteButtonWidth+nameLabelWidth+secondaryRoleLabelWidth,spaceBetween,
                deleteButtonWidth+spaceBetween*2+nameLabelWidth,spaceBetween);

        erzählerFrame.secondaryRoleSpecifyTable = pageElementFactory.generatePageTable(titleLabel, null, columns, secondaryRoleLabelWidth,
                tableElementHeight,deleteButtonWidth+nameLabelWidth+mainRoleLabelWidth,spaceBetween,
                deleteButtonWidth+spaceBetween*3+nameLabelWidth+mainRoleLabelWidth,spaceBetween);

        erzählerFrame.playerSpecifyGoNextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.playerSpecifyGoNextJButton);
        erzählerFrame.goNextButtons.add(erzählerFrame.playerSpecifyGoNextJButton);

        erzählerFrame.playerSpecifyGoBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.playerSpecifyGoBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.playerSpecifyGoBackJButton);

        Page playerSpecifyPage = new Page();

        playerSpecifyPage.add(titleLabel);
        playerSpecifyPage.add(playerLabel);
        playerSpecifyPage.add(choosePlayer);
        playerSpecifyPage.add(mainRoleLabel);
        playerSpecifyPage.add(chooseMainRole);
        playerSpecifyPage.add(secondaryRoleLabel);
        playerSpecifyPage.add(chooseSecondaryRole);
        playerSpecifyPage.addTable(erzählerFrame.playerSpecifyTable);
        playerSpecifyPage.addTable(erzählerFrame.mainRoleSpecifyTable);
        playerSpecifyPage.addTable(erzählerFrame.secondaryRoleSpecifyTable);
        playerSpecifyPage.addTable(erzählerFrame.deleteSpecifyPlayerTable);
        playerSpecifyPage.add(goNextButton);
        playerSpecifyPage.add(goBackButton);

        erzählerFrame.setupPages.add(playerSpecifyPage);

        return playerSpecifyPage;
    }

    public Page generateDefaultNightPage(Statement statement){
        PageElement nightLabel = pageElementFactory.generateNightLabel(statement.beschreibung);
        PageElement titleLabel = pageElementFactory.generateTitleLabel(nightLabel, statement.titel);
        erzählerFrame.nextJButton = new JButton();
        PageElement goNextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);
        Page defaultNightPage = new Page();

        defaultNightPage.add(nightLabel);
        defaultNightPage.add(titleLabel);
        defaultNightPage.add(goNextButton);

        continueToGeneratePagePoint = defaultNightPage.pageElements.indexOf(titleLabel);

        return defaultNightPage;
    }

    public Page generateDeactivatedPage(Statement statement){
        return generateIconPicturePage(statement, ResourcePath.DEAKTIVIERT);
    }

    public Page generateAufgebrauchtPage(Statement statement){
        return generateIconPicturePage(statement, ResourcePath.AUFGEBRAUCHT);
    }

    public Page generateIconPicturePage(Statement statement, String imagePath) {
        Page picturePage = generateDefaultNightPage(statement);
        PageElement deactivatedIcon = pageElementFactory.generateIcon(getContinueToGeneratePagePoint(picturePage), imagePath);

        picturePage.add(deactivatedIcon);

        return picturePage;
    }

    public Page generateCardPicturePage(Statement statement, String imagePath) {
        Page picturePage = generateDefaultNightPage(statement);
        PageElement deactivatedIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(picturePage), imagePath);

        picturePage.add(deactivatedIcon);

        return picturePage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions){
        Page dropdownPage = generateDefaultNightPage(statement);
        erzählerFrame.comboBox1 = new JComboBox(dropdownOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer1);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(choosePlayer1);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, String imagePath){
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2){
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions);
        erzählerFrame.comboBox2 = new JComboBox(dropdownOptions2.toArray());
        PageElement choosePlayer2 = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer2);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(choosePlayer2);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2, String imagePath){
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions, dropdownOptions2);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2, ArrayList<String> dropdownOptions3){
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions, dropdownOptions2);
        erzählerFrame.comboBox3 = new JComboBox(dropdownOptions3.toArray());
        PageElement choosePlayer3 = pageElementFactory.generateDropdown(erzählerFrame.comboBox3,
                null, getContinueToGeneratePagePoint(dropdownPage));

        dropdownPage.add(choosePlayer3);

        return dropdownPage;
    }

    public Page generateDropdownPage(Statement statement, ArrayList<String> dropdownOptions, ArrayList<String> dropdownOptions2, ArrayList<String> dropdownOptions3, String imagePath){
        Page dropdownPage = generateDropdownPage(statement, dropdownOptions, dropdownOptions2, dropdownOptions3);
        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(dropdownPage), imagePath);

        dropdownPage.add(modeIcon);

        continueToGeneratePagePoint = dropdownPage.pageElements.indexOf(modeIcon);

        return dropdownPage;
    }

    public Page generateListPage(Statement statement, ArrayList<String> stringsToDisplay) {
        Page listPage = generateDefaultNightPage(statement);

        if(stringsToDisplay.size() > 0) {
            PageElement label = pageElementFactory.generateLabel(getContinueToGeneratePagePoint(listPage), stringsToDisplay.get(0));
            listPage.add(label);
            int i = 0;
            for (String string : stringsToDisplay) {
                if (i != 0) {
                    label = pageElementFactory.generateLabel(label, string);
                    listPage.add(label);

                    if (i == (stringsToDisplay.size() - 1)) {
                        continueToGeneratePagePoint = listPage.pageElements.indexOf(label);
                    }
                }

                i++;
            }
        }

        return listPage;
    }

    public Page generateListPage(Statement statement, ArrayList<String> stringsToDisplay, String imagePath) {
        Page listPage = generateListPage(statement, stringsToDisplay);

        PageElement modeIcon = pageElementFactory.generateSmallIcon(getContinueToGeneratePagePoint(listPage), imagePath);

        listPage.add(modeIcon);

        continueToGeneratePagePoint = listPage.pageElements.indexOf(modeIcon);

        return listPage;
    }

    public Page generateDefaultDayPage() {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, Tag.dayTitle);
        ArrayList<String> dropdownOptions = Spieler.getLivigPlayerOrNoneStrings();
        erzählerFrame.comboBox1 = new JComboBox(dropdownOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, titleLabel);

        erzählerFrame.nextJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);

        erzählerFrame.nachzüglerJButton = new JButton();
        PageElement nachzüglerButton = pageElementFactory.generateLowestButton(erzählerFrame.nachzüglerJButton, "Nachzügler", false);

        erzählerFrame.umbringenJButton = new JButton();
        PageElement umbringenButton = pageElementFactory.generateLowestButton(erzählerFrame.umbringenJButton, "Umbringen", false, 1);
        Page tagPage = new Page();

        tagPage.add(titleLabel);
        tagPage.add(choosePlayer1);
        tagPage.add(nextButton);
        tagPage.add(nachzüglerButton);
        tagPage.add(umbringenButton);

        return tagPage;
    }

    public Page generateAnnounceVictimsDayPage(String spieler1) {
        Page tagPage = generateDefaultDayPage();

        JLabel label = pageElementFactory.generateBigJLabel(new JLabel(spieler1));

        PageElement nameLabel = pageElementFactory.generateLeftCenteredLabel(new JLabel(spieler1));
        PageElement deadImage = pageElementFactory.generateRightCenteredImage(ResourcePath.TOT);

        tagPage.add(nameLabel);
        tagPage.add(deadImage);

        return tagPage;
    }

    public Page generateNachzüglerPage() {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Nachzügler");

        PageElement nameLabel = pageElementFactory.generateLabel(titleLabel, "Name");

        PageElement nameTxtField = pageElementFactory.generateAddPlayerTxtField(nameLabel);

        PageElement hauptRolleLabel = pageElementFactory.generateLabel(nameTxtField, "Hauptrolle");

        ArrayList<String> comboBoxOptions = Hauptrolle.getStillAvailableMainRoleNames();
        erzählerFrame.comboBox1 = new JComboBox(comboBoxOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, hauptRolleLabel,0,0);

        PageElement nebenRolleLabel = pageElementFactory.generateLabel(choosePlayer1, "Hauptrolle");

        ArrayList<String> comboBox2Options = Nebenrolle.getStillAvailableSecondaryRoleNames();
        erzählerFrame.comboBox2 = new JComboBox(comboBox2Options.toArray());
        PageElement choosePlayer2 = pageElementFactory.generateDropdown(erzählerFrame.comboBox2,
                null, nebenRolleLabel,0,0);


        erzählerFrame.nachzüglerJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.nachzüglerJButton);

        erzählerFrame.goBackJButton = new JButton();
        PageElement goBackButton = pageElementFactory.generateLowestButton(erzählerFrame.goBackJButton, "Zurück", false);
        erzählerFrame.goBackButtons.add(erzählerFrame.goBackJButton);

        Page nachtzüglerPage = new Page();

        nachtzüglerPage.add(titleLabel);
        nachtzüglerPage.add(nameLabel);
        nachtzüglerPage.add(nameTxtField);
        nachtzüglerPage.add(hauptRolleLabel);
        nachtzüglerPage.add(choosePlayer1);
        nachtzüglerPage.add(nebenRolleLabel);
        nachtzüglerPage.add(choosePlayer2);
        nachtzüglerPage.add(nextButton);
        nachtzüglerPage.add(goBackButton);

        return nachtzüglerPage;
    }

    public Page generateUmbringenPage() {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Umbringen");

        PageElement nameLabel = pageElementFactory.generateLabel(titleLabel, "Name");

        ArrayList<String> comboBoxOptions = Spieler.getLivigPlayerStrings();
        comboBoxOptions.add("");
        erzählerFrame.comboBox1 = new JComboBox(comboBoxOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, nameLabel,0,0);

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

    public Page generateTortenPage() {
        PageElement titleLabel = pageElementFactory.generateTitleLabel(null, "Torte");

        int tableElementHeight = 25;
        int deleteButtonWidth = 40;
        int nameLabelWidth = 150;
        int spaceBetween = 10;
        int columns = 2;

        ArrayList<String> comboBoxOptions = Spieler.getLivigPlayerStrings();
        erzählerFrame.comboBox1 = new JComboBox(comboBoxOptions.toArray());
        PageElement choosePlayer1 = pageElementFactory.generateDropdown(erzählerFrame.comboBox1,
                null, titleLabel,0,0);

        erzählerFrame.addPlayerTortenButton = new JButton("Hinzufügen");
        PageElement addPlayerButton = pageElementFactory.generateSmallButton(erzählerFrame.addPlayerTortenButton, choosePlayer1);

        erzählerFrame.deletePlayerTable = pageElementFactory.generatePageTable(choosePlayer1,
                columns, deleteButtonWidth, tableElementHeight,nameLabelWidth,spaceBetween,0,spaceBetween);

        erzählerFrame.playerTable = pageElementFactory.generatePageTable(choosePlayer1,
                columns, nameLabelWidth, tableElementHeight,deleteButtonWidth,spaceBetween,deleteButtonWidth+spaceBetween,spaceBetween);

        erzählerFrame.nextJButton = new JButton();
        PageElement nextButton = pageElementFactory.generateLowestButton(erzählerFrame.nextJButton);

        Torte.tortenEsser = new ArrayList<>();

        Page tortenPage = new Page();

        tortenPage.add(titleLabel);
        tortenPage.add(choosePlayer1);
        tortenPage.add(addPlayerButton);
        tortenPage.addTable(erzählerFrame.deletePlayerTable);
        tortenPage.addTable(erzählerFrame.playerTable);
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
