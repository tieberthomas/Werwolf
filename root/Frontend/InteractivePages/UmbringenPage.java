package root.Frontend.InteractivePages;

import root.Controller.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.GameController;
import root.Logic.Game;
import root.Logic.Phases.Day;
import root.Logic.Spieler;

public class UmbringenPage extends OneDropdownPage {

    public UmbringenPage(DropdownOptions dropdownOptions) {
        super(dropdownOptions, "Umbringen", "Name");
    }

    @Override
    public void executeAction() {
        if (comboBox.getSelectedItem() != null) {
            String spielerName = comboBox.getSelectedItem().toString();
            Spieler spieler = Game.game.findSpieler(spielerName);

            if (spieler != null) {
                System.out.println(spieler.name + " wurde vom Erzähler umgebracht.");
                Day.umbringenSpieler = spieler;
                Day.umbringenButton = true;

                FrontendControl.refreshÜbersichtsFrame();

                GameController.continueThreads();
            }
        }
    }
}
