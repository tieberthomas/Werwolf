package root.Frontend;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Page.Page;
import root.Phases.Statement;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrollen.Konditor;
import root.Rollen.Nebenrollen.Konditorlehrling;
import root.Rollen.Rolle;
import root.Spieler;
import root.mechanics.Opfer;

import java.util.ArrayList;

/**
 * Created by Steve on 16.05.2018.
 */
public class FrontendControl {
    public static final int DROPDOWN_WITHOUT_SUGGESTIONS = 0;
    public static final int DROPDOWN_WITH_SUGGESTIONS = 1;
    public static final int STATIC_LIST = 2;
    public static final int STATIC_IMAGE = 3;
    public static final int STATIC_CARD = 4;

    public static ErzählerFrame erzählerFrame;
    public static SpielerFrame spielerFrame;

    public int typeOfContent;
    public ArrayList<String> content;
    public String imagePath;
    public String title;

    public FrontendControl() {
        this.typeOfContent = DROPDOWN_WITHOUT_SUGGESTIONS;
        this.content = new ArrayList<>();
    }

    public FrontendControl(ArrayList<String> content) {
        this.typeOfContent = DROPDOWN_WITHOUT_SUGGESTIONS;
        this.content = content;
    }

    public FrontendControl(ArrayList<String> content, String title) {
        this.typeOfContent = DROPDOWN_WITHOUT_SUGGESTIONS;
        this.content = content;
        this.title = title;
    }

    public FrontendControl(String imagePath) {
        this.typeOfContent = STATIC_IMAGE;
        this.imagePath = imagePath;
    }

    public FrontendControl(String imagePath, String title) {
        this.typeOfContent = STATIC_IMAGE;
        this.imagePath = imagePath;
        this.title = title;
    }

    public FrontendControl(int typeOfContent, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.imagePath = imagePath;
    }

    public FrontendControl(int typeOfContent, ArrayList<String> content) {
        this.typeOfContent = typeOfContent;
        this.content = content;
    }

    public FrontendControl(int typeOfContent, ArrayList<String> content, String title) {
        this.typeOfContent = typeOfContent;
        this.content = content;
        this.title = title;
    }

    public static void showDropdownPage(Statement statement, FrontendControl frontendControl) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                erzählerDropdownPage(statement, frontendControl.content);

                switch (frontendControl.typeOfContent)
                {
                    case DROPDOWN_WITHOUT_SUGGESTIONS:
                        spielerDropdownPage(statement.title, 1);
                        break;

                    case DROPDOWN_WITH_SUGGESTIONS:
                        spielerListMirrorPage(statement.title, frontendControl.content);
                        break;
                }
                break;

            case Statement.DEAKTIV:
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
                break;

            case Statement.DEAD:
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
                break;

            case Statement.NOT_IN_GAME:
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                spielerDropdownPage(statement.title,1);
                break;
        }
    }

    public static void showListOnBothScreens(Statement statement, String title, ArrayList<String> strings) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                erzählerListPage(statement, title, strings);
                spielerListPage(title, strings);
                break;

            case Statement.DEAKTIV:
                erzählerListPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
                break;

            case Statement.DEAD:
                erzählerListPage(statement, getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
                break;

            case Statement.NOT_IN_GAME:
                erzählerListPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                spielerListPage(statement.title, getEmptyStringList());
                break;
        }
    }

    public static void showDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
                spielerDropdownPage(statement.title, 2);
                break;

            case Statement.DEAKTIV:
                erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
                break;

            case Statement.DEAD:
                erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
                break;

            case Statement.NOT_IN_GAME:
                erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                spielerDropdownPage(statement.title, 2);
                break;
        }
    }

    public static void showAfterDeathDropdownListPage(Statement statement, ArrayList<String> dropdownOptions) {
        if (statement.isLebend()) {
            if (statement.isAktiv()) {
                erzählerDropdownPage(statement, dropdownOptions);
                spielerListMirrorPage(statement.title, dropdownOptions);
            } else {
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
            }
        } else {
            erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
            spielerDropdownPage(statement.title, 1);
        }
    }

    public static void showKonditorDropdownPage(Statement statement, FrontendControl frontendControl) {
        if (Rolle.rolleLebend(Konditor.name) || Rolle.rolleLebend(Konditorlehrling.name)) {
            if (!Opfer.isOpferPerRolle(Konditor.name) || !Opfer.isOpferPerRolle(Konditorlehrling.name)) {
                if (Rolle.rolleAktiv(Konditor.name) || Rolle.rolleAktiv(Konditorlehrling.name)) {
                    erzählerDropdownPage(statement, frontendControl.content);

                    switch (frontendControl.typeOfContent)
                    {
                        case DROPDOWN_WITHOUT_SUGGESTIONS:
                            spielerDropdownPage(statement.title, 1);
                            break;

                        case DROPDOWN_WITH_SUGGESTIONS:
                            spielerListMirrorPage(statement.title, frontendControl.content);
                            break;
                    }
                } else {
                    erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                    spielerFrame.deactivatedPage();
                }
            } else {
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
            }
        } else {
            erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
            spielerDropdownPage(statement.title, 1);
        }
    }

    public static ArrayList<String> getEmptyStringList() {
        ArrayList<String> emptyContent = new ArrayList<>();
        emptyContent.add("");
        return emptyContent;
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
        Page dayPage = erzählerFrame.pageFactory.generateDefaultDayPage();
        erzählerFrame.buildScreenFromPage(dayPage);
    }

    public static void spielerDayPage() {
        spielerFrame.generateDayPage();
    }

    public static void erzählerTortenPage() {
        erzählerFrame.tortenPage = erzählerFrame.pageFactory.generateTortenPage();
        erzählerFrame.buildScreenFromPage(erzählerFrame.tortenPage);
    }
    
    public static void spielerAnnounceVictimPage(Spieler spieler) {
        spielerTwoImagePage(spieler.name, spieler.hauptrolle.getImagePath(), spieler.nebenrolle.getImagePath());
    }

    public static void erzählerAnnounceVictimPage(Spieler spieler) {
        Page dayPage = erzählerFrame.pageFactory.generateAnnounceVictimsDayPage(spieler.name);
        erzählerFrame.buildScreenFromPage(dayPage);
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

    public static void spielerListMirrorPage(String title, ArrayList<String> dropdownOptions) {
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
        Page nightPage = spielerFrame.pageFactory.generateListPage(title, strings);
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

    public static void spielerTwoImagePage(String title, String imagePath1, String imagePath2) {
        Page dayPage = spielerFrame.pageFactory.generateTwoImagePage(title, imagePath1, imagePath2);
        spielerFrame.buildScreenFromPage(dayPage);
    }
}
