package aggregateSpecSusp;

import java.io.File;
import java.util.List;

public class BlockedSpectrumBasedFaultLocalization extends FaultLocalization {

    BlockedSpectrumBasedFaultLocalization(ExtractLineData extractLineData, List<Double> weightTestCase) {
        super(extractLineData, weightTestCase);
    }

    BlockedSpectrumBasedFaultLocalization(ExtractLineData extractLineData, List<Double> weightTestCase,
            String fileName) {
        super(extractLineData, weightTestCase);
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
