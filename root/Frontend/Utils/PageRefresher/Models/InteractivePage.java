package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Factories.ErzählerPageFactory;
import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Page.Page;

import java.awt.event.ActionEvent;

public class InteractivePage {
    public static ErzählerPageFactory pageFactory;
    public static ErzählerFrame erzählerFrame;
    public Page page;

    public InteractivePage() {
        page = new Page();
        setupObjects();
        setupPageElementsDtos();
    }

    public void showPage() {
        erzählerFrame.currentInteractivePage = this;
        erzählerFrame.currentPage = this.page;
        erzählerFrame.buildScreenFromPage(this.page);
    }

    protected void setupPageElementsDtos() {
    }

    protected void setupObjects() {
    }

    public void generatePage() {
    }

    public void processActionEvent(ActionEvent ae) {
    }
}
