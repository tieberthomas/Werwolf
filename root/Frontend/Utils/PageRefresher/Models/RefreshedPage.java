package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.mechanics.Game;

import java.awt.event.ActionEvent;

public class RefreshedPage extends InteractivePage {
    public static Game game;
    public static SpielerFrame spielerFrame;
    public PageRefresher pageRefresher;

    public RefreshedPage() {
        setupObjects();
        setupPageElementsDtos();
    }

    public void showPage() {
        erzählerFrame.currentInteractivePage = this;
        erzählerFrame.currentPage = this.page;
        refresh();
        erzählerFrame.buildScreenFromPage(this.page);
    }

    protected void setupPageElementsDtos() {
    }

    protected void setupObjects() {
    }

    public void setupPageRefresher() {
    }

    public void refresh() {
        pageRefresher.refreshPage();
    }

    public void generatePage() {

    }

    public void processActionEvent(ActionEvent ae) {
    }
}
