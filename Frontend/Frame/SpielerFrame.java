package root.Frontend.Frame;

import root.Frontend.Factories.SpielerPageElementFactory;
import root.Frontend.Factories.SpielerPageFactory;
import root.Frontend.Page.Page;
import root.Phases.PhaseMode;
import root.Phases.Tag;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrolle;
import root.Spieler;

import javax.swing.*;
import java.util.ArrayList;

public class SpielerFrame extends MyFrame{
    public SpielerPageFactory pageFactory;
    public SpielerPageElementFactory pageElementFactory;

    public JLabel comboBox1Label;
    public JLabel comboBox2Label;
    public JLabel comboBox3Label;

    public Page blankPage;
    public Page deactivatedPage;
    public Page dropDownPage;
    public Page bierPage;
    public Page aufgebrauchtPage;
    public Page deadPage;

    public int mode = SpielerFrameMode.blank;
    public String title = "";

    public SpielerFrame(){
        WINDOW_TITLE = "Spieler Fenster";

        pageFactory = new SpielerPageFactory(this);
        pageElementFactory = new SpielerPageElementFactory(this);

        comboBox1Label = new JLabel();
        comboBox2Label = new JLabel();
        comboBox3Label = new JLabel();

        generateAllPages();

        this.setLocation(ErzählerFrame.PANEL_WIDTH + 20,0);

        showFrame();
    }

    public void generateAllPages() {
        blankPage = new Page();
        dropDownPage = pageFactory.generateDropdownPage("",1);
        bierPage = pageFactory.generateStaticImagePage(Tag.dayTitle, ResourcePath.FREIBIER, true);
        deadPage = pageFactory.generateStaticImagePage("Tod", ResourcePath.TOT, true);
        aufgebrauchtPage = pageFactory.generateStaticImagePage("Aufgebraucht", ResourcePath.AUFGEBRAUCHT, true);
        deactivatedPage = pageFactory.generateStaticImagePage("Deaktiviert", ResourcePath.DEAKTIVIERT, true);
    }

    public void refreshSetupPage(ErzählerFrame erzählerFrame){
        if(erzählerFrame.currentPage.equals(erzählerFrame.playerSetupPage)) {
            refreshPlayerSetupPage();
        }

        if(erzählerFrame.currentPage.equals(erzählerFrame.mainRoleSetupPage)) {
            refreshMainRoleSetupPage();
        }

        if(erzählerFrame.currentPage.equals(erzählerFrame.secondaryRoleSetupPage)) {
            refreshSecondaryRoleSetupPage();
        }

        if(erzählerFrame.currentPage.equals(erzählerFrame.playerSpecifiyPage)) {
            refreshSecondarySpecifySetupPage();
        }
    }

    public void refreshPlayerSetupPage(){
        buildScreenFromPage(pageFactory.generateListPage(Spieler.getLivigPlayerStrings()));
    }

    public void refreshMainRoleSetupPage(){
        buildScreenFromPage(pageFactory.generateListPage(Hauptrolle.getMainRoleInGameNames()));
    }

    public void refreshSecondaryRoleSetupPage(){
        buildScreenFromPage(pageFactory.generateListPage(Nebenrolle.getSecondaryRoleInGameNames()));
    }

    public void refreshSecondarySpecifySetupPage(){
        ArrayList<String> mainRoles = new ArrayList<>();
        mainRoles.add("Hauptrollen:");
        mainRoles.addAll(Hauptrolle.getMainRoleInGameNames());

        ArrayList<String> secondaryRoles = new ArrayList<>();
        secondaryRoles.add("Nebenrollen:");
        secondaryRoles.addAll(Nebenrolle.getSecondaryRoleInGameNames());
        buildScreenFromPage(pageFactory.generateDoubleListPage(mainRoles, secondaryRoles));
    }

    public void generateDayPage() {
        title = Tag.dayTitle;
        if(PhaseMode.phase == PhaseMode.freibierTag) {
            mode = SpielerFrameMode.freibierPage;
            this.bierPage();
        } else {
            dropDownPage = pageFactory.generateDropdownPage(title, 1);
            buildScreenFromPage(dropDownPage);
        }
    }

    public void deadPage() {
        buildScreenFromPage(deadPage);
    }

    public void aufgebrauchtPage() {
        buildScreenFromPage(aufgebrauchtPage);
    }

    public void bierPage() {
        buildScreenFromPage(bierPage);
    }

    public void deactivatedPage() {
        buildScreenFromPage(deactivatedPage);
    }
}
