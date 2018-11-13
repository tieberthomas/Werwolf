package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.PlayerSetupPageElementsDto;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresher.Models.Label;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Spieler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class PlayerSetupPage extends RefreshedPage {
    private PlayerSetupPageElementsDto interactiveElementsDto;

    private JButton addPlayerButton;
    private JTextField addPlayerTxtField;
    private JLabel counterLabel;
    private PageTable labelTable;
    private PageTable deleteTable;
    private List<JButton> deleteButtons = new ArrayList<>();
    private JButton next;
    private JButton back;

    @Override
    protected void setupObjects() {
        addPlayerButton = new JButton();
        addPlayerTxtField = new JTextField();
        counterLabel = new JLabel();
        labelTable = new PageTable();
        deleteTable = new PageTable();
        next = new JButton();
        back = new JButton();
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (next.equals(ae.getSource())) {
            erzählerFrame.nextPage();
        } else if (back.equals(ae.getSource())) {
            erzählerFrame.prevPage();

        } else if (addPlayerButton.equals(ae.getSource()) || addPlayerTxtField.equals(ae.getSource())) {
            if (!addPlayerTxtField.getText().equals("") && !game.spielerExists(addPlayerTxtField.getText())) {
                addPlayer();
            }
        } else if (deleteButtons.contains(ae.getSource())) {
            deletePlayer(ae);
        }
    }

    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new LabelTable(labelTable, game::getLivingSpielerStrings));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, game.spieler::size));
        pageRefresher.add(new Label(counterLabel, this::getNumberOfPlayersLabelText));
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new PlayerSetupPageElementsDto(addPlayerButton, addPlayerTxtField, counterLabel,
                labelTable, deleteTable, next, back);
    }

    public void refresh() {
        pageRefresher.refreshPage();
        if (spielerFrame != null) {
            spielerFrame.refreshPlayerSetupPage();
        }
        addPlayerTxtField.requestFocusInWindow();
    }

    private String getNumberOfPlayersLabelText() {
        return pageFactory.pageElementFactory.generateNumberOfPLayersLabelTitle(game.spieler.size());
    }

    @Override
    public void generatePage() {
        int numberOfplayers = game.spieler.size();
        pageFactory.generatePlayerSetupPage(page, interactiveElementsDto, numberOfplayers);
    }

    private void addPlayer() {
        String newPlayerName = addPlayerTxtField.getText();
        Spieler newPlayer = new Spieler(newPlayerName);
        game.spieler.add(newPlayer);
        addPlayerTxtField.setText("");

        refresh();
    }

    private void deletePlayer(ActionEvent ae) {
        int index = deleteButtons.indexOf(ae.getSource());

        deleteButtons.remove(index);
        Spieler spieler = game.spieler.get(index);

        if (game.spielerSpecified.contains(spieler)) {
            removeSpecifiedPlayer(index);
        }

        game.spieler.remove(index);

        refresh();
    }

    private void removeSpecifiedPlayer(int index) {
        if (deleteButtons.size() > index) {
            deleteButtons.remove(index);
        }

        game.spielerSpecified.remove(index);
    }
}
