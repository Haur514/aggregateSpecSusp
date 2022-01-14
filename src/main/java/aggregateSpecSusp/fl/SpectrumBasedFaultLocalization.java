package aggregateSpecSusp.fl;

import aggregateSpecSusp.route.*;
import java.io.File;

public class SpectrumBasedFaultLocalization extends FaultLocalization {
    public SpectrumBasedFaultLocalization(ExtractLineData extractLineData) {
        super(extractLineData);
    }

    @Override
    double weight(int testCaseNum) {
        return 1;
    }

    @Override
    void setFileName() {
        this.file = new File("SBFL.txt");
    }
}
