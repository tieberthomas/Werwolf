package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.Spieler;

public class PriesterPage extends TwoDropdownPage {
    public PriesterPage(DropdownOptions dropdownOptions) {
        super(dropdownOptions, "Bürgen", "Priester", "Spieler");
    }

    @Override
    public void executeAction() {
        String chosenOption1 = (String) comboBox1.getSelectedItem();
        String chosenOption2 = (String) comboBox2.getSelectedItem();

        Spieler priester = Game.game.findSpieler(chosenOption1);
        Spieler spieler = Game.game.findSpieler(chosenOption2);

        if (priester != null && spieler != null) {
            System.out.println(priester.name + " bürgt für " + spieler.name + ".");
            Game.game.day.bürgen(priester, spieler);
        }

        FrontendControl.showDayPage();
    }
}
