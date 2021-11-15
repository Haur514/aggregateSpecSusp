package aggregateSpecSusp;

import java.io.File;
import java.util.List;

public class NonBlockedSpectrumBasedFaultLocalization extends FaultLocalization {
    NonBlockedSpectrumBasedFaultLocalization(ExtractLineData extractLineData, List<Double> weightTestCase) {
        super(extractLineData, weightTestCase);
    }

    @Override
    double weight(int j) {
        return this.weightTestCase.get(j);
    }

    @Override
    void setFileName() {
        this.file = new File("NonBSBFL.txt");
    }
}
