package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Controller.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.Spieler;

public class RichterinPage extends TwoDropdownPage {
    public RichterinPage(DropdownOptions dropdownOptions) {
        super(dropdownOptions, "Verurteilen", "Richterin", "Spieler");
    }

    @Override
    public void executeAction() {
        String chosenOption1 = (String) comboBox1.getSelectedItem();
        String chosenOption2 = (String) comboBox2.getSelectedItem();

        Spieler richterin = Game.game.findSpieler(chosenOption1);
        Spieler spieler = Game.game.findSpieler(chosenOption2);

        if (richterin != null && spieler != null) {
            System.out.println(richterin.name + " verurteilt " + spieler.name + ".");
            Game.game.day.verurteilen(richterin, spieler);
        }

        FrontendControl.showDayPage();
    }
}
