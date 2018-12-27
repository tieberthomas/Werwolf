package root.Frontend;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Frame.SpielerFrameMode;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.Page.Page;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.Utils.PageRefresher.InteractivePages.IrrlichtPage;
import root.Frontend.Utils.PageRefresher.InteractivePages.TortenPage;
import root.Logic.Game;
import root.Logic.Persona.Rollen.Bonusrollen.Schnüffler;
import root.Logic.Persona.Rollen.Constants.RawInformation;
import root.Logic.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.AusDemSpiel;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Tot;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Logic.Phases.Statement.Statement;
import root.Logic.Phases.Winner;
import root.Logic.Spieler;

import java.util.ArrayList;
import java.util.List;

public class FrontendControl {
    public static ErzählerFrame erzählerFrame;
    public static SpielerFrame spielerFrame;
    public static ÜbersichtsFrame übersichtsFrame;

    public FrontendControlType typeOfContent;
    public String title;
    public DropdownOptions dropdownOptions;
    public List<String> displayedStrings;
    public List<SchnüfflerInformation> informationen;
    public String imagePath;
    public String imagePath2;
    public String note;

    public boolean hatZurückButton = false;

    public FrontendControl() {
        this.typeOfContent = FrontendControlType.SKIP;
    }

    public FrontendControl(String title) {
        this.typeOfContent = FrontendControlType.TITLE;
        this.title = title;
    }

    public FrontendControl(String title, String listString) {
        this.typeOfContent = FrontendControlType.LIST;
        this.title = title;
        displayedStrings = new ArrayList<>();
        displayedStrings.add(listString);
    }

    public FrontendControl(FrontendControlType typeOfContent, String title, List<String> strings) {
        this(typeOfContent, strings);
        this.title = title;
    }

    public FrontendControl(DropdownOptions dropdownOptions) {
        this.typeOfContent = FrontendControlType.DROPDOWN;
        this.dropdownOptions = dropdownOptions;
    }

    public FrontendControl(FrontendControlType typeOfContent, List<String> strings) {
        this.typeOfContent = typeOfContent;
        if (typeOfContent.isDropdown()) {
            dropdownOptions = (DropdownOptions) strings;
        } else {
            displayedStrings = strings;
        }
    }

    public FrontendControl(String title, DropdownOptions dropdownOptions) {
        this.typeOfContent = FrontendControlType.DROPDOWN;
        this.title = title;
        this.dropdownOptions = dropdownOptions;
    }

    public FrontendControl(FrontendControlType typeOfContent, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.imagePath = imagePath;
    }

    public FrontendControl(FrontendControlType typeOfContent, String title, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.imagePath = imagePath;
    }

    public FrontendControl(Zeigekarte zeigekarte) {
        this.typeOfContent = FrontendControlType.IMAGE;
        this.title = zeigekarte.title;
        this.imagePath = zeigekarte.imagePath;
    }

    public FrontendControl(FrontendControlType typeOfContent, DropdownOptions dropdownOptions, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.dropdownOptions = dropdownOptions;
        this.imagePath = imagePath;
    }

    public FrontendControl(FrontendControlType typeOfContent, String title, List<String> displayedStrings, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.displayedStrings = displayedStrings;
        this.imagePath = imagePath;
    }

    public FrontendControl(FrontendControlType typeOfContent, String title, String note, List<String> displayedStrings) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.note = note;
        this.displayedStrings = displayedStrings;
    }

    public FrontendControl(List<SchnüfflerInformation> informationen, String title) {
        this.typeOfContent = FrontendControlType.SCHNÜFFLER_INFO;
        this.title = title;
        this.informationen = informationen;
    }

    public FrontendControl(String title, DropdownOptions dropdownOptions, List<String> displayedStrings) {
        this.typeOfContent = FrontendControlType.DROPDOWN_SEPARATED_LIST;
        this.title = title;
        this.dropdownOptions = dropdownOptions;
        this.displayedStrings = displayedStrings;
    }

    public FrontendControl(String title, String imagePath1, String imagePath2, List<String> displayedStrings) {
        this.title = title;
        this.imagePath = imagePath1;
        this.imagePath2 = imagePath2;
        this.displayedStrings = displayedStrings;
        typeOfContent = FrontendControlType.TWO_IMAGES;
    }

    public static void erzählerDefaultNightPage(Statement statement) {
        Page nightPage = erzählerFrame.pageFactory.generateDefaultNightPage(new Page(), statement);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerTitlePage(String title) {
        Page nightPage = spielerFrame.pageFactory.generateTitlePage(title);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void showDayPage() {
        FrontendControl.erzählerDefaultDayPage();
        FrontendControl.spielerDayPage();
    }

    public static void erzählerDefaultDayPage() {
        Page dayPage = erzählerFrame.pageFactory.generateDayPage(Game.game.getSpielerDropdownOptions(true));
        erzählerFrame.buildScreenFromPage(dayPage);
    }

    public static void spielerDayPage() {
        spielerFrame.generateDayPage();
    }

    public static void erzählerTortenPage() {
        erzählerFrame.currentInteractivePage = new TortenPage(new DropdownOptions(Game.game.getLivingSpielerStrings()));
    }

    public static List<String> getTortenesser() {
        return TortenPage.tortenesser;
    }

    public static void announceOpferPage(Spieler spieler) {
        erzählerAnnounceOpferPage(spieler);
        spielerAnnounceOpferPage(spieler);
        refreshÜbersichtsFrame();
    }

    public static void announceOpferPage(Spieler spieler, String imagePath) {
        FrontendControl.erzählerAnnounceOpferPage(spieler, imagePath);
        FrontendControl.spielerCardPicturePage(spieler.name, imagePath);
        refreshÜbersichtsFrame();

    }

    public static void erzählerAnnounceOpferPage(Spieler spieler) {
        Page dayPage = erzählerFrame.pageFactory.generateAnnounceOpferDayPage(spieler.name, new Tot().imagePath);
        erzählerFrame.buildScreenFromPage(dayPage);
    }

    public static void erzählerAnnounceOpferPage(Spieler spieler, String imagepath) {
        Page dayPage = erzählerFrame.pageFactory.generateAnnounceOpferDayPage(spieler.name, imagepath);
        erzählerFrame.buildScreenFromPage(dayPage);
    }

    public static void spielerAnnounceOpferPage(Spieler spieler) {
        String hauptRolleImagePath = spieler.hauptrolle.imagePath;
        String bonusRolleImagePath = spieler.bonusrolle.imagePath;
        if (GrafVladimir.verschleierterSpieler != null) {
            if (spieler.equals(GrafVladimir.verschleierterSpieler)) {
                AusDemSpiel ausDemSpiel = new AusDemSpiel();
                hauptRolleImagePath = ausDemSpiel.imagePath;
                bonusRolleImagePath = ausDemSpiel.imagePath;
            }
        }
        spielerTwoImagePage(spieler.name, hauptRolleImagePath, bonusRolleImagePath);
    }

    public static void spielerTwoImagePage(String title, String imagePath1, String imagePath2) {
        Page dayPage = spielerFrame.pageFactory.generateTwoImagePage(title, imagePath1, imagePath2);
        spielerFrame.buildScreenFromPage(dayPage);
    }

    public static void erzählerEndScreenPage(Winner winner) {
        Page nightPage = spielerFrame.pageFactory.generateEndScreenPage(winner);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerEndScreenPage(Winner winner) {
        Page nightPage = spielerFrame.pageFactory.generateEndScreenPage(winner);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, DropdownOptions dropdownOptions) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions, false);
    }

    public static void erzählerDropdownPage(Statement statement, DropdownOptions dropdownOptions, boolean hatZurückButton) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(new Page(), statement, dropdownOptions, hatZurückButton);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, DropdownOptions dropdownOptions1, DropdownOptions dropdownOptions2) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(new Page(), statement, dropdownOptions1, dropdownOptions2);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, DropdownOptions dropdownOptions, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(new Page(), statement, dropdownOptions, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, DropdownOptions dropdownOptions1, DropdownOptions dropdownOptions2, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(new Page(), statement, dropdownOptions1, dropdownOptions2, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerDropdownPage(String title, int numberOfDropdowns) {
        spielerFrame.dropDownPage = spielerFrame.pageFactory.generateDropdownPage(title, numberOfDropdowns);
        spielerFrame.buildScreenFromPage(spielerFrame.dropDownPage);
    }

    public static void spielerDropdownListPage(String title, List<String> dropdownOptions) {
        spielerFrame.dropDownPage = spielerFrame.pageFactory.generateListMirrorPage(title, dropdownOptions);
        spielerFrame.buildScreenFromPage(spielerFrame.dropDownPage);
    }

    public static void spielerDropdownMirrorImagePage(String title, String imagepath) {
        spielerFrame.dropDownPage = spielerFrame.pageFactory.generateDropdownMirrorImagePage(title, imagepath);
        spielerFrame.buildScreenFromPage(spielerFrame.dropDownPage);
    }

    public static void erzählerListPage(Statement statement, String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        erzählerListPage(statement, list);
    }

    public static void erzählerListPage(Statement statement, List<String> strings) {
        erzählerListPage(statement, statement.title, strings, false);
    }

    public static void erzählerListPage(Statement statement, String title, List<String> strings) {
        erzählerListPage(statement, title, strings, false);
    }

    public static void erzählerListPage(Statement statement, String title, List<String> strings, boolean hatZurückButton) {
        Page nightPage = erzählerFrame.pageFactory.generateListPage(new Page(), statement, title, strings, hatZurückButton);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerListPage(Statement statement, List<String> strings, String imagePath) {
        erzählerListPage(statement, statement.title, strings, imagePath);
    }

    public static void erzählerListPage(Statement statement, String title, List<String> strings, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateListPage(new Page(), statement, title, strings, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerListPage(String title, List<String> strings) {
        Page nightPage = spielerFrame.pageFactory.generateListPage(title, strings);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerListPageWithNote(String title, List<String> strings, String note) {
        Page nightPage = spielerFrame.pageFactory.generateListPageWithNote(title, strings, note);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerIconPicturePage(Statement statement, String imagePath) {
        erzählerIconPicturePage(statement, statement.title, imagePath);
    }

    public static void erzählerIconPicturePage(Statement statement, String title, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateIconPicturePage(new Page(), statement, title, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerIconPicturePage(String title, String imagePath) {
        Page nightPage = spielerFrame.pageFactory.generateStaticImagePage(title, imagePath, true);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void showZeigekarteOnSpielerScreen(Zeigekarte zeigekarte) {
        Page nightPage = spielerFrame.pageFactory.generateStaticImagePage(zeigekarte.title, zeigekarte.imagePath, true);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerCardPicturePage(Statement statement, String title, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateCardPicturePage(new Page(), statement, title, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerCardPicturePage(String title, String imagePath) {
        Page nightPage = spielerFrame.pageFactory.generateStaticImagePage(title, imagePath, false);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerSchnüfflerInfoPage(List<SchnüfflerInformation> informationen) {
        List<RawInformation> rawInformationen = convertToRawInformation(informationen);

        Page nightPage = spielerFrame.pageFactory.generateSchnüfflerInformationPage(rawInformationen, Schnüffler.MAX_ANZAHL_AN_INFORMATIONEN);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    private static List<RawInformation> convertToRawInformation(List<SchnüfflerInformation> informationen) {
        List<RawInformation> rawInformationen = new ArrayList<>();

        for (SchnüfflerInformation information : informationen) {
            RawInformation rawInformation = RawInformation.convertToRawInformation(information);
            rawInformationen.add(rawInformation);
        }

        return rawInformationen;
    }

    public static void refreshÜbersichtsFrame() {
        übersichtsFrame.refresh();
    }

    public static void regenerateAndRefreshÜbersichtsFrame() {
        übersichtsFrame.regenerateAndRefresh();
    }

    public static void irrlichtDropdownPage(Statement statement, DropdownOptions dropdownStrings) {
        erzählerFrame.currentInteractivePage = new IrrlichtPage(statement, dropdownStrings);
    }

    public static List<String> getFlackerndeIrrlichter() {
        return IrrlichtPage.flackerndeIrrlichter;
    }

    public static void combobox1Changed(String nexText) {
        spielerFrame.combobox1Changed(nexText);
    }

    public static void combobox2Changed(String nexText) {
        spielerFrame.combobox2Changed(nexText);
    }

    public static void ereaseSpielerFrame() {
        spielerFrame.mode = SpielerFrameMode.blank;
        spielerFrame.buildScreenFromPage(spielerFrame.blankPage);
    }
}
