package root.Frontend.Frame;

import root.Phases.PhaseMode;

public class ErzählerFrameMode {
    public static int setup = 0;
    public static int ersteNacht = 1;
    public static int tag = 2;
    public static int freibierTag = 3;
    public static int nacht = 4;
    public static int nachzüglerSetup = 5;
    public static int umbringenSetup = 6;
    public static int priesterSetup = 7;
    public static int richterinSetup = 8;

    public static int getPhaseMode() {
        if(PhaseMode.phase == PhaseMode.tag) {
            return tag;
        } else if(PhaseMode.phase == PhaseMode.freibierTag) {
            return freibierTag;
        } else if(PhaseMode.phase == PhaseMode.ersteNacht) {
            return ersteNacht;
        } else if(PhaseMode.phase == PhaseMode.nacht) {
            return nacht;
        } else {
            return setup;
        }
    }
}
