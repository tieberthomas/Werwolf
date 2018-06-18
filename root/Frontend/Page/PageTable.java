package root.Frontend.Page;

import javax.swing.*;
import java.util.ArrayList;

public class PageTable extends Predecessor {
    public ArrayList<JComponent> tableElements;
    public int rows;
    public int columns;
    public int table_element_width;
    public int table_element_height;
    private int table_elements_x_distance;
    private int table_elements_y_distance;

    public static int DEFAULT_COLUMNS = 4;
    public static int DEFAULT_TABLE_ELEMENTS_DISTANCE = 10;


    public PageTable(int columns, int table_element_width, int table_element_height, Predecessor predecessorX, Predecessor predecessorY, int space_to_predecessor_x, int space_to_predecessor_y) {
        super(0, 0, predecessorX, predecessorY, space_to_predecessor_x, space_to_predecessor_y);
        this.columns = columns;
        this.table_element_width = table_element_width;
        this.table_element_height = table_element_height;
        table_elements_x_distance = DEFAULT_TABLE_ELEMENTS_DISTANCE;
        table_elements_y_distance = DEFAULT_TABLE_ELEMENTS_DISTANCE;
        tableElements = new ArrayList<JComponent>();
        calcTableHeight();
        calcTableWidth();
    }

    public PageTable(int table_element_width, int table_element_height) {
        this(DEFAULT_COLUMNS, table_element_width, table_element_height, null, null, DEFAULT_SPACE,DEFAULT_SPACE);
    }

    public PageTable(int table_element_width, int table_element_height, Predecessor predecessorX) {
        this(DEFAULT_COLUMNS, table_element_width, table_element_height, predecessorX, null, DEFAULT_SPACE,DEFAULT_SPACE);
    }

    public PageTable(int table_element_width, int table_element_height, Predecessor predecessorX, int space_to_predecessor_x) {
        this(DEFAULT_COLUMNS, table_element_width, table_element_height, predecessorX, null, space_to_predecessor_x,DEFAULT_SPACE);
    }

    public void calcTableRows()
    {
        rows = (int)Math.ceil(((double) tableElements.size())/columns);
    }

    public void calcTableHeight() {
        calcTableRows();
        int height = rows * (table_element_height+table_elements_y_distance)-table_elements_y_distance;

        if(height<0)
            this.height =  0;
        else
            this.height = height;
    }

    public void calcTableWidth() {
        int width = columns * (table_element_width+table_elements_x_distance)-table_elements_x_distance;

        if(width<0)
            this.width = 0;
        else
            this.width = width;
    }

    public void add(JComponent tableElement) {
        int currentColumn = tableElements.size()%columns;
        tableElements.add(tableElement);
        calcTableRows();
        calcTableWidth();
        calcTableHeight();

        int elementCoordX = pageCoordX + currentColumn * (table_element_width + table_elements_x_distance);
        int elementCoordY = pageCoordY + ((rows - 1) * (table_element_height + table_elements_y_distance));
        tableElement.setBounds(elementCoordX, elementCoordY, table_element_width,table_element_height);
    }

    public void setTable_elements_x_distance(int table_elements_x_distance) {
        this.table_elements_x_distance = table_elements_x_distance;
        calcTableWidth();
    }

    public void setTable_elements_y_distance(int table_elements_y_distance) {
        this.table_elements_y_distance = table_elements_y_distance;
        calcTableHeight();
    }

    public void setCoords(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        pageCoordX = this.coordX + pageOffsetX;
        pageCoordY = this.coordY + pageOffsetY;

        ArrayList<JComponent> tmp = tableElements;
        tableElements = new ArrayList<JComponent>();

        for(JComponent component : tmp)
        {
            this.add(component);
        }
    }
}
