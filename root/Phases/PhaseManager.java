package root.Phases;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Frame.ÜbersichtsFrame;

public class PhaseManager {
    public static void firstnight(ErzählerFrame erzählerFrame) {
        PhaseMode.phase = PhaseMode.ersteNacht;
        erzählerFrame.mode = ErzählerFrameMode.ersteNacht;
        erzählerFrame.übersichtsFrame = new ÜbersichtsFrame(erzählerFrame);
        erzählerFrame.toFront();
        ErsteNacht ersteNacht = new ErsteNacht(erzählerFrame, erzählerFrame.spielerFrame);
        ersteNacht.start();
    }

    public static void day(ErzählerFrame erzählerFrame, SpielerFrame spielerFrame) {
        erzählerFrame.mode = ErzählerFrameMode.tag;
        PhaseMode.phase = PhaseMode.tag;
        Tag tag = new Tag(erzählerFrame, spielerFrame);
        tag.start();
    }

    public static void freibierDay(ErzählerFrame erzählerFrame, SpielerFrame spielerFrame) {
        erzählerFrame.mode = ErzählerFrameMode.freibierTag;
        PhaseMode.phase = PhaseMode.freibierTag;
        Tag tag = new Tag(erzählerFrame, spielerFrame);
        tag.start();
    }

    public static void night(ErzählerFrame erzählerFrame, SpielerFrame spielerFrame) {
        erzählerFrame.mode = ErzählerFrameMode.nacht;
        PhaseMode.phase = PhaseMode.nacht;
        Nacht nacht = new Nacht(erzählerFrame, spielerFrame);
        nacht.start();
    }
}
