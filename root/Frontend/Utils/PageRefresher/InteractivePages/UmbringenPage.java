package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Phases.Day;
import root.Logic.Spieler;
import root.Logic.Game;

public class UmbringenPage extends OneDropdownPage {
    private ÜbersichtsFrame übersichtsFrame;

    public UmbringenPage(DropdownOptions dropdownOptions, ÜbersichtsFrame übersichtsFrame) {
        super(dropdownOptions, "Umbringen", "Name");
        this.übersichtsFrame = übersichtsFrame;
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

                if (übersichtsFrame != null) {
                    übersichtsFrame.refresh();
                }

                ErzählerFrame.continueThreads();
            }
        }
    }
}
