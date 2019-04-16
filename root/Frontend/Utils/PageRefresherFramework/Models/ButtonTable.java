package root.Frontend.Utils.PageRefresherFramework.Models;

import root.Frontend.Utils.JButtonStyler;

import javax.swing.*;
import java.util.List;

public class ButtonTable implements RefreshObject {
    private List<JButton> buttons;
    private List<String> rolesInGame;

    public ButtonTable(List<JButton> buttons, List<String> rolesInGame) {
        this.buttons = buttons;
        this.rolesInGame = rolesInGame;
    }

    @Override
    public void refresh() {
        JButtonStyler.refreshRolleButtons(buttons, rolesInGame);
    }
}
