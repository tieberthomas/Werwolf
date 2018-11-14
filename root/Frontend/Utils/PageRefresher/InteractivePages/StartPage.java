package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.StartPageElementsDto;
import root.Frontend.Utils.PageRefresher.Models.InteractivePage;
import root.Frontend.Utils.PageRefresher.Models.LoadMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class StartPage extends InteractivePage {
    StartPageElementsDto interactiveElementsDto;

    private JButton startButton;
    private JButton lastCompositionButton;
    private JButton lastGameButton;
    private List<JButton> startGameButtons;

    public void showPage() {
        erzählerFrame.currentInteractivePage = this;
        erzählerFrame.currentPage = this.page;
        erzählerFrame.buildScreenFromPage(this.page);
        if (erzählerFrame.spielerFrame != null) {
            erzählerFrame.spielerFrame.dispatchEvent(new WindowEvent(erzählerFrame.spielerFrame, WindowEvent.WINDOW_CLOSING));
        }
    }

    @Override
    protected void setupObjects() {
        startButton = new JButton();
        lastCompositionButton = new JButton();
        lastGameButton = new JButton();
        startGameButtons = new ArrayList<>();
        startGameButtons.add(startButton);
        startGameButtons.add(lastCompositionButton);
        startGameButtons.add(lastGameButton);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (startGameButtons.contains(ae.getSource())) {
            LoadMode loadMode = LoadMode.NOLOAD;

            if (startButton.equals(ae.getSource())) {
                loadMode = LoadMode.NOLOAD;
            } else if (lastCompositionButton.equals(ae.getSource())) {
                loadMode = LoadMode.COMPOSITION;
            } else if (lastGameButton.equals(ae.getSource())) {
                loadMode = LoadMode.GAME;
            }

            erzählerFrame.setupGame(loadMode);

            erzählerFrame.nextPage();
        }
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new StartPageElementsDto(startButton, lastCompositionButton, lastGameButton);
    }

    @Override
    public void generatePage() {
        pageFactory.generateStartPage(page, interactiveElementsDto);
    }
}
