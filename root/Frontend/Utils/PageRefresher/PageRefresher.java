package root.Frontend.Utils.PageRefresher;

import root.Frontend.Utils.PageRefresher.Models.RefreshObject;

import java.util.ArrayList;

public class PageRefresher {
    private ArrayList<RefreshObject> refreshObjects;

    public PageRefresher() {
        refreshObjects = new ArrayList<>();
    }

    public void add(RefreshObject refreshObject) {
        refreshObjects.add(refreshObject);
    }

    public void refreshAll() {
        for (RefreshObject refreshObject : refreshObjects) {
            refreshObject.refresh();
        }
    }
}
