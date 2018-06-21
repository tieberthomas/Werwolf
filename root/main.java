package root;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.MyFrame;

import javax.swing.*;

public class main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MyFrame.calcFrameSize();
                ErzählerFrame erzählerFrame = new ErzählerFrame();
            }
        });
    }
}
