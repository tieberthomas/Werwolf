package root.Frontend.InteractivePages.InteractiveElementsDtos;

import javax.swing.*;

public class StartPageElementsDto {
    public JButton startButton;
    public JButton lastCompositionButton;
    public JButton lastGameButton;

    public StartPageElementsDto(JButton startButton, JButton lastCompositionButton, JButton lastGameButton) {
        this.startButton = startButton;
        this.lastCompositionButton = lastCompositionButton;
        this.lastGameButton = lastGameButton;
    }
}
