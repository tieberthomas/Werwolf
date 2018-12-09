package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Utils.PageRefresher.Models.ButtonTable;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.Label;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.GameController;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class BonusrolePage extends RolePage {
    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new ButtonTable(roleButtons));
        pageRefresher.add(new LabelTable(labelTable, Game.game::getBonusrolleInGameNames));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, Game.game.bonusrollenInGame::size));
        pageRefresher.add(new Label(counterLabel, this::getBonusroleCounterLabelText));
    }

    private String getBonusroleCounterLabelText() {
        return getCounterLabelText(Game.game.spieler.size(), Game.game.bonusrollenInGame.size());
    }

    protected void refreshSpielerFrame() {
        spielerFrame.refreshBonusrolleSetupPage();
    }

    @Override
    public void generatePage() {
        List<String> bonusrollen = Game.game.getBonusrollenButtonNames();
        pageFactory.generateRollenSetupPage(page, interactiveElementsDto, bonusrollen);
    }

    @Override
    protected void next() {
        erzählerFrame.nextPage();
        GameController.writeComposition();
    }

    @Override
    protected void addRolle(ActionEvent ae) {
        String bonusrolleName = ((JButton) ae.getSource()).getText();
        Bonusrolle newBonusrolle = Game.game.findBonusrollePerName(bonusrolleName);
        Game.game.bonusrollenInGame.add(newBonusrolle);

        refresh();
    }

    @Override
    protected void deleteRolle(ActionEvent ae) {
        int index = deleteButtons.indexOf(ae.getSource());
        deleteButtons.remove(index);
        List<String> sortedBonusrollenInGame = Game.game.bonusrollenInGame.stream().map(h -> h.id).sorted().collect(Collectors.toList());
        String bonusrolleID = sortedBonusrollenInGame.get(index);

        List<String> bonusrollenSpecifiedIDs = Game.game.getBonusrolleSpecifiedIDs();

        if (bonusrollenSpecifiedIDs.contains(bonusrolleID)) {
            int specifedIndex = bonusrollenSpecifiedIDs.indexOf(bonusrolleID);
            Game.game.spielerSpecified.remove(specifedIndex);
        }

        Bonusrolle bonusrolle = Game.game.findBonusrolle(bonusrolleID);
        Game.game.bonusrollenInGame.remove(bonusrolle);

        refresh();
    }

    protected void addAllRollen() {
        Game.game.addAllBonusrollen();
    }
}
