package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Utils.JButtonStyler;

import javax.swing.*;
import java.util.List;

public class ButtonTable implements RefreshObject {
    private List<JButton> buttons;

    public ButtonTable(List<JButton> buttons) {
        this.buttons = buttons;
    }

    @Override
    public void refresh() {
        JButtonStyler.refreshRolleButtons(buttons);
    }
}
