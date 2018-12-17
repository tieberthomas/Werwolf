package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Logic.Game;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Werwolf;

import java.util.List;
import java.util.stream.Collectors;

public class MainrolePage extends RolePage {
    protected void refreshSpielerFrame() {
        spielerFrame.refreshHauptrolleSetupPage(roles);
    }

    @Override
    public void generatePage() {
        List<String> hauptrollen = Game.game.getHauptrolleNames();
        pageFactory.generateRollenSetupPage(page, interactiveElementsDto, hauptrollen);
    }

    @Override
    protected void addAllRollen() {
        roles.clear();
        roles.addAll(Game.game.hauptrollen.stream().map(hauptrolle -> hauptrolle.name).collect(Collectors.toList()));
        roles.remove(Dorfbewohner.NAME);
        roles.remove(Werwolf.NAME);
    }

    @Override
    protected void getRollenFromGame() {
        roles.clear();
        Game.game.hauptrollenInGame.forEach(role -> roles.add(role.name));
    }

    @Override
    protected void storeRollenInGame() {
        Game.game.hauptrollenInGame.clear();
        roles.forEach(role -> Game.game.hauptrollenInGame.add((Hauptrolle) Rolle.findRollePerName(role)));
    }
}
