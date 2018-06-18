package root.Frontend.Frame;

import root.Frontend.Factories.ErzählerPageElementFactory;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.WHITE;

public class MyFrame extends JFrame {
    public static int PANEL_WIDTH = 900;
    public static int PANEL_HEIGHT = 675;
    public static final int yOffset = 38;

    public static Color DEFAULT_BACKGROUND_COLOR = WHITE;
    public static Color BACKGROUND_COLOR = DEFAULT_BACKGROUND_COLOR;

    public static Color DEFAULT_BUTTON_COLOR = new Color(240,240,240);

    public static String WINDOW_TITLE;

    public Page currentPage;
    public ErzählerPageElementFactory pageFactory;

    public void showFrame() {
        this.setTitle(WINDOW_TITLE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void buildScreenFromPage(Page page) {
        currentPage = page;
        JPanel panel = generatePanelFromPage(page);
        this.setContentPane(panel);
        this.pack();
        this.requestFocusInWindow();
    }

    public JPanel generatePanelFromPage(Page page) {
        JPanel panel = generateDefaultPanel();

        for(PageElement element : page.pageElements) {
            if(element.component.getClass() == JButton.class) {
                ((JButton)element.component).setFocusPainted(false);
            }
            panel.add(element.component);
        }

        for(PageTable table : page.pageTables) {
            for(JComponent component : table.tableElements) {
                panel.add(component);
            }
        }

        return panel;
    }

    public JPanel generateDefaultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(this.BACKGROUND_COLOR);
        panel.setPreferredSize(new Dimension(this.PANEL_WIDTH, this.PANEL_HEIGHT));
        return panel;
    }
}
