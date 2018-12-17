package root.Frontend.Utils;

import root.Logic.Persona.Rolle;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class JButtonStyler {
    private JButtonStyler() {
    }

    public static void refreshRolleButtons(List<JButton> buttons, List<String> rolesInGame) {
        styleButtons(buttons);
        disableButtons(buttons, rolesInGame);
    }

    private static void styleButtons(List<JButton> buttons) {
        for (JButton button : buttons) {
            button.setEnabled(true);
            Rolle rolle = Rolle.findRollePerName(button.getText());
            if (rolle != null) {
                button.setBackground(rolle.getColor());
            }
            setTextColorWhiteIfBackgroundIsTooDark(button);
        }
    }

    private static void setTextColorWhiteIfBackgroundIsTooDark(JButton button) {
        if (button.getBackground().equals(Color.BLACK)) {
            button.setForeground(Color.WHITE);
            button.setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return Color.WHITE;
                }
            });
        }
    }

    private static void disableButtons(List<JButton> buttons, List<String> rolesInGame) {
        for (JButton button : buttons) {
            if (buttonShouldBeDisabled(button, rolesInGame)) {
                disableButton(button);
            }
        }
    }

    private static boolean buttonShouldBeDisabled(JButton button, List<String> rolesInGame) {
        Rolle rolle = Rolle.findRollePerName(button.getText());
        if (rolle != null) {
            int occurrences = Collections.frequency(rolesInGame, rolle.name);
            if (rolle.numberOfPossibleInstances <= occurrences) {
                if (button.isEnabled()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void disableButton(JButton button) {
        button.setEnabled(false);
        Color oldColor = button.getBackground();
        button.setBackground(getDisabledColor(oldColor));
    }

    private static Color getDisabledColor(Color currentColor) {
        int offset = 128;
        int newRed = currentColor.getRed() + offset;
        int newBlue = currentColor.getBlue() + offset;
        int newGreen = currentColor.getGreen() + offset;

        if (newRed > 255)
            newRed = 255;
        if (newBlue > 255)
            newBlue = 255;
        if (newGreen > 255)
            newGreen = 255;

        return new Color(newRed, newGreen, newBlue);
    }
}
