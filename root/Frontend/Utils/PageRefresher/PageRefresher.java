package root.Frontend.Utils.PageRefresher;

import root.Frontend.Utils.PageRefresher.Models.RefreshObject;

import java.util.ArrayList;

public class PageRefresher {
    private ArrayList<RefreshObject> refreshObjects;
    public int listSize = 0;

    public PageRefresher() {
        this(0);
    }

    public PageRefresher(int listSize) {
        refreshObjects = new ArrayList<>();
        this.listSize = listSize;
    }

    public void add(RefreshObject refreshObject) {
        refreshObjects.add(refreshObject);
        refreshObject.pageRefresher = this;
    }

    public void refreshAll() {
        for(RefreshObject refreshObject : refreshObjects) {
            refreshObject.refresh();
        }
    }
}
