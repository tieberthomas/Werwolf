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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpielerFrame extends MyFrame{
    ErzählerFrame erzählerFrame;
    public SpielerPageFactory pageFactory;
    public SpielerPageElementFactory pageElementFactory;

    public JLabel comboBox1Label;
    public JLabel comboBox2Label;
    public JLabel comboBox3Label;

    public JLabel clockLabel;

    public Page blankPage;
    public Page deactivatedPage;
    public Page dropDownPage;
    public Page bierPage;
    public Page aufgebrauchtPage;
    public Page deadPage;

    public int mode = SpielerFrameMode.blank;
    public String title = "";

    public static String timestring = "00:00:00";
    public static int time = 0;

    public SpielerFrame(ErzählerFrame erzählerFrame){
        WINDOW_TITLE = "Spieler Fenster";
        this.erzählerFrame = erzählerFrame;

        pageFactory = new SpielerPageFactory(this);
        pageElementFactory = new SpielerPageElementFactory(this);

        comboBox1Label = new JLabel("");
        comboBox2Label = new JLabel("");
        comboBox3Label = new JLabel("");
        clockLabel = new JLabel();

        frameJpanel = generateDefaultPanel();

        generateAllPages();

        this.setLocation(erzählerFrame.frameJpanel.getWidth() + 20,0);

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

    public void refreshSetupPage(){
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
        mainRoles.addAll(Hauptrolle.getMainRoleInGameNames());

        ArrayList<String> secondaryRoles = new ArrayList<>();
        secondaryRoles.addAll(Nebenrolle.getSecondaryRoleInGameNames());
        buildScreenFromPage(pageFactory.generateDoubleListPage(mainRoles, secondaryRoles, "Hauptrollen", "Nebenrollen"));
    }

    public void generateDayPage() {
        title = Tag.dayTitle;
        if(PhaseMode.phase == PhaseMode.freibierTag) {
            mode = SpielerFrameMode.freibierPage;
            this.bierPage();
        } else {
            buildScreenFromPage(pageFactory.generateDayPage(Hauptrolle.getPossibleInGameMainRoleNames(), Nebenrolle.getPossibleInGameMainRoleNames()));
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

    public void startTimeUpdateThread() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        Runnable periodicTask = new Runnable() {
            public void run() {
                time++;
                SpielerFrame.generateTimeString();
                clockLabel.setText(timestring);
            }
        };

        executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS);
    }

    public static void generateTimeString(){
        int tmpTime = time;
        int firstDigit = 0;
        int secondDigit = 0;
        int thirdDigit = 0;
        int fourthDigit = 0;
        int fifthDigit = 0;
        int sixthDigit = 0;

        firstDigit = tmpTime%10;
        tmpTime-=firstDigit;
        tmpTime = tmpTime/10;
        secondDigit = tmpTime%6;
        tmpTime-=secondDigit;
        tmpTime = tmpTime/6;
        thirdDigit = tmpTime%10;
        tmpTime-=thirdDigit;
        tmpTime = tmpTime/10;
        fourthDigit = tmpTime%6;
        tmpTime-=fourthDigit;
        tmpTime = tmpTime/6;
        fifthDigit = tmpTime%10;
        tmpTime-=fifthDigit;
        tmpTime = tmpTime/10;
        sixthDigit = tmpTime;

        timestring = "" + sixthDigit + "" + fifthDigit + ":" + fourthDigit + "" + thirdDigit + ":" + secondDigit + "" + firstDigit;
    }
}
