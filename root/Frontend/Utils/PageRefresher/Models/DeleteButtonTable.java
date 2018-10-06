package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Factories.ErzählerPageElementFactory;
import root.Frontend.Page.PageTable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.function.IntSupplier;

public class DeleteButtonTable extends RefreshObject{
    private PageTable table;
    private ArrayList<JButton> buttons;
    private IntSupplier numberOfButtons;

    public DeleteButtonTable(PageTable table, ArrayList<JButton> buttons, IntSupplier numberOfButtons) {
        this.table = table;
        this.buttons = buttons;
        this.numberOfButtons = numberOfButtons;
    }

    @Override
    public void refresh() {
        table.tableElements.clear();
        buttons.clear();

        for (int i=0; i<numberOfButtons.getAsInt(); i++) {
            JButton deleteButton = ErzählerPageElementFactory.generateDeleteButton();
            table.add(deleteButton);
            buttons.add(deleteButton);
        }
    }
}
