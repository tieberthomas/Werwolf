package root.Controller;

import root.Controller.FrontendObject.FrontendObject;
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
import root.Logic.Persona.Rollen.Constants.Zeigekarten.*;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Logic.Phases.Statement.Statement;
import root.Logic.Phases.Winner;
import root.Logic.Spieler;
import root.ResourceManagement.SoundManager;

import java.util.ArrayList;
import java.util.List;

public abstract class FrontendControl {
    public static ErzählerFrame erzählerFrame;
    public static SpielerFrame spielerFrame;
    public static ÜbersichtsFrame übersichtsFrame;

    public static Object lock;

    public static String showFrontendObject(Statement statement, FrontendObject frontendObject) {
        if (frontendObject.title == null) {
            frontendObject.title = statement.title;
        }

        switch (statement.state) {
            case NORMAL:
                switch (frontendObject.typeOfContent) {
                    case TITLE:
                        showTitle(statement, frontendObject.title);
                        break;

                    case DROPDOWN:
                        showDropdown(statement, frontendObject.title, frontendObject.dropdownOptions);
                        return erzählerFrame.chosenOption1;

                    case DROPDOWN_LIST:
                        showDropdownList(statement, frontendObject.title, frontendObject.dropdownOptions, frontendObject.hatZurückButton);
                        return erzählerFrame.chosenOption1;

                    case DROPDOWN_IMAGE:
                        showDropdownShowImage(statement, frontendObject.title, frontendObject.dropdownOptions, frontendObject.imagePath);
                        return erzählerFrame.chosenOption1;

                    case LIST:
                        showList(statement, frontendObject.title, frontendObject.displayedStrings, frontendObject.hatZurückButton);
                        break;

                    case LIST_WITH_NOTE:
                        showListWithNote(statement, frontendObject.title, frontendObject.displayedStrings, frontendObject.hatZurückButton, frontendObject.note);
                        break;

                    case IMAGE:
                        showImage(statement, frontendObject.title, frontendObject.imagePath);
                        break;

                    case CARD:
                        showCard(statement, frontendObject.title, frontendObject.imagePath);
                        break;

                    case SCHNÜFFLER_INFO:
                        showSchnüfflerInfo(statement, frontendObject.informationen);
                        break;

                    case IRRLICHT_DROPDOWN:
                        showIrrlichtDropdown(statement, frontendObject.title, frontendObject.dropdownOptions);
                        break;

                    case TWO_IMAGES:
                        showTwoImages(statement, frontendObject.title, frontendObject.imagePath, frontendObject.imagePath2, frontendObject.displayedStrings, frontendObject.hatZurückButton);
                        break;

                }
                break;

            case AUFGEBRAUCHT:
                showAufgebrauchtPages(statement, frontendObject);
                break;

            case DEAKTIV:
                showDeaktivPages(statement, frontendObject);
                break;

            case DEAD:
                showTotPages(statement, frontendObject);
                break;

            case NOT_IN_GAME:
                showAusDemSpielPages(statement, frontendObject);
                break;
        }

        return null;
    }

    private static void showTwoImages(Statement statement, String title, String imagePath, String imagePath2, List<String> displayedStrings, boolean hatZurückButton) {
        erzählerListPage(statement, title, displayedStrings, hatZurückButton);
        spielerTwoImagePage(title, imagePath, imagePath2);

        waitForAnswer();
    }

    private static void showIrrlichtDropdown(Statement statement, String title, DropdownOptions dropdownStrings) {
        irrlichtDropdownPage(statement, dropdownStrings);
        spielerTitlePage(title);

        waitForAnswer();
    }

    private static void showDropdownShowImage(Statement statement, String title, DropdownOptions strings, String imagePath) {
        erzählerDropdownPage(statement, strings);
        spielerDropdownMirrorImagePage(title, imagePath);

        waitForAnswer();
    }

    public static void showDropdownPage(Statement statement, DropdownOptions dropdownOptions1, DropdownOptions dropdownOptions2) {
        switch (statement.state) {
            case NORMAL:
                erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
                spielerDropdownPage(statement.title, 2);
                break;

            case DEAKTIV:
                Deaktiviert deaktiviert = new Deaktiviert();
                erzählerDropdownPage(statement, getEmptyDropdownOptions(), getEmptyDropdownOptions(), deaktiviert.imagePath);
                showZeigekarteOnSpielerScreen(deaktiviert);
                break;

            case DEAD:
                Tot tot = new Tot();
                erzählerDropdownPage(statement, getEmptyDropdownOptions(), getEmptyDropdownOptions(), tot.imagePath);
                showZeigekarteOnSpielerScreen(tot);
                break;

            case NOT_IN_GAME:
                erzählerDropdownPage(statement, getEmptyDropdownOptions(), getEmptyDropdownOptions(), new AusDemSpiel().imagePath);
                spielerDropdownPage(statement.title, 2);
                break;
        }

        waitForAnswer();
    }

    public static String showKonditorDropdownPage(Statement statement, FrontendObject frontendObject) {
        erzählerDropdownPage(statement, frontendObject.dropdownOptions);
        spielerDropdownPage(statement.title, 1);

        waitForAnswer();

        return erzählerFrame.chosenOption1;
    }

    public static void showEndScreenPage(Winner winner) {
        erzählerEndScreenPage(winner);
        spielerEndScreenPage(winner);

        waitForAnswer();
    }

    public static void showZeigekarte(Statement statement, Zeigekarte zeigekarte) {
        erzählerIconPicturePage(statement, zeigekarte.imagePath);
        spielerIconPicturePage(zeigekarte.title, zeigekarte.imagePath);

        waitForAnswer();
    }

    //TODO Cases die sowieso gleich aussehen zusammenfassen
    public static void showAufgebrauchtPages(Statement statement, FrontendObject frontendObject) {
        Zeigekarte aufgebraucht = new Aufgebraucht();

        switch (frontendObject.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_IMAGE:
                erzählerDropdownPage(statement, getEmptyDropdownOptions(), aufgebraucht.imagePath);
                break;

            case LIST:
                erzählerListPage(statement, getEmptyStringList(), aufgebraucht.imagePath);
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                erzählerIconPicturePage(statement, aufgebraucht.imagePath);
                break;
        }

        showZeigekarteOnSpielerScreen(aufgebraucht);
        waitForAnswer();
    }

    public static void showDeaktivPages(Statement statement, FrontendObject frontendObject) {
        Zeigekarte deaktiviert = new Deaktiviert();

        switch (frontendObject.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_IMAGE:
                erzählerDropdownPage(statement, getEmptyDropdownOptions(), deaktiviert.imagePath);
                break;

            case LIST:
                erzählerListPage(statement, getEmptyStringList(), deaktiviert.imagePath);
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                erzählerIconPicturePage(statement, deaktiviert.imagePath);
                break;
        }

        showZeigekarteOnSpielerScreen(deaktiviert);
        waitForAnswer();
    }

    public static void showTotPages(Statement statement, FrontendObject frontendObject) {
        Zeigekarte tot = new Tot();

        switch (frontendObject.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_IMAGE:
                erzählerDropdownPage(statement, getEmptyDropdownOptions(), tot.imagePath);
                break;
            case LIST:
                erzählerListPage(statement, getEmptyStringList(), tot.imagePath);
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                erzählerIconPicturePage(statement, tot.imagePath);
                break;
        }

        showZeigekarteOnSpielerScreen(tot);
        waitForAnswer();
    }

    public static void showAusDemSpielPages(Statement statement, FrontendObject frontendObject) {
        Zeigekarte ausDemSpiel = new AusDemSpiel();

        switch (frontendObject.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_IMAGE:
                erzählerDropdownPage(statement, getEmptyDropdownOptions(), ausDemSpiel.imagePath);
                spielerDropdownPage(statement.title, 1);
                break;

            case LIST:
                erzählerListPage(statement, getEmptyStringList(), ausDemSpiel.imagePath);
                spielerListPage(statement.title, getEmptyStringList());
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                erzählerIconPicturePage(statement, ausDemSpiel.imagePath);
                spielerIconPicturePage(statement.title, "");
                break;
        }

        waitForAnswer();
    }

    public static void showTitle(Statement statement) {
        showTitle(statement, statement.title);
    }

    public static void showTitle(Statement statement, String title) {
        erzählerDefaultNightPage(statement);
        spielerTitlePage(title);

        waitForAnswer();
    }

    public static void showDropdown(Statement statement, String title, DropdownOptions dropdownOptions) {
        erzählerDropdownPage(statement, dropdownOptions);
        spielerDropdownPage(title, 1);

        waitForAnswer();
    }

    public static void showDropdown(Statement statement, DropdownOptions dropdownOptions1, DropdownOptions dropdownOptions2) {
        erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
        spielerDropdownPage(statement.title, 2);

        waitForAnswer();
    }

    public static void showDropdownList(Statement statement, String title, DropdownOptions dropdownOptions, boolean hatZurückButton) {
        erzählerDropdownPage(statement, dropdownOptions, hatZurückButton);
        spielerDropdownListPage(title, dropdownOptions);

        waitForAnswer();
    }

    public static void showList(Statement statement, String string) {
        List<String> list = new ArrayList<>();
        list.add(string);
        showList(statement, list);
    }

    public static void showList(Statement statement, List<String> strings) {
        showList(statement, statement.title, strings, false);
    }

    public static void showList(Statement statement, String title, List<String> strings, boolean hatZurückButton) {
        erzählerListPage(statement, title, strings, hatZurückButton);
        spielerListPage(title, strings);

        waitForAnswer();
    }

    public static void showListWithNote(Statement statement, String title, List<String> strings, boolean hatZurückButton, String note) {
        erzählerListPage(statement, title, strings, hatZurückButton);
        spielerListPageWithNote(title, strings, note);

        waitForAnswer();
    }

    public static void showImage(Statement statement, String imagePath) {
        showImage(statement, statement.title, imagePath);
    }

    public static void showImage(Statement statement, String title, String imagePath) {
        erzählerIconPicturePage(statement, title, imagePath);
        spielerIconPicturePage(title, imagePath);

        waitForAnswer();
    }

    public static void showCard(Statement statement, String title, String imagePath) {
        erzählerCardPicturePage(statement, title, imagePath);
        spielerCardPicturePage(title, imagePath);

        waitForAnswer();
    }

    public static void showListShowImage(Statement statement, List<String> strings, String spielerImagePath, String erzählerImagePath) {
        showListShowImage(statement, statement.title, strings, spielerImagePath, erzählerImagePath);
    }

    public static void showListShowImage(Statement statement, String title, List<String> strings, String spielerImagePath, String erzählerImagePath) {
        erzählerListPage(statement, strings, erzählerImagePath);
        spielerIconPicturePage(title, spielerImagePath);

        waitForAnswer();
    }

    public static void showListShowImage(Statement statement, List<String> strings, String spielerImagePath) {
        showListShowImage(statement, statement.title, strings, spielerImagePath);
    }

    public static void showListShowImage(Statement statement, String title, List<String> strings, String spielerImagePath) {
        erzählerListPage(statement, strings);
        spielerIconPicturePage(title, spielerImagePath);

        waitForAnswer();
    }

    public static void showSchnüfflerInfo(Statement statement, List<SchnüfflerInformation> informationen) {
        erzählerDefaultNightPage(statement);
        spielerSchnüfflerInfoPage(informationen);

        waitForAnswer();
    }

    public static List<String> getEmptyStringList() {
        List<String> emptyContent = new ArrayList<>();
        emptyContent.add("");
        return emptyContent;
    }

    public static DropdownOptions getEmptyDropdownOptions() {
        return new DropdownOptions(getEmptyStringList());
    }

    public static void waitForAnswer() {
        refreshÜbersichtsFrame();
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        erzählerDefaultDayPage();
        spielerDayPage();
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
        erzählerAnnounceOpferPage(spieler, imagePath);
        spielerCardPicturePage(spieler.name, imagePath);
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
        SoundManager.playCannon();
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
        erzählerDropdownPage(statement, dropdownOptions, false);
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
