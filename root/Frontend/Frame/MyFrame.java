package root.Frontend.Frame;

import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.WHITE;

public class MyFrame extends JFrame {
    public static int DEFAULT_PANEL_WIDTH = 900;
    public static int DEFAULT_PANEL_HEIGHT = 675;
    public static final int yOffset = 38;
    public static final int xOffset = 8;

    public static Color DEFAULT_BACKGROUND_COLOR = WHITE;
    public static Color BACKGROUND_COLOR = DEFAULT_BACKGROUND_COLOR;

    public static Color DEFAULT_BUTTON_COLOR = new Color(240,240,240);

    public static String WINDOW_TITLE;

    public Page currentPage;
    public JPanel frameJpanel = null;

    public void showFrame() {
        this.setTitle(WINDOW_TITLE);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public void buildScreenFromPage(Page page) {
        currentPage = page;
        Dimension dimension = frameJpanel.getSize();
        frameJpanel = generatePanelFromPage(page);
        frameJpanel.setPreferredSize(dimension);
        this.setContentPane(frameJpanel);
        this.pack();
        this.requestFocusInWindow();
    }

    public JPanel generatePanelFromPage(Page page) {
        frameJpanel.removeAll();

        for(PageElement element : page.pageElements) {
            if(element.component.getClass() == JButton.class) {
                ((JButton)element.component).setFocusPainted(false);
            }
            frameJpanel.add(element.component);
        }

        for(PageTable table : page.pageTables) {
            for(JComponent component : table.tableElements) {
                frameJpanel.add(component);
            }
        }

        return frameJpanel;
    }

    public JPanel generateDefaultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(BACKGROUND_COLOR);
        panel.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT));
        panel.setSize(panel.getPreferredSize());
        return panel;
    }

    public static void calcFrameSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        if(width != 1920.0) {
            DEFAULT_PANEL_WIDTH = 720;
            DEFAULT_PANEL_HEIGHT = 540;
        }
    }
}
