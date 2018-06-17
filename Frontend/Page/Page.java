package root.Frontend.Page;

import java.util.ArrayList;

public class Page {
    public Page prevPage;
    public Page nextPage;
    public ArrayList<PageElement> pageElements;
    public ArrayList<PageTable> pageTables;
    public int xPageOffset;
    public int yPageOffset;

    public static int DEFAULT_SPACE = 10;

    public Page(int spaceToXBorder, int spaceToYBorder) {
        pageElements = new ArrayList<PageElement>();
        pageTables = new ArrayList<PageTable>();

        this.xPageOffset = spaceToXBorder;
        this.yPageOffset = spaceToYBorder;

        prevPage = null;
        nextPage = null;
    }

    public Page()
    {
        this(DEFAULT_SPACE,DEFAULT_SPACE);
    }

    public void add(PageElement pageElement) {
        pageElement.setPageOffset(xPageOffset, yPageOffset);
        pageElements.add(pageElement);
    }

    public void addTable(PageTable pageTable) {
        pageTable.setPageOffset(xPageOffset, yPageOffset);
        pageTables.add(pageTable);
    }

    public void chain(Page predecessor) {
        prevPage = predecessor;
        predecessor.nextPage = this;
    }
}
