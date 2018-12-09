package root.Frontend.Factories;

import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Logic.Game;
import root.Logic.Liebespaar;

import javax.swing.*;

public class ÜbersichtsPageFactory {
    ÜbersichtsFrame übersichtsFrame;
    ÜbersichtsPageElementFactory pageElementFactory;

    public ÜbersichtsPageFactory(ÜbersichtsFrame frame) {
        übersichtsFrame = frame;
        pageElementFactory = new ÜbersichtsPageElementFactory(übersichtsFrame);
    }

    public Page generateÜbersichtsPage() {
        int tableElementHeight = 25;
        int nameLabelWidth = 150;
        int hauptrolleLabelWidth = 150;
        int bonusrolleLabelWidth = 150;
        int aliveLabelWidth = 80;
        int activeLabelWidth = 80;
        int protectedLabelWidth = 80;
        int spaceBetween = 5;
        int columns = 1;

        PageElement liebespaarLabel = null;

        Page übersichtsPage = new Page();

        Liebespaar liebespaar = Game.game.liebespaar;

        if (liebespaar != null && liebespaar.spieler1 != null && !liebespaar.spieler1.equals(liebespaar.spieler2)) {
            liebespaarLabel = pageElementFactory.generateLabel(null, "Liebespaar: " + liebespaar.spieler1.name + ", " + liebespaar.spieler2.name);
            übersichtsPage.add(liebespaarLabel);
        }

        int xOffset = 0;
        übersichtsFrame.playerTable = pageElementFactory.generatePageTable(liebespaarLabel, columns, nameLabelWidth,
                tableElementHeight, 0, spaceBetween, xOffset, spaceBetween);

        xOffset += nameLabelWidth + spaceBetween;
        übersichtsFrame.hauptrolleTable = pageElementFactory.generatePageTable(liebespaarLabel, columns, hauptrolleLabelWidth,
                tableElementHeight, 0, spaceBetween, xOffset, spaceBetween);

        xOffset += hauptrolleLabelWidth + spaceBetween;
        übersichtsFrame.bonusrolleTable = pageElementFactory.generatePageTable(liebespaarLabel, columns, bonusrolleLabelWidth,
                tableElementHeight, 0, spaceBetween, xOffset, spaceBetween);

        xOffset += bonusrolleLabelWidth + spaceBetween;
        übersichtsFrame.aliveTable = pageElementFactory.generatePageTable(liebespaarLabel, columns, aliveLabelWidth,
                tableElementHeight, 0, spaceBetween, xOffset, spaceBetween);

        xOffset += aliveLabelWidth + spaceBetween;
        übersichtsFrame.activeTable = pageElementFactory.generatePageTable(liebespaarLabel, columns, activeLabelWidth,
                tableElementHeight, 0, spaceBetween, xOffset, spaceBetween);

        xOffset += activeLabelWidth + spaceBetween;
        übersichtsFrame.protectedTable = pageElementFactory.generatePageTable(liebespaarLabel, columns, protectedLabelWidth,
                tableElementHeight, 0, spaceBetween, xOffset, spaceBetween);

        übersichtsFrame.refreshJButton = new JButton();
        PageElement refreshButton = pageElementFactory.generateLowestButton(übersichtsFrame.refreshJButton, "Refresh", true);

        übersichtsPage.add(übersichtsFrame.playerTable);
        übersichtsPage.add(übersichtsFrame.hauptrolleTable);
        übersichtsPage.add(übersichtsFrame.bonusrolleTable);
        übersichtsPage.add(übersichtsFrame.aliveTable);
        übersichtsPage.add(übersichtsFrame.activeTable);
        übersichtsPage.add(übersichtsFrame.protectedTable);
        übersichtsPage.add(refreshButton);

        return übersichtsPage;
    }
}
