package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Page.Page;
import root.Frontend.Utils.PageRefresher.PageRefresher;

public class InteractivePage {
    Page page;
    PageRefresher pageRefresher;

    public InteractivePage(Page page) {
        this.page = page;
        setupPageRefresher();
    }

    private void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
    }

    public void refresh() {
        pageRefresher.refreshPage();
    }
}
