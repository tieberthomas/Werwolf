package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Utils.PageRefresher.Models.ButtonTable;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.Label;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Persona.Hauptrolle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainrolePage extends RolePage {
    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new ButtonTable(roleButtons));
        pageRefresher.add(new LabelTable(labelTable, game::getHauptrolleInGameNames));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, game.hauptrollenInGame::size));
        pageRefresher.add(new Label(counterLabel, this::getMainroleCounterLabelText));
    }

    private String getMainroleCounterLabelText() {
        return getCounterLabelText(game.spieler.size(), game.hauptrollenInGame.size());
    }

    protected void refreshSpielerFrame() {
        spielerFrame.refreshHauptrolleSetupPage();
    }

    @Override
    public void generatePage() {
        int numberOfplayers = game.spieler.size();
        int numberOfhauptrollen = game.hauptrollenInGame.size();
        List<String> hauptrollen = game.getHauptrolleNames();
        //TODO remove information from page generation since it already is gathered in refresh
        pageFactory.generateRollenSetupPage(page, interactiveElementsDto, numberOfplayers, numberOfhauptrollen, hauptrollen);
        //TODO generalize role-pagecreation
    }

    @Override
    protected void addRolle(ActionEvent ae) {
        String hauptrolleName = ((JButton) ae.getSource()).getText();
        Hauptrolle newHauptrolle = game.findHauptrolle(hauptrolleName);
        game.hauptrollenInGame.add(newHauptrolle);

        refresh();
    }

    @Override
    protected void deleteRolle(ActionEvent ae) {
        int index = deleteButtons.indexOf(ae.getSource());
        deleteButtons.remove(index);
        String hauptrolleName = game.hauptrollenInGame.get(index).name;

        List<String> hauptrollenSpecified = game.getHauptrollenSpecifiedStrings();

        if (hauptrollenSpecified.contains(hauptrolleName)) {
            int specifedIndex = hauptrollenSpecified.indexOf(hauptrolleName);
            removeSpecifiedPlayer(specifedIndex);
        }

        game.hauptrollenInGame.remove(index);

        refresh();
    }

    protected void addAllRollen() {
        game.addAllHauptrollenToGame();
    }
}
