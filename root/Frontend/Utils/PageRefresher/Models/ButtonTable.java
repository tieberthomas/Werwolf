package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Utils.JButtonStyler;

import javax.swing.*;
import java.util.ArrayList;

public class ButtonTable implements RefreshObject {
    private ArrayList<JButton> buttons;

    public ButtonTable(ArrayList<JButton> buttons) {
        this.buttons = buttons;
    }

    @Override
    public void refresh() {
        JButtonStyler.refreshRolleButtons(buttons);
    }
}
