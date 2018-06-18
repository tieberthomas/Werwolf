package root;

import root.Frontend.Frame.ErzählerFrame;

import javax.swing.*;

public class main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                ErzählerFrame erzählerFrame = new ErzählerFrame();
            }
        });
    }
}
