package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Page.PageTable;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.SpecifyPageElementsDto;
import root.Frontend.Utils.PageRefresher.Models.Combobox;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Spieler;
import root.Logic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpecifyPage extends RefreshedPage {
    private SpecifyPageElementsDto interactiveElementsDto;

    private PageTable playerLabelTable;
    private PageTable mainroleLabelTable;
    private PageTable bonusroleLabelTable;
    private PageTable deleteTable;
    private List<JButton> deleteButtons = new ArrayList<>();
    private JComboBox spielerComboBox;
    private JComboBox hauptrollenComboBox;
    private JComboBox bonusrollenComboBox;
    private JButton next;
    private JButton back;

    @Override
    protected void setupObjects() {
        playerLabelTable = new PageTable();
        mainroleLabelTable = new PageTable();
        bonusroleLabelTable = new PageTable();
        deleteTable = new PageTable();
        spielerComboBox = new JComboBox();
        hauptrollenComboBox = new JComboBox();
        bonusrollenComboBox = new JComboBox();
        next = new JButton();
        back = new JButton();
    }

    @Override
    public void generatePage() {
        pageFactory.generateSpecifiyPage(page, interactiveElementsDto);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (next.equals(ae.getSource())) {
            if (allPlayersSpecified()) {
                Game.game.startGame(erzählerFrame); //TODO auslagern in controller
                erzählerFrame.dataManager.writeGame(); //TODO thread game writing
            } else {
                specifyPlayer();
            }
        } else if (back.equals(ae.getSource())) {
            erzählerFrame.prevPage();
        } else if (deleteButtons.contains(ae.getSource())) {
            deleteSpecifiedPlayer(ae);
        }
    }

    private boolean allPlayersSpecified() {
        return Game.game.spielerSpecified.size() == Game.game.spieler.size();
    }

    private void specifyPlayer() { //TODO make strings as parameters
        try {
            String spielerName = (String) spielerComboBox.getSelectedItem();
            Spieler spieler = Game.game.findSpieler(spielerName);
            Game.game.spielerSpecified.add(spieler);

            String hauptrolle = (String) hauptrollenComboBox.getSelectedItem();
            spieler.hauptrolle = Game.game.findHauptrollePerName(hauptrolle);
            if (spieler.hauptrolle == null) {
                spieler.hauptrolle = Hauptrolle.getDefaultHauptrolle();
            }

            String bonusrolle = (String) bonusrollenComboBox.getSelectedItem();
            spieler.bonusrolle = Game.game.findBonusrollePerName(bonusrolle);
            if (spieler.bonusrolle == null) {
                spieler.bonusrolle = Bonusrolle.DEFAULT_BONUSROLLE;
            }

            refresh();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No player in game.");
        }
    }

    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new LabelTable(playerLabelTable,
                () -> Game.game.spielerSpecified.stream()
                        .map(player -> player.name)
                        .collect(Collectors.toList()), false));
        pageRefresher.add(new LabelTable(mainroleLabelTable,
                () -> Game.game.spielerSpecified.stream()
                        .map(player -> player.hauptrolle.name)
                        .collect(Collectors.toList()), false));
        pageRefresher.add(new LabelTable(bonusroleLabelTable,
                () -> Game.game.spielerSpecified.stream()
                        .map(player -> player.bonusrolle.name)
                        .collect(Collectors.toList()), false));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, Game.game.spielerSpecified::size));
        pageRefresher.add(new Combobox(spielerComboBox, Game.game::getSpielerUnspecifiedStrings));
        pageRefresher.add(new Combobox(hauptrollenComboBox, Game.game::getHauptrollenUnspecifiedStrings));
        pageRefresher.add(new Combobox(bonusrollenComboBox, Game.game::getBonusrollenUnspecifiedStrings));
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new SpecifyPageElementsDto(playerLabelTable, mainroleLabelTable, bonusroleLabelTable, deleteTable,
                deleteButtons, spielerComboBox, hauptrollenComboBox, bonusrollenComboBox, next, back);
    }

    public void refresh() {
        Collections.sort(Game.game.spielerSpecified);
        pageRefresher.refreshPage();
        if (spielerFrame != null) {
            spielerFrame.refreshSecondarySpecifySetupPage();
        }
    }

    private void deleteSpecifiedPlayer(ActionEvent ae) {
        for (int i = 0; i < deleteButtons.size(); i++) {
            if (ae.getSource() == deleteButtons.get(i)) {
                removeSpecifiedPlayer(i);

                refresh();
            }
        }
    }

    private void removeSpecifiedPlayer(int index) {
        if (deleteButtons.size() > index) {
            deleteButtons.remove(index);
        }

        Game.game.spielerSpecified.remove(index);
    }
}
