package aggregateSpecSusp;

import java.io.File;

public class BlockedSpectrumBasedFaultLocalization extends FaultLocalization {

    BlockedSpectrumBasedFaultLocalization(ExtractLineData extractLineData, CalcProximity calcProximity) {
        super(extractLineData, calcProximity);
    }

    @Override
    double weight(int j) {
        return this.weightTestCase.get(j);
    }

    @Override
    void setFileName() {
        this.file = new File("BSBFL.txt");
    }
}
