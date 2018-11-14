package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Utils.PageRefresher.Models.ButtonTable;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.Label;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Persona.Hauptrolle;
import root.mechanics.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class MainrolePage extends RolePage {
    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new ButtonTable(roleButtons));
        pageRefresher.add(new LabelTable(labelTable, Game.game::getHauptrolleInGameNames));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, Game.game.hauptrollenInGame::size));
        pageRefresher.add(new Label(counterLabel, this::getMainroleCounterLabelText));
    }

    private String getMainroleCounterLabelText() {
        return getCounterLabelText(Game.game.spieler.size(), Game.game.hauptrollenInGame.size());
    }

    protected void refreshSpielerFrame() {
        spielerFrame.refreshHauptrolleSetupPage();
    }

    @Override
    public void generatePage() {
        int numberOfplayers = Game.game.spieler.size();
        int numberOfhauptrollen = Game.game.hauptrollenInGame.size();
        List<String> hauptrollen = Game.game.getHauptrolleNames();
        //TODO remove information from page generation since it already is gathered in refresh
        pageFactory.generateRollenSetupPage(page, interactiveElementsDto, numberOfplayers, numberOfhauptrollen, hauptrollen);
        //TODO generalize role-pagecreation
    }

    @Override
    protected void addRolle(ActionEvent ae) {
        String hauptrolleName = ((JButton) ae.getSource()).getText();
        Hauptrolle newHauptrolle = Game.game.findHauptrollePerName(hauptrolleName);
        Game.game.hauptrollenInGame.add(newHauptrolle);

        refresh();
    }

    @Override
    protected void deleteRolle(ActionEvent ae) {
        int index = deleteButtons.indexOf(ae.getSource());
        deleteButtons.remove(index);
        List<String> sortedHauptrollenInGame = game.hauptrollenInGame.stream().map(h -> h.name).sorted().collect(Collectors.toList());
        String hauptrolleID = sortedHauptrollenInGame.get(index);

        List<String> hauptrollenSpecifiedIDs = Game.game.getHauptrollenSpecifiedIDs();

        if (hauptrollenSpecifiedIDs.contains(hauptrolleID)) {
            int specifedIndex = hauptrollenSpecifiedIDs.indexOf(hauptrolleID);
            removeSpecifiedPlayer(specifedIndex);
        }

        Hauptrolle hauptrolle = game.findHauptrolle(hauptrolleID);
        game.hauptrollenInGame.remove(hauptrolle);

        refresh();
    }

    protected void addAllRollen() {
        Game.game.addAllHauptrollenToGame();
    }
}
