package root.Frontend.Utils.PageRefresherFramework.Models;

import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Utils.PageRefresherFramework.PageRefresher;

import java.awt.event.ActionEvent;

public class RefreshedPage extends InteractivePage {
    public static SpielerFrame spielerFrame;
    protected PageRefresher pageRefresher;

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
