package root.Frontend.Page;

import java.util.ArrayList;

public class Page {
    public ArrayList<PageElement> pageElements;
    public ArrayList<PageTable> pageTables;
    private int xPageOffset;
    private int yPageOffset;

    private static int DEFAULT_SPACE = 10;

    public Page(int spaceToXBorder, int spaceToYBorder) {
        pageElements = new ArrayList<>();
        pageTables = new ArrayList<>();

        this.xPageOffset = spaceToXBorder;
        this.yPageOffset = spaceToYBorder;
    }

    public Page() {
        this(DEFAULT_SPACE, DEFAULT_SPACE);
    }

    public void add(PageElement pageElement) {
        pageElement.setPageOffset(xPageOffset, yPageOffset);
        pageElements.add(pageElement);
    }

    public void addTable(PageTable pageTable) {
        pageTable.setPageOffset(xPageOffset, yPageOffset);
        pageTables.add(pageTable);
    }

    public void clearPage() {
        pageElements.clear();
        pageTables.clear();
    }
}
