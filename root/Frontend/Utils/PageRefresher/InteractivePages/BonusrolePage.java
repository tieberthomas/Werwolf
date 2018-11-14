package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Utils.PageRefresher.Models.ButtonTable;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.Label;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Persona.Bonusrolle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class BonusrolePage extends RolePage {
    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new ButtonTable(roleButtons));
        pageRefresher.add(new LabelTable(labelTable, game::getBonusrolleInGameNames));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, game.bonusrollenInGame::size));
        pageRefresher.add(new Label(counterLabel, this::getBonusroleCounterLabelText));
    }

    private String getBonusroleCounterLabelText() {
        return getCounterLabelText(game.spieler.size(), game.bonusrollenInGame.size());
    }

    protected void refreshSpielerFrame() {
        spielerFrame.refreshBonusrolleSetupPage();
    }

    @Override
    public void generatePage() {
        int numberOfplayers = game.spieler.size();
        int numberOfBonusrollen = game.bonusrollenInGame.size();
        List<String> bonusrollen = game.getBonusrolleNames();
        //TODO remove information from page generation since it already is gathered in refresh
        pageFactory.generateRollenSetupPage(page, interactiveElementsDto, numberOfplayers, numberOfBonusrollen, bonusrollen);
        //TODO generalize role-pagecreation
    }

    @Override
    protected void next() {
        erzählerFrame.nextPage();
        erzählerFrame.dataManager.writeComposition(); //TODO access over game controller
        //TODO generate speciyplayer page here, it cannot be created before that
    }

    @Override
    protected void addRolle(ActionEvent ae) {
        String bonusrolleName = ((JButton) ae.getSource()).getText();
        Bonusrolle newBonusrolle = game.findBonusrollePerName(bonusrolleName);
        game.bonusrollenInGame.add(newBonusrolle);

        refresh();
    }

    @Override
    protected void deleteRolle(ActionEvent ae) {
        int index = deleteButtons.indexOf(ae.getSource());
        deleteButtons.remove(index);
        String bonusrolleName = game.bonusrollenInGame.get(index).name;

        List<String> bonusrollenSpecifiedStrings = game.getBonusrolleSpecifiedStrings();

        if (bonusrollenSpecifiedStrings.contains(bonusrolleName)) {
            int specifedIndex = bonusrollenSpecifiedStrings.indexOf(bonusrolleName);
            removeSpecifiedPlayer(specifedIndex);
        }

        game.bonusrollenInGame.remove(index);

        refresh();
    }

    protected void addAllRollen() {
        game.addAllBonusrollen();
    }
}
