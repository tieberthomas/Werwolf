package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.GameController;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Bonusrollen.ReineSeele;
import root.Logic.Persona.Rollen.Bonusrollen.Schatten;
import root.Logic.Persona.Rollen.Bonusrollen.SchwarzeSeele;

import java.util.List;
import java.util.stream.Collectors;

public class BonusrolePage extends RolePage {
    protected void refreshSpielerFrame() {
        spielerFrame.refreshBonusrolleSetupPage(roles);
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

    protected void addAllRollen() {
        roles.clear();
        roles.addAll(Game.game.bonusrollen.stream().map(bonusrolle -> bonusrolle.name).collect(Collectors.toList()));
        roles.remove(Schatten.NAME);
        roles.remove(ReineSeele.NAME);
        roles.remove(SchwarzeSeele.NAME);
    }

    @Override
    protected void getRollenFromGame() {
        roles.clear();
        Game.game.bonusrollenInGame.forEach(role -> roles.add(role.name));
    }

    @Override
    protected void storeRollenInGame() {
        Game.game.bonusrollenInGame.clear();
        roles.forEach(role -> Game.game.bonusrollenInGame.add((Bonusrolle) Rolle.findRollePerName(role)));
    }
}
