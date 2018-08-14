package root.Frontend;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.Page.Page;
import root.Phases.Statement;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Spieler;
import root.mechanics.Game;

import java.util.ArrayList;

/**
 * Created by Steve on 16.05.2018.
 */
public class FrontendControl {
    public static Game game;

    public static final int SKIP = -1;
    public static final int TITLE = 0;
    public static final int DROPDOWN = 10;
    public static final int DROPDOWN_LIST = 11;
    public static final int LIST = 20;
    public static final int IMAGE = 30;
    public static final int CARD = 31;
    public static final int LIST_IMAGE = 40;

    public static ErzählerFrame erzählerFrame;
    public static SpielerFrame spielerFrame;
    public static ÜbersichtsFrame übersichtsFrame;

    public int typeOfContent;
    public String title;
    public ArrayList<String> strings;
    public String imagePath;

    public FrontendControl() {
        this.typeOfContent = SKIP;
    }

    public FrontendControl(String title) {
        this.typeOfContent = TITLE;
        this.title = title;
    }

    public FrontendControl(ArrayList<String> strings) {
        this.typeOfContent = LIST;
        this.strings = strings;
    }

    public FrontendControl(int typeOfContent, ArrayList<String> strings) {
        this.typeOfContent = typeOfContent;
        this.strings = strings;
    }

    public FrontendControl(int typeOfContent, String title, ArrayList<String> strings) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.strings = strings;
    }

    public FrontendControl(int typeOfContent, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.imagePath = imagePath;
    }

    public FrontendControl(int typeOfContent, String title, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.imagePath = imagePath;
    }

    public FrontendControl(int typeOfContent, ArrayList<String> strings, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.strings = strings;
        this.imagePath = imagePath;
    }

    public FrontendControl(int typeOfContent, String title, ArrayList<String> strings, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.strings = strings;
        this.imagePath = imagePath;
    }

    public static void erzählerDefaultNightPage(Statement statement) {
        Page nightPage = erzählerFrame.pageFactory.generateDefaultNightPage(statement);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerTitlePage(String title) {
        Page nightPage = spielerFrame.pageFactory.generateTitlePage(title);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDefaultDayPage() {
        Page dayPage = erzählerFrame.pageFactory.generateDefaultDayPage(game.getLivingPlayerStrings());
        erzählerFrame.buildScreenFromPage(dayPage);
    }

    public static void spielerDayPage() {
        spielerFrame.generateDayPage();
    }

    public static void erzählerTortenPage() {
        erzählerFrame.tortenPage = erzählerFrame.pageFactory.generateTortenPage(game.getLivingPlayerStrings());
        erzählerFrame.buildScreenFromPage(erzählerFrame.tortenPage);
    }

    public static void erzählerAnnounceVictimPage(Spieler spieler) {
        Page dayPage = erzählerFrame.pageFactory.generateAnnounceVictimsDayPage(spieler.name, game.getLivingPlayerStrings());
        erzählerFrame.buildScreenFromPage(dayPage);
    }

    public static void spielerAnnounceVictimPage(Spieler spieler) {
        String hauptRolleImagePath = spieler.hauptrolle.getImagePath();
        String nebenRolleImagePath = spieler.nebenrolle.getImagePath();
        if(GrafVladimir.unerkennbarerSpieler!=null) {
            if (spieler.name.equals(GrafVladimir.unerkennbarerSpieler.name)) {
                hauptRolleImagePath = ResourcePath.AUS_DEM_SPIEL;
                nebenRolleImagePath = ResourcePath.AUS_DEM_SPIEL;
            }
        }
        spielerTwoImagePage(spieler.name, hauptRolleImagePath, nebenRolleImagePath);
    }

    public static void spielerTwoImagePage(String title, String imagePath1, String imagePath2) {
        Page dayPage = spielerFrame.pageFactory.generateTwoImagePage(title, imagePath1, imagePath2);
        spielerFrame.buildScreenFromPage(dayPage);
    }

    public static void erzählerEndScreenPage(String victory) {
        Page nightPage = spielerFrame.pageFactory.generateEndScreenPage(victory);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerEndScreenPage(String victory) {
        Page nightPage = spielerFrame.pageFactory.generateEndScreenPage(victory);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions1, dropdownOptions2);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions1, dropdownOptions2, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerDropdownPage(String title, int numberOfDropdowns) {
        spielerFrame.dropDownPage = spielerFrame.pageFactory.generateDropdownPage(title, numberOfDropdowns);
        spielerFrame.buildScreenFromPage(spielerFrame.dropDownPage);
    }

    public static void spielerDropdownListPage(String title, ArrayList<String> dropdownOptions) {
        spielerFrame.dropDownPage = spielerFrame.pageFactory.generateListMirrorPage(title, dropdownOptions);
        spielerFrame.buildScreenFromPage(spielerFrame.dropDownPage);
    }

    public static void erzählerListPage(Statement statement, String string) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        erzählerListPage(statement, list);
    }

    public static void erzählerListPage(Statement statement, ArrayList<String> strings) {
        erzählerListPage(statement, statement.title, strings);
    }

    public static void erzählerListPage(Statement statement, String title, ArrayList<String> strings) {
        Page nightPage = erzählerFrame.pageFactory.generateListPage(statement, title, strings);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerListPage(Statement statement, ArrayList<String> strings, String imagePath) {
        erzählerListPage(statement, statement.title, strings, imagePath);
    }

    public static void erzählerListPage(Statement statement, String title, ArrayList<String> strings, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateListPage(statement, title, strings, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerListPage(String title, ArrayList<String> strings) {
        //Page nightPage = spielerFrame.pageFactory.generateListPage(title, strings); //TODO removen falls option auf hotfix nichtmer notwendig ist
        Page nightPage = spielerFrame.pageFactory.generateListPage(strings);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerIconPicturePage(Statement statement, String imagePath) {
        erzählerIconPicturePage(statement, statement.title, imagePath);
    }

    public static void erzählerIconPicturePage(Statement statement, String title, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateIconPicturePage(statement, title, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerIconPicturePage(String title, String imagePath) {
        Page nightPage = spielerFrame.pageFactory.generateStaticImagePage(title, imagePath, true);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void erzählerCardPicturePage(Statement statement, String title, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateCardPicturePage(statement, title, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public static void spielerCardPicturePage(String title, String imagePath) {
        Page nightPage = spielerFrame.pageFactory.generateStaticImagePage(title, imagePath, false);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public static void refreshÜbersichtsFrame(){
        übersichtsFrame.refreshÜbersichtsPage();
    }
}
