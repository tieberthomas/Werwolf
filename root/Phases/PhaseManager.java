package root.Phases;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.FrontendControl;

public class PhaseManager {
    public static void firstnight(ErzählerFrame erzählerFrame) {
        PhaseMode.phase = PhaseMode.ersteNacht;
        erzählerFrame.mode = ErzählerFrameMode.ersteNacht;
        erzählerFrame.übersichtsFrame = new ÜbersichtsFrame(erzählerFrame);
        erzählerFrame.toFront();
        FrontendControl.erzählerFrame = erzählerFrame;
        FrontendControl.spielerFrame = erzählerFrame.spielerFrame;
        ErsteNacht ersteNacht = new ErsteNacht();
        ersteNacht.start();
    }

    public static void day() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.tag;
        PhaseMode.phase = PhaseMode.tag;
        Tag tag = new Tag();
        tag.start();
    }

    public static void freibierDay() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.freibierTag;
        PhaseMode.phase = PhaseMode.freibierTag;
        Tag tag = new Tag();
        tag.start();
    }

    public static void night() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.nacht;
        PhaseMode.phase = PhaseMode.nacht;
        Nacht nacht = new Nacht();
        nacht.start();
    }
}
