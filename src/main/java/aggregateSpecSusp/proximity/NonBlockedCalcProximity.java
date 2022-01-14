package aggregateSpecSusp.proximity;

import aggregateSpecSusp.route.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * calc proximity with non-blocked spectrum for control experiment
 */
public class NonBlockedCalcProximity extends AbstractCalcProximity{
    private List<List<List<Integer>>> exRoute = new ArrayList<>();

    public NonBlockedCalcProximity(ExtractLineData extractLineData) {
        super(extractLineData);
        init();
        for (Integer failedTestNumber : failedTestList) {
            calcurateProximity(failedTestNumber);
        }
        takeWeightAverage();
        printWeight();
        pw.close();
    }

    private void init() {
        // init PointWriter
        file = new File("NonBlockedWeight.txt");
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file, false)));
        } catch (IOException e) {
            throw new RuntimeException("ERROR : FAILED NonBlockedCalcProximity INIT");
        }

        initSpectrumListAndWeight();
        // set exRoutes
        for(ExecutionRoute executionRoute : executionRoutes){
            exRoute.add(executionRoute.getExecutionRoutes());
        }
        setExecutionRoutes(exRoute);
    }
}
