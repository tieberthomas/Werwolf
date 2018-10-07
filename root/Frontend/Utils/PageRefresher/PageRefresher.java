package root.Frontend.Utils.PageRefresher;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Utils.PageRefresher.Models.RefreshObject;

import java.util.ArrayList;

public class PageRefresher {
    public static ErzählerFrame erzählerFrame;
    private Page page;
    private ArrayList<RefreshObject> refreshObjects;

    public PageRefresher(Page page) {
        this.page = page;
        refreshObjects = new ArrayList<>();
    }

    public void add(RefreshObject refreshObject) {
        refreshObjects.add(refreshObject);
    }

    public void refreshPage() {
        for (RefreshObject refreshObject : refreshObjects) {
            refreshObject.refresh();
        }

        erzählerFrame.buildScreenFromPage(page);
    }
}
