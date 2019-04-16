package root.Frontend.Utils.PageRefresherFramework;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Utils.PageRefresherFramework.Models.RefreshObject;

import java.util.ArrayList;
import java.util.List;

public class PageRefresher {
    public static ErzählerFrame erzählerFrame;
    private Page page;
    private List<RefreshObject> refreshObjects;

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
