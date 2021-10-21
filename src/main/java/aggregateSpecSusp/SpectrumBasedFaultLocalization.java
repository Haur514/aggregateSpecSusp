package aggregateSpecSusp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SpectrumBasedFaultLocalization extends FaultLocalization {
    SpectrumBasedFaultLocalization(ExtractLineData extractLineData) {
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
